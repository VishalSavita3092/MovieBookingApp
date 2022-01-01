package com.upgrad.user.controller;

import com.upgrad.user.dto.UserDTO;
import com.upgrad.user.entities.User;
import com.upgrad.user.service.UserService;
import com.upgrad.user.utils.POJOConvertor;
import org.apache.tomcat.websocket.PojoClassHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user_app/v1")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Create a POST API
     */

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody UserDTO userDTO){
        User user = POJOConvertor.covertUserDTOToEntity(userDTO);
        User savedUser = userService.createUser(user);

        UserDTO savedUserDTO = POJOConvertor.covertUserEntityToDTO(user);
        return new ResponseEntity(savedUserDTO, HttpStatus.CREATED);
    }

    /**
     * Get the list of all users
     *
     * 127.0.0.1:8080/users_app/v1/users
     */

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllUsers(){
        List<User> userList = userService.getAllUsers();

        List<UserDTO> userDTOList = new ArrayList<>();
        userList.forEach(u -> userDTOList.add(POJOConvertor.covertUserEntityToDTO(u)));
        return new ResponseEntity(userDTOList, HttpStatus.OK);
    }

    /**
     * 127.0.0.1:8080/users_app/v1/users/{id}
     */

    @GetMapping(value = "/users/{id}")
    public ResponseEntity getUser(@PathVariable(name = "id") int id){
        User user = userService.getUserBasedOnId(id);

        UserDTO userDTO = POJOConvertor.covertUserEntityToDTO(user);
        return new ResponseEntity(userDTO, HttpStatus.OK);
    }

    /**
     * PUT Call
     * 127.0.0.1:8080/user_app/v1/users/{id}
     */

    @PutMapping(value = "/movies", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(int id,@RequestBody UserDTO userDTO){
        User userTOBeUpdated = POJOConvertor.covertUserDTOToEntity(userDTO);
        User savedUpdatedUser = userService.updateUser(userTOBeUpdated);
        UserDTO user = POJOConvertor.covertUserEntityToDTO(savedUpdatedUser);

        return new ResponseEntity(user, HttpStatus.ACCEPTED);
    }

    /**
     * 127.0.0.1:8080/user_app/v1/users/{id}
     */

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity deleteUser(@PathVariable(name = "id") int id){

        User user = userService.getUserBasedOnId(id);
        userService.deleteUser(user);

        return new ResponseEntity(null, HttpStatus.OK);
    }

}