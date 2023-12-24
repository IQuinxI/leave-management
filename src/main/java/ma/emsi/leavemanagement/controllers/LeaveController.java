package ma.emsi.leavemanagement.controllers;


import lombok.AllArgsConstructor;
import ma.emsi.leavemanagement.entities.Leave;
import ma.emsi.leavemanagement.services.LeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/leaves")
@CrossOrigin("*")
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping("/{idPerson}")
    public ResponseEntity<Leave> submitLeaveRequest(@RequestBody Leave leave,@PathVariable Long idPerson){
        Leave savedLeave=leaveService.saveLeave(leave,idPerson);
        return new ResponseEntity<>(savedLeave, HttpStatus.OK);
    }
}
