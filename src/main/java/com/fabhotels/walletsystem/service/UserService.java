package com.fabhotels.walletsystem.service;

import com.fabhotels.walletsystem.models.entity.User;
import com.fabhotels.walletsystem.models.dto.UserProfileDataDto;
import com.fabhotels.walletsystem.models.entity.Wallet;
import com.fabhotels.walletsystem.models.requestobjects.UserLoginCredential;

public interface UserService {
    User getUserById(Long id);
    User saveNewUser(UserProfileDataDto userProfileDataDto);
    User checkLoginCredentials(UserLoginCredential userLoginCredential);
    UserProfileDataDto updateUserData(Long userId, UserProfileDataDto userProfileDataDto);
    Wallet getWalletInfoByUserId(Long userId);
}
