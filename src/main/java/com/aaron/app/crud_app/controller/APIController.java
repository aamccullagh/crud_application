package com.aaron.app.crud_app.controller;
import com.aaron.app.crud_app.models.User;
import com.aaron.app.crud_app.service.ApplicationServiceImplementation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "CRUD Application for User DB")
@RestController
public class APIController {

    @Autowired
    ApplicationServiceImplementation service;

    @ApiOperation(value = "View a list of available users", response = List.class)
    @GetMapping("/read")
    public List<User> getUsers(){
        return service.getUsers();
    }

    @ApiOperation(value = "Add a user")
    @PostMapping("/create")
    public String createUser(@Valid @RequestBody User user ){return service.addUser(user);}

    @ApiOperation(value = "Update a user")
    @PutMapping("/update/{id}")
    public String updateUser(@PathVariable Integer id, @Valid @RequestBody User user){
        return service.updateUser(id,user);
    }

    @ApiOperation(value = "Delete a user")
    @DeleteMapping("delete/{id}")
    public String deleteUser(@PathVariable Integer id) { return service.deleteUser(id);}

    }
