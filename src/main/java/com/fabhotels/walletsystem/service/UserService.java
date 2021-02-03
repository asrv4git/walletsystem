package com.fabhotels.walletsystem.service;

import com.fabhotels.walletsystem.models.dto.UserProfileCreateDto;
import com.fabhotels.walletsystem.models.dto.UserProfileUpdateDto;

public interface UserService {
    UserProfileCreateDto getUserProfileDetailsByUserName(String userName);
    UserProfileCreateDto saveNewUser(UserProfileCreateDto userProfileCreateDto);
    void updateUserData(String userName, UserProfileUpdateDto userProfileUpdateDto);
}
