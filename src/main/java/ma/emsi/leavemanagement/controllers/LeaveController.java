package ma.emsi.leavemanagement.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import ma.emsi.leavemanagement.entities.Leave;

/**
 * LeaveController
 */
public interface LeaveController {

    public ResponseEntity<Leave> submitLeaveRequest(Leave leave, Long idPerson);
    public CollectionModel<EntityModel<Leave>> getAllLeaves(Long idPerson);
    public ResponseEntity<EntityModel<Leave>> approveLeaveRequest(Long idLeave, Long idManager);
    public ResponseEntity<Leave> cancelLeaveRequest(Long idLeave);


}