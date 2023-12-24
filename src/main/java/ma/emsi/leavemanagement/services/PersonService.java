package ma.emsi.leavemanagement.services;

import ma.emsi.leavemanagement.entities.Person;
import ma.emsi.leavemanagement.enums.LeaveType;

public interface PersonService {

    public Person savePerson(Person person);
    public int getLeaveTypeBalance(Person person, LeaveType leaveType);
}
