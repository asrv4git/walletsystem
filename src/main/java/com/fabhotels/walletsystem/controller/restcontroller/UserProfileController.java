package com.fabhotels.walletsystem.controller.restcontroller;

import com.fabhotels.walletsystem.models.dto.UserProfileDataDto;
import com.fabhotels.walletsystem.models.entity.Wallet;
import com.fabhotels.walletsystem.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/"+"${apiversion}/user")
public class UserProfileController {

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @ApiOperation("Get profile details of corresponding user")
    @ApiResponse(code = 200, message = "profile details of corresponding user")
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDataDto> getUserDetails(@PathVariable @NotBlank Long id){
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id),UserProfileDataDto.class));
    }

    @ApiOperation("Register a new user")
    @ApiResponse(code = 201, message = "successful registration")
    @PostMapping("/register")
    public ResponseEntity<UserProfileDataDto> addNewUser(@RequestBody @Valid UserProfileDataDto userProfileDataDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                modelMapper.map(userService.saveNewUser(userProfileDataDto),UserProfileDataDto.class));
    }

    /*@ApiOperation("login with username and password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "correct login credentials"),
            @ApiResponse(code = 401, message = "incorrect login credentials")
    })
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody @Valid UserLoginCredential loginCredential){
        User user = userService.checkLoginCredentials(loginCredential);
        if(user==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        else
            return ResponseEntity.ok().build();
    }*/

    @ApiOperation("update user profile details")
    @ApiResponse(code = 200, message = "update successful")
    @PatchMapping("/update/{id}")
    public ResponseEntity<UserProfileDataDto> updateUser(@PathVariable @NotNull Long id,
                                                         @RequestBody @Valid UserProfileDataDto userProfileDataDto){
        UserProfileDataDto userData = userService.updateUserData(id, userProfileDataDto);
        return ResponseEntity.ok(userData);
    }

    @ApiOperation("get wallet details for user")
    @ApiResponse(code = 200, message = "retrieval of wallet details successful")
    @GetMapping("/wallet/{userId}")
    public ResponseEntity<Wallet> getWalletInfoForUser(@PathVariable @NotNull Long userId){
        return ResponseEntity.ok(userService.getWalletInfoByUserId(userId));
    }
}
