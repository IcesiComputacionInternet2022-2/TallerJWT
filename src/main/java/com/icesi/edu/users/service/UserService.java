package com.icesi.edu.users.service;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface UserService {


    public User getUser(@PathVariable UUID userId);

    public User createUser(@RequestBody User userDTO);

    public List<User> getUsers();
}
