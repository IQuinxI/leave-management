package ma.emsi.leavemanagement.controllers;


import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.entities.Leave;
import ma.emsi.leavemanagement.enums.LeaveStatus;
import ma.emsi.leavemanagement.repositories.LeaveRepository;
import ma.emsi.leavemanagement.services.Impl.LeaveBalanceService;
import ma.emsi.leavemanagement.services.LeaveService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@AllArgsConstructor
@RequestMapping("api/v1/leaves")
@CrossOrigin("*")
public class LeaveControllerImpl implements LeaveController {

    private final LeaveService leaveService;
    private final LeaveBalanceService leaveBalanceService;

    @PostMapping("/{idPerson}")
    public ResponseEntity<Leave> submitLeaveRequest(@RequestBody Leave leave,@PathVariable Long idPerson){
        Leave savedLeave=leaveService.saveLeave(leave,idPerson);
        return new ResponseEntity<>(savedLeave, HttpStatus.OK);
    }

    @GetMapping("/{idPerson}")
    public CollectionModel<EntityModel<Leave>> getAllLeaves(@PathVariable("idPerson") Long idPerson) {
        return leaveService.getAllLeaves(idPerson);
    }

    @PutMapping("/approve")
    @Override
    public ResponseEntity<EntityModel<Leave>> approveLeaveRequest(@RequestParam ("idLeave") Long idLeave, @RequestParam("idManager") Long idManager) {
        return leaveService.approveLeaveRequest(idLeave, idManager);
    }
    
    @PutMapping("cancel/{idLeave}")
    public ResponseEntity<Leave> cancelLeaveRequest(@PathVariable Long idLeave){
        Leave canceledLeave=leaveService.cancelLeave(idLeave);
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
    public ResponseEntity<EntityModel<Leave>> declineLeaveRequest(@RequestParam ("idLeave") Long idLeave, @RequestParam("idManager") Long idManager) {
        return leaveService.declineLeaveRequest(idLeave, idManager);
    }

   
    @GetMapping("/managers/{idManager}")
    @Override
    public CollectionModel<EntityModel<Leave>> getLeavesUnderSupervision(@PathVariable("idManager") Long idManager) {
        return leaveService.getLeavesUnderSupervision(idManager);
    }


    
}
