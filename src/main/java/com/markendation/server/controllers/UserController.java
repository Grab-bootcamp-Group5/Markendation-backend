package com.markendation.server.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.markendation.server.classes.Location;
import com.markendation.server.dto.UserDto;
import com.markendation.server.services.UserService;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<UserDto> getUserInformation(@AuthenticationPrincipal UserDetails userDetails) throws IOException {
        UserDto response = userService.getInfo(userDetails.getUsername());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/location")
    public ResponseEntity<Void> updateLocation(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Location location) throws IOException {
        userService.updateLocation(userDetails.getUsername(), location);
        return ResponseEntity.accepted().build();
    }
}
