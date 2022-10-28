package com.icesi.edu.users.api;

import com.icesi.edu.users.dto.TokenDTO;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserWithPasswordDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/users")
public interface UserAPI {

    @GetMapping("/{userId}")
    UserWithPasswordDTO getUser(@PathVariable UUID userId, @RequestHeader TokenDTO authorization); //Validar que el id sea el del propio usuario

    @PostMapping()
    UserWithPasswordDTO createUser(@RequestBody UserWithPasswordDTO userWithPasswordDTO);

    @GetMapping//Hacer que retorne
    List<UserDTO> getUsers();

}
