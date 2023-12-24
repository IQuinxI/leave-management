package ma.emsi.leavemanagement.services;

import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.entities.Leave;
import org.springframework.stereotype.Service;


public interface LeaveService {
    public Leave saveLeave(Leave leave,Long idPerson);
}
