package com.atm.controller;

import com.atm.dto.AuthenticatedUserDto;
import com.atm.dto.UserDto;
import com.atm.model.BankUser;
import com.atm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {
    private final ModelMapper modelMapper;
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody AuthenticatedUserDto bankUser) {
        BankUser registeredBankUser = userService.registerUser(bankUser);

        UserDto userDto = modelMapper.map(registeredBankUser, UserDto.class);

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody AuthenticatedUserDto bankUser) {
        BankUser loggedBankUser = userService.loginUser(bankUser);

        UserDto userDto = modelMapper.map(loggedBankUser, UserDto.class);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}

