package ma.emsi.leavemanagement.services;

import ma.emsi.leavemanagement.entities.Leave;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;


public interface LeaveService {
    public Leave saveLeave(Leave leave,Long idPerson);
    public CollectionModel<EntityModel<Leave>> getAllLeaves(Long idPerson);
    public Leave cancelLeave(Long idLeave);
}
