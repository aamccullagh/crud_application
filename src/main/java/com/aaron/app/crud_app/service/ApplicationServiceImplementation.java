package com.aaron.app.crud_app.service;
import com.aaron.app.crud_app.logging.LogMethodEntry;
import com.aaron.app.crud_app.models.User;
import com.aaron.app.crud_app.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImplementation implements ApplicationService {

    @Autowired
    UserRepo userRepo;

    @LogMethodEntry
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    @LogMethodEntry
    public String addUser(User user ){
        userRepo.save(user);
        return "user added";
    }

    @LogMethodEntry
    public String updateUser( Integer id, User user){

        Optional<User> userToUpdateOptional = userRepo.findById(id);

        if (userToUpdateOptional.isPresent()) {
            User updateUser = userRepo.findById(id).get();
            updateUser.setFirstName(user.getFirstName());
            updateUser.setLastName(user.getLastName());
            updateUser.setOccupation(user.getOccupation());
            userRepo.save(updateUser);
            return "user " + updateUser.getId() + " updated";

        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

    }
    @LogMethodEntry
    public String deleteUser(Integer id) {

        Optional<User> userToDeleteOptional = userRepo.findById(id);
        if (userToDeleteOptional.isPresent()) {
            User userToDelete = userToDeleteOptional.get();
            userRepo.delete(userToDelete);
            return "user " + userToDelete.getId() + " deleted.";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

    }

}
