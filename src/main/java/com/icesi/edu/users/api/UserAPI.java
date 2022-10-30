package com.icesi.edu.users.api;

import com.icesi.edu.users.dto.UserCreateDTO;
import com.icesi.edu.users.dto.UserDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("/users")
public interface UserAPI {

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable UUID userId);

    @PostMapping()
    public UserDTO createUser(@RequestBody @Valid UserCreateDTO userCreateDTO);

    @GetMapping
    public List<UserDTO> getUsers();
}
