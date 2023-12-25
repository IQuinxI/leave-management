package ma.emsi.leavemanagement.services.Impl;

import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.assemblers.EmployeeAssembler;
import ma.emsi.leavemanagement.assemblers.LeaveAssembler;
import ma.emsi.leavemanagement.entities.Leave;
import ma.emsi.leavemanagement.entities.Person;
import ma.emsi.leavemanagement.enums.Approbation;
import ma.emsi.leavemanagement.exceptions.EmployeeNotFoundException;
import ma.emsi.leavemanagement.exceptions.InsufficientBalanceException;
import ma.emsi.leavemanagement.exceptions.InvalidLeaveDateException;
import ma.emsi.leavemanagement.exceptions.InvalidLeaveTypeException;
import ma.emsi.leavemanagement.repositories.LeaveRepository;
import ma.emsi.leavemanagement.repositories.PersonRepository;
import ma.emsi.leavemanagement.services.LeaveService;
import ma.emsi.leavemanagement.validators.EmployeeValidator;
import ma.emsi.leavemanagement.validators.LeaveValidators;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LeaveServiceImpl implements LeaveService {

	private final LeaveRepository leaveRepository;
	private final LeaveValidators leaveValidators;
	private final PersonRepository personRepository;
	private final EmployeeValidator employeeValidator;
	private final LeaveAssembler leaveAssembler;

	@Override
	public Leave saveLeave(Leave leave, Long idPerson)
			throws InsufficientBalanceException, InvalidLeaveDateException, InvalidLeaveTypeException {
		personRepository.save(
				Person.builder()
						.Id(idPerson)
						.sickBalance(100)
						.annualBalance(100)
						.unpaidBalance(100)
						.maternityBalance(100)
						.build());

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
}
