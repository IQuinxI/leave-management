package ma.emsi.leavemanagement.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ma.emsi.leavemanagement.controllers.LeaveControllerImpl;
import ma.emsi.leavemanagement.entities.Leave;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * LeaveAssembler
 */
@Component
public class LeaveAssembler implements RepresentationModelAssembler<Leave, EntityModel<Leave>>{

    @Override
    public EntityModel<Leave> toModel(Leave leave) {
        return EntityModel.of(leave, 
        // TODO: uncomment when the getLeave endpoint has been added
            // linkTo(methodOn(LeaveControllerImpl.class).getLeave(leave.getLeaveId())).withSelfRel(),
            linkTo(methodOn(LeaveControllerImpl.class).getAllLeaves(leave.getPerson().getId())).withSelfRel()
        );
    }

    
}