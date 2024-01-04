package ma.emsi.leavemanagement.services.Impl;

import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.assemblers.LeaveAssembler;
import ma.emsi.leavemanagement.controllers.LeaveControllerImpl;
import ma.emsi.leavemanagement.entities.Leave;
import ma.emsi.leavemanagement.entities.Manager;
import ma.emsi.leavemanagement.entities.Person;
import ma.emsi.leavemanagement.entities.Supervisor;
import ma.emsi.leavemanagement.enums.Approbation;
import ma.emsi.leavemanagement.enums.LeaveStatus;
import ma.emsi.leavemanagement.exceptions.*;
import ma.emsi.leavemanagement.repositories.LeaveRepository;
import ma.emsi.leavemanagement.repositories.ManagerRepository;
import ma.emsi.leavemanagement.repositories.PersonRepository;
import ma.emsi.leavemanagement.repositories.SupervisorRepository;
import ma.emsi.leavemanagement.services.LeaveService;
import ma.emsi.leavemanagement.validators.EmployeeValidator;
import ma.emsi.leavemanagement.validators.LeaveValidators;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
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
	private final SupervisorRepository sueprvisorRepository;

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
				.orElseThrow(() -> new LeaveNotFoundException(idLeave));

		canceledLeave.setApprobation(Approbation.NONE);
		canceledLeave.setStatus(LeaveStatus.CANCELLED);
		return canceledLeave;
	}

	@Override
	public ResponseEntity<EntityModel<Leave>> approveLeaveRequest(Long idLeave, Long idManager) {
		return approbation(idLeave, idManager, LeaveStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<EntityModel<Leave>> declineLeaveRequest(Long idLeave, Long idManager) {
		return approbation(idLeave, idManager, LeaveStatus.REJECTED);
	}

	private ResponseEntity<EntityModel<Leave>> approbation(Long idLeave, Long idManager, LeaveStatus leaveStatus) {
		managerRepository.findById(idManager)
				.orElseThrow(() -> new EmployeeNotFoundException());

		// checks if the Leave request already exists
		Leave leave = leaveRepository.findById(idLeave)
				.orElseThrow(() -> new LeaveNotFoundException(idLeave));

		// checks if the manager oversees the employee
		// uses JPA to find the employee id inside the list of employees
		Long idEmployee = leave.getPerson().getId();
		List<Manager> emp = managerRepository.findByIdAndEmployees_Id(idManager, idEmployee);
		if (emp.size() == 0)
			throw new ManagerDoesNotOverseeEmployeeException();

		// checks if the Leave request is pending
		leaveValidators.leaveRequestIsPending(leave);

		// change the status
		leave.setStatus(leaveStatus);
		leave.setApprobation(Approbation.NONE);
		// assemble the entity
		EntityModel<Leave> leaveEntityModel = leaveAssembler.toModel(leave);

		// return a response entity
		return ResponseEntity
				.created(leaveEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(leaveEntityModel);
	}

	// this is a general purpose method
	// it will be used for all supervisors (HR, manager)
	// to get all the leaves of the employees
	@Override
	public CollectionModel<EntityModel<Leave>> getLeavesUnderSupervision(Long idManager) {
		List<Leave> leaves = new ArrayList<>();

		// retrieve all employees under the manager
		// and load the leaves in the leaves List
		Supervisor supervisor = sueprvisorRepository.findById(idManager)
				.orElseThrow(() -> new EmployeeNotFoundException("Manager not found"));

		supervisor.getEmployees()
				.forEach(emp -> {
					leaves.addAll(emp.getLeaves());
				});

		// convert the list into a collectionModel
		CollectionModel<EntityModel<Leave>> collectionModel = CollectionModel.of(leaves
						.stream()
						.filter(leave -> leave.getApprobation() == Approbation.APPRO_MANAGER)
						.map(leaveAssembler::toModel)
						.collect(Collectors.toList()),
				linkTo(methodOn(LeaveControllerImpl.class).getLeavesUnderSupervision(idManager)).withSelfRel());

		return collectionModel;
	}

}
