package ma.emsi.leavemanagement.controllers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import ma.emsi.leavemanagement.entities.Leave;
import ma.emsi.leavemanagement.enums.LeaveStatus;

/**
 * LeaveController
 */
public interface LeaveController {

    public ResponseEntity<Leave> submitLeaveRequest(Leave leave, Long idPerson);

    public CollectionModel<EntityModel<Leave>> getAllLeaves(Long idPerson);

    public ResponseEntity<EntityModel<Leave>> approveLeaveRequest(Long idLeave, Long idManager);

    public ResponseEntity<Leave> cancelLeaveRequest(Long idLeave);

    public ResponseEntity<EntityModel<Leave>> declineLeaveRequest(Long idLeave, Long idManager);

    public CollectionModel<EntityModel<Leave>> getLeavesUnderSupervision(Long idManager);

    public CollectionModel<EntityModel<Leave>> getPendingLeavesUnderSupervisor(Long idSupervisor);

    public CollectionModel<EntityModel<Leave>> getCancelledLeavesUnderSupervisor(Long idSupervisor);

    public CollectionModel<EntityModel<Leave>> getAcceptedLeavesUnderSupervisor(Long idSupervisor);

}