package ma.emsi.leavemanagement.services;

import ma.emsi.leavemanagement.entities.Person;
import ma.emsi.leavemanagement.enums.LeaveType;

import java.util.List;

public interface PersonService {

    public Person savePerson(Person person);
    public int getLeaveTypeBalance(Person person, LeaveType leaveType);
    public List<Person> getPersonsList();
}
