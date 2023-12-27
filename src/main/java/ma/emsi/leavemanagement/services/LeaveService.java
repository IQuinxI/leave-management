package ma.emsi.leavemanagement.services;

import ma.emsi.leavemanagement.entities.Leave;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;


public interface LeaveService {
    public Leave saveLeave(Leave leave,Long idPerson);
    public CollectionModel<EntityModel<Leave>> getAllLeaves(Long idPerson);
    public ResponseEntity<EntityModel<Leave>> approveLeaveRequest(Long idLeave, Long idManager);
}
