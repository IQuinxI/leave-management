package ma.emsi.leavemanagement.controllers;


import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.entities.Leave;
import ma.emsi.leavemanagement.services.LeaveService;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@RequestMapping("api/v1/leaves")
@CrossOrigin("*")
public class LeaveControllerImpl implements LeaveController {

    private final LeaveService leaveService;

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
    
}
