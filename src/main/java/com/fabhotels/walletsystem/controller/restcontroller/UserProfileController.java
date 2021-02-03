package com.fabhotels.walletsystem.controller.restcontroller;

import com.fabhotels.walletsystem.models.dto.UserProfileCreateDto;
import com.fabhotels.walletsystem.models.dto.UserProfileUpdateDto;
import com.fabhotels.walletsystem.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/"+"${apiversion}/user")
public class UserProfileController {

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @ApiOperation("Get profile details of authenticated user")
    @ApiResponse(code = 200, message = "profile details of authenticated user")
    @GetMapping("/profile")
    public ResponseEntity<UserProfileCreateDto> getUserDetails(Principal principal){
        //authenticated user can only view his/her details
        String userName = principal.getName();
        return ResponseEntity.ok(userService.getUserProfileDetailsByUserName(userName));
    }

    @ApiOperation("Register a new user")
    @ApiResponse(code = 201, message = "successful registration")
    @PostMapping("/signup")
    public ResponseEntity<UserProfileCreateDto> addNewUser(@RequestBody @Valid UserProfileCreateDto userProfileCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.saveNewUser(userProfileCreateDto));
    }

    @ApiOperation("update user profile details")
    @ApiResponse(code = 200, message = "update successful")
    @PatchMapping("/update")
    public ResponseEntity updateUser(Principal principal, @RequestBody @Valid UserProfileUpdateDto userProfileUpdateDto){
        userService.updateUserData(principal.getName(), userProfileUpdateDto);
        return ResponseEntity.ok().build();
    }
}
