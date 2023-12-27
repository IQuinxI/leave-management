package ma.emsi.leavemanagement.services.Impl;

import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.assemblers.EmployeeAssembler;
import ma.emsi.leavemanagement.assemblers.LeaveAssembler;
import ma.emsi.leavemanagement.entities.Employee;
import ma.emsi.leavemanagement.entities.Leave;
import ma.emsi.leavemanagement.entities.Manager;
import ma.emsi.leavemanagement.entities.Person;
import ma.emsi.leavemanagement.enums.Approbation;
import ma.emsi.leavemanagement.enums.LeaveStatus;
import ma.emsi.leavemanagement.exceptions.*;
import ma.emsi.leavemanagement.exceptions.LeaveNotFoundException;
import ma.emsi.leavemanagement.exceptions.ManagerDoesNotOverseeEmployeeException;
import ma.emsi.leavemanagement.repositories.LeaveRepository;
import ma.emsi.leavemanagement.repositories.ManagerRepository;
import ma.emsi.leavemanagement.repositories.PersonRepository;
import ma.emsi.leavemanagement.services.LeaveService;
import ma.emsi.leavemanagement.validators.EmployeeValidator;
import ma.emsi.leavemanagement.validators.LeaveValidators;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Service
public class LeaveServiceImpl implements LeaveService {

	private final LeaveRepository leaveRepository;
	private final LeaveValidators leaveValidators;
	private final PersonRepository personRepository;
	private final EmployeeValidator employeeValidator;
	private final LeaveAssembler leaveAssembler;
	private final ManagerRepository managerRepository;

	@Override
	public Leave saveLeave(Leave leave, Long idPerson)
			throws InsufficientBalanceException, InvalidLeaveDateException, InvalidLeaveTypeException {

		Person person = personRepository.findById(idPerson)
				.orElseThrow(() -> new EmployeeNotFoundException(idPerson));

		// validate leave inputs
		leaveValidators.validateLeaveRequest(leave, person);

		Leave savedLeave = Leave.builder()
				.person(person)
				.leaveType(leave.getLeaveType())
				.startDate(leave.getStartDate())
				.endDate(leave.getEndDate())
				.approbation(Approbation.APPRO_MANAGER)
				.status(LeaveStatus.PENDING)
				.createdAt(new Date())
				.build();

		return leaveRepository.save(savedLeave);

	}

	@Override
	public CollectionModel<EntityModel<Leave>> getAllLeaves(Long idPerson) {
		// throws exception if the person is not found
		Person person = employeeValidator.doesEmployeeExist(idPerson);

		// retrieves all leaves done by the person
		// turn the list into a collectionModel
		List<EntityModel<Leave>> leavesList = leaveRepository
				.findByPerson(person)
				.stream()
				.map(leaveAssembler::toModel)
				.collect(Collectors.toList());

		// convert the list into CollectionModel
		// and return it
		return CollectionModel.of(leavesList);
	}

	@Override
	public Leave cancelLeave(Long idLeave) {
		Leave canceledLeave = leaveRepository.findById(idLeave)
				.orElseThrow(()->new LeaveNotFoundException(idLeave));

		canceledLeave.setApprobation(Approbation.NONE);
		canceledLeave.setStatus(LeaveStatus.CANCELLED);
		return canceledLeave;
	}

	@Override
	public ResponseEntity<EntityModel<Leave>> approveLeaveRequest(Long idLeave, Long idManager) {
		// checks if the manager exists
		managerRepository.findById(idManager)
				.orElseThrow(() -> new EmployeeNotFoundException());


		// checks if the Leave request already exists
		Leave leave = leaveRepository.findById(idLeave)
				.orElseThrow(() -> new LeaveNotFoundException(idLeave));

		// checks if the manager oversees the employee
		// uses JPA to find the employee id inside the list of employees
		Long idEmployee = leave.getPerson().getId();
		List<Manager> emp = managerRepository.findByIdAndEmployees_Id(idManager, idEmployee);
		if(emp.size() == 0) 
			throw  new ManagerDoesNotOverseeEmployeeException();

		// checks if the Leave request is pending
		leaveValidators.leaveRequestIsPending(leave);

		// change the status
		leave.setStatus(LeaveStatus.ACCEPTED);

		// assemble the entity
		EntityModel<Leave> leaveEntityModel = leaveAssembler.toModel(leave);

		// return a response entity
		return ResponseEntity
				.created(leaveEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(leaveEntityModel);
	}
}
