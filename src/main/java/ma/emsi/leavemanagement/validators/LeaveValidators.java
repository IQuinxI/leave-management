package ma.emsi.leavemanagement.validators;

import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.entities.Leave;
import ma.emsi.leavemanagement.entities.Person;
import ma.emsi.leavemanagement.enums.LeaveType;
import ma.emsi.leavemanagement.exceptions.InsufficientBalanceException;
import ma.emsi.leavemanagement.exceptions.InvalidLeaveDateException;
import ma.emsi.leavemanagement.exceptions.InvalidLeaveTypeException;
import ma.emsi.leavemanagement.services.PersonService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class LeaveValidators {
    private final PersonService personService;

    public void validateLeaveRequest(Leave leave, Person person) {
        validateLeaveDateRange(leave);
        validateLeaveTypeBalance(leave, person);
        validateLeaveType(leave);
    }

    private void validateLeaveType(Leave leave) {

            String type = leave.getLeaveType().name();
            if(!type.equals("UNPAID") && !type.equals("ANNUAL") &&!type.equals("SICK") && !type.equals("MATERNITY")){
                throw new InvalidLeaveTypeException("Invalid leave type: " + leave.getLeaveType());

            }
    }


    private void validateLeaveDateRange(Leave leave) {
        Date startDate = leave.getStartDate();
        Date endDate = leave.getEndDate();

        if (startDate == null || endDate == null || startDate.after(endDate)) {
            throw new InvalidLeaveDateException("Invalid leave date range");
        }
    }

    private void validateLeaveTypeBalance(Leave leave, Person person) {
        LeaveType leaveType = leave.getLeaveType();
        int balance = personService.getLeaveTypeBalance(person, leaveType);
        int leaveDays = calculateLeaveDays(leave);

        if (balance < leaveDays) {
            throw new InsufficientBalanceException("Insufficient leave balance for type: " + leaveType);
        }
    }

    private int calculateLeaveDays(Leave leave) {
        int millisInDay = 24 * 60 * 60 * 1000;
        return (int) ((leave.getEndDate().getTime() - leave.getStartDate().getTime()) / millisInDay) + 1;
    }
}
