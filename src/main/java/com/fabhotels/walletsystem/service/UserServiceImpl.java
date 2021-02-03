package com.fabhotels.walletsystem.service;

import com.fabhotels.walletsystem.dao.UserDao;
import com.fabhotels.walletsystem.exceptions.UserAlreadyExistsException;
import com.fabhotels.walletsystem.models.dto.UserProfileCreateDto;
import com.fabhotels.walletsystem.models.dto.UserProfileUpdateDto;
import com.fabhotels.walletsystem.models.entity.User;
import com.fabhotels.walletsystem.models.entity.Wallet;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private UserDao userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserProfileCreateDto getUserProfileDetailsByUserName(String userName) {
        Optional<User> optionalUser = userDAO.findUserByUsername(userName);
        if(optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with useraname: "+userName);
        else
            return modelMapper.map(optionalUser.get(), UserProfileCreateDto.class);
    }


    /**
     * save new user
     *
     * @param userProfileCreateDto : user profile details
     * @return the {@link User}
     * @throws UserAlreadyExistsException {@code 409 (Conflict)} if a user already exists in DB
     * with any of the unique values
     */
    @Override
    public UserProfileCreateDto saveNewUser(UserProfileCreateDto userProfileCreateDto) {
        //check if a user with an unique value already exists
        if (userDAO.findUserByEmail(userProfileCreateDto.getEmail()).isPresent())
            throw new UserAlreadyExistsException("A user already exists with email: " + userProfileCreateDto.getEmail());
        if (userDAO.findUserByUsername(userProfileCreateDto.getUsername()).isPresent())
            throw new UserAlreadyExistsException("A user already exists with username: " + userProfileCreateDto.getUsername());
        if (userDAO.findUserByMobileNumber(userProfileCreateDto.getMobileNumber()).isPresent())
            throw new UserAlreadyExistsException("A user already exists with email: " + userProfileCreateDto.getEmail());

        User userToAdd = modelMapper.map(userProfileCreateDto, User.class);
        //save encrypted password
        userToAdd.setPassword(passwordEncoder.encode(userProfileCreateDto.getPassword()));

        //always create a new wallet for newly registered user
        Wallet wallet = new Wallet();
        wallet.setWalletOwner(userToAdd);
        userToAdd.setWallet(wallet);
        User addedUser = userDAO.save(userToAdd);
        log.info("New User added with username: " + addedUser.getUsername());
        return modelMapper.map(addedUser,UserProfileCreateDto.class);
    }

    /**
     * update user profile details in DB
     *
     * @param userName : username of user whose details to be updated
     * @param userProfileUpdateDto : user profile details to update
     */
    @Override
    public void updateUserData(String userName, UserProfileUpdateDto userProfileUpdateDto) {
        Optional<User> optionalUser = userDAO.findUserByUsername(userName);

        if(optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found with useraname: "+userName);

        User user = optionalUser.get();
        //do not update with null values
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        modelMapper.map(userProfileUpdateDto,user);

        //save encode password if password needs to be updated
        if(userProfileUpdateDto.getPassword()!=null)
            user.setPassword(passwordEncoder.encode(userProfileUpdateDto.getPassword()));
        //save with new values
        User updatedUser = userDAO.save(user);
        log.info("User profile updated for user with username: "+userName);
    }

}
