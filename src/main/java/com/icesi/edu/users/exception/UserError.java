package com.icesi.edu.users.exception;

import com.icesi.edu.users.constant.UserErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserError {

    private UserErrorCode userErrorCode;
    private String message;
}
