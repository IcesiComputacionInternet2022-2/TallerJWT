package com.icesi.edu.users.controller;

import com.icesi.edu.users.api.UserAPI;
import com.icesi.edu.users.constant.UserErrorCode;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.dto.UserWithoutPasswordDTO;
import com.icesi.edu.users.mapper.UserMapper;
import com.icesi.edu.users.security.SecurityContextHolder;
import com.icesi.edu.users.service.UserService;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {

    public final UserService userService;
    public final UserMapper userMapper;

    @Override
    public UserDTO getUser(UUID userId) {
        if (SecurityContextHolder.getContext().getUserId().equals(userId))
            return userMapper.fromUser(userService.getUser(userId));
        throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("08", UserErrorCode.CODE_UD_08));
    }

    @Override
    public UserDTO createUser(@Valid UserDTO userDTO) {
        validateUser(userDTO);
        return userMapper.fromUser(userService.createUser(userMapper.fromDTO(userDTO)));
    }

    @Override
    public List<UserWithoutPasswordDTO> getUsers() {
        return userService.getUsers().stream().map(userMapper::fromUserWithPassword).collect(Collectors.toList());
    }

    private void validateUser(UserDTO userDTO) {
        if (Optional.ofNullable(userDTO.getEmail()).isEmpty() && Optional.ofNullable(userDTO.getPhoneNumber()).isEmpty()) {
            throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("01", UserErrorCode.CODE_UD_01));
        }
        validateEmail(userDTO.getEmail());
        validatePhoneNumber(userDTO.getPhoneNumber());
        validateNames(userDTO.getFirstName(), userDTO.getLastName());
    }

    private void validateEmail(String userEmail) {
        Optional.ofNullable(userEmail).ifPresent(email -> {
            if (!email.matches("\\w+\\.?\\w+@icesi\\.edu\\.co$"))
                throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("02", UserErrorCode.CODE_UD_02));
        });
    }

    private void validatePhoneNumber(String userPhoneNumber) {
        Optional.ofNullable(userPhoneNumber).ifPresent(phoneNumber -> {
            if (!phoneNumber.matches("^\\+57\\d{10}"))
                throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("03", UserErrorCode.CODE_UD_03));
        });
    }

    private void validateNames(String userFirstName, String userLastName) {
        Optional.ofNullable(userFirstName).ifPresent(firstName -> {
            if (!firstName.matches("[a-zA-Z]{0,120}"))
                throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("04", UserErrorCode.CODE_UD_04));
        });
        Optional.ofNullable(userLastName).ifPresent(lastName -> {
            if (!lastName.matches("[a-zA-Z]{0,120}"))
                throw new UserDemoException(HttpStatus.BAD_REQUEST, new UserDemoError("04", UserErrorCode.CODE_UD_04));
        });
    }
}