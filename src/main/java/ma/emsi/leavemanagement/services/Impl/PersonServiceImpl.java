package ma.emsi.leavemanagement.services.Impl;


import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.entities.Person;
import ma.emsi.leavemanagement.enums.LeaveType;
import ma.emsi.leavemanagement.exceptions.InvalidLeaveTypeException;
import ma.emsi.leavemanagement.repositories.PersonRepository;
import ma.emsi.leavemanagement.services.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;


    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public int getLeaveTypeBalance(Person person, LeaveType leaveType) throws InvalidLeaveTypeException {
        switch (leaveType){
            case ANNUAL:return person.getAnnualBalance();
            case SICK:return person.getSickBalance();
            case UNPAID:return person.getUnpaidBalance();
            case MATERNITY:return person.getMaternityBalance();
            default:throw new InvalidLeaveTypeException("Unsupported leave type : "+leaveType);
        }
    }

    public List<Person> getPersonsList(){
        return personRepository.findAll();
    }

}
