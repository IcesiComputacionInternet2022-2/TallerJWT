package com.icesi.edu.users.api;

import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserSensibleDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RequestMapping("/users")
public interface UserAPI {

    @GetMapping("/{userId}")
    public UserSensibleDTO getUser(@PathVariable UUID userId);

    @PostMapping()
    public UserSensibleDTO createUser(@RequestBody UserSensibleDTO userDTO);

    @GetMapping
    public List<UserDTO> getUsers();

}
