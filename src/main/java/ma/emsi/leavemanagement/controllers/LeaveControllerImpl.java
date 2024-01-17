package ma.emsi.leavemanagement.controllers;

import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.entities.Leave;
import ma.emsi.leavemanagement.enums.LeaveStatus;
import ma.emsi.leavemanagement.services.Impl.LeaveBalanceService;
import ma.emsi.leavemanagement.services.LeaveService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/leaves")
@CrossOrigin("*")
public class LeaveControllerImpl implements LeaveController {

    private final LeaveService leaveService;
    private final LeaveBalanceService leaveBalanceService;

    @PostMapping("/{idPerson}")
    public ResponseEntity<Leave> submitLeaveRequest(@RequestBody Leave leave, @PathVariable Long idPerson) {
        Leave savedLeave = leaveService.saveLeave(leave, idPerson);
        return new ResponseEntity<>(savedLeave, HttpStatus.OK);
    }

    @GetMapping("/{idPerson}")
    public CollectionModel<EntityModel<Leave>> getAllLeaves(@PathVariable("idPerson") Long idPerson) {
        return leaveService.getAllLeaves(idPerson);
    }

    @PutMapping("/approve")
    @Override
    public ResponseEntity<EntityModel<Leave>> approveLeaveRequest(@RequestParam("idLeave") Long idLeave,
            @RequestParam("idManager") Long idManager) {
        return leaveService.approveLeaveRequest(idLeave, idManager);
    }

    @PutMapping("cancel/{idLeave}")
    public ResponseEntity<Leave> cancelLeaveRequest(@PathVariable Long idLeave) {
        Leave canceledLeave = leaveService.cancelLeave(idLeave);
        return new ResponseEntity<>(canceledLeave, HttpStatus.OK);
    }

    @PostMapping("/reset")
    @PreAuthorize("hasRole('ADMIN')") // Example authorization
    public ResponseEntity<?> resetLeaveBalancesManually() {
        leaveBalanceService.updateLeaveBalanceEachNewYear();
        return ResponseEntity.ok("Leave balances reset successfully");
    }

    @PutMapping("/decline")
    @Override
    public ResponseEntity<EntityModel<Leave>> declineLeaveRequest(@RequestParam("idLeave") Long idLeave,
            @RequestParam("idManager") Long idManager) {
        return leaveService.declineLeaveRequest(idLeave, idManager);
    }

    @GetMapping("/managers/{idManager}")
    @Override
    public CollectionModel<EntityModel<Leave>> getLeavesUnderSupervision(@PathVariable("idManager") Long idManager) {
        return leaveService.getLeavesUnderSupervision(idManager);
    }

    // get all pending leaves under a supervisor
    @GetMapping("/managers/pending/{idSupervisor}")
    @Override
    public CollectionModel<EntityModel<Leave>> getPendingLeavesUnderSupervisor(
            @PathVariable("idSupervisor") Long idSupervisor) {
        return leaveService.getLeavesUnderSupervisorByStatus(idSupervisor, LeaveStatus.PENDING)
                .add(linkTo(methodOn(LeaveControllerImpl.class).getPendingLeavesUnderSupervisor(idSupervisor))
                        .withSelfRel());
    }

    // get all cancelled leaves under a supervisor
    @GetMapping("/managers/cancelled/{idSupervisor}")
    @Override
    public CollectionModel<EntityModel<Leave>> getCancelledLeavesUnderSupervisor(
            @PathVariable("idSupervisor") Long idSupervisor) {
        return leaveService.getLeavesUnderSupervisorByStatus(idSupervisor, LeaveStatus.CANCELLED)
                .add(linkTo(methodOn(LeaveControllerImpl.class).getCancelledLeavesUnderSupervisor(idSupervisor))
                        .withSelfRel());
    }

    // get all accepted leaves under a supervisor
    @GetMapping("/managers/accepted/{idSupervisor}")
    @Override
    public CollectionModel<EntityModel<Leave>> getAcceptedLeavesUnderSupervisor(
            @PathVariable("idSupervisor") Long idSupervisor) {
        return leaveService.getLeavesUnderSupervisorByStatus(idSupervisor,
                LeaveStatus.ACCEPTED)
                .add(linkTo(methodOn(LeaveControllerImpl.class).getAcceptedLeavesUnderSupervisor(idSupervisor))
                        .withSelfRel());
    }

}
