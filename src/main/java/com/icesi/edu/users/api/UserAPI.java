package com.icesi.edu.users.api;

import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.validation.CustomAnnotations;
import org.springframework.data.repository.query.Param;
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
    public UserCreateDTO getUser(@PathVariable UUID userId);

    @PostMapping()
    public UserCreateDTO createUser(@RequestBody UserCreateDTO userCreateDTO);

    @GetMapping
    public List<UserDTO> getUsers();

}
