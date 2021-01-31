package com.fabhotels.walletsystem.service;

import com.fabhotels.walletsystem.dao.UserDao;
import com.fabhotels.walletsystem.exceptions.UserAlreadyExistsException;
import com.fabhotels.walletsystem.models.dto.UserProfileDataDto;
import com.fabhotels.walletsystem.models.entity.User;
import com.fabhotels.walletsystem.models.entity.Wallet;
import com.fabhotels.walletsystem.models.requestobjects.UserLoginCredential;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private UserDao userDAO;

    /**
     * retrieve user by id
     *
     * @param id : the ID of the user to be fetched
     * @return the {@link User}
     * @throws ResponseStatusException {@code 404 (Not Found)} if no such Id in the database
     */
    @Override
    public User getUserById(Long id) {
        return userDAO.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user found for provided userId: "+id));
    }

    /**
     * save new user
     *
     * @param userProfileDataDto : user profile details
     * @return the {@link User}
     * @throws UserAlreadyExistsException {@code 409 (Conflict)} if a user already exists in DB
     * with any of the unique values
     */
    @Override
    public User saveNewUser(UserProfileDataDto userProfileDataDto) {
        //check if a user with an unique value already exists
        if (userDAO.findUserByEmail(userProfileDataDto.getEmail()).isPresent())
            throw new UserAlreadyExistsException("A user already exists with email: " + userProfileDataDto.getEmail());
        if (userDAO.findUserByUsername(userProfileDataDto.getUsername()).isPresent())
            throw new UserAlreadyExistsException("A user already exists with username: " + userProfileDataDto.getUsername());
        if (userDAO.findUserByMobileNumber(userProfileDataDto.getMobileNumber()).isPresent())
            throw new UserAlreadyExistsException("A user already exists with email: " + userProfileDataDto.getEmail());

        User userToAdd = modelMapper.map(userProfileDataDto, User.class);

        //always create a new wallet for newly registered user
        Wallet wallet = new Wallet();
        wallet.setWalletOwner(userToAdd);
        userToAdd.setWallet(wallet);
        User addedUser = userDAO.save(userToAdd);
        log.info("New User added with username: " + addedUser.getUsername());
        return addedUser;
    }

    /**
     * check if a user exists with provide user credentials viz username and password
     *
     * @param loginCredential : username and password
     * @return the {@link User}
     */
    @Override
    public User checkLoginCredentials(UserLoginCredential loginCredential) {
        //simple logic for authentication if a pair of username and password exists then
        //the user is authenticated else unauthenticated
        Optional<User> existingUser = userDAO.findUserByUsernameAndPassword(loginCredential.getUsername(),
                loginCredential.getPassword());
        if (existingUser.isPresent()) {
            log.info("New login for user with username: " + loginCredential.getUsername() + "at " + Instant.now() + " UTC");
            return existingUser.get();
        }
        else
            return null;
    }

    /**
     * update user profile details in DB
     *
     * @param userId : userId of user whose details to be updated
     * @param userProfileDataDto : user profile details
     * @return the {@link User}
     */
    @Override
    public UserProfileDataDto updateUserData(Long userId, UserProfileDataDto userProfileDataDto) {
        User user = getUserById(userId);
        //update with new values
        user.setPassword(userProfileDataDto.getPassword());
        user.setUsername(userProfileDataDto.getUsername());
        user.setEmail(userProfileDataDto.getEmail());
        user.setFirstName(userProfileDataDto.getFirstName());
        user.setLastName(userProfileDataDto.getLastName());
        user.setMobileNumber(userProfileDataDto.getMobileNumber());
        User updatedUser = userDAO.save(user);
        log.info("User profile updated for user with userId: "+userId);
        return modelMapper.map(updatedUser, UserProfileDataDto.class);
    }

    /**
     * get wallet details of user
     *
     * @param userId : userId of user whose wallet details to be fetched
     * @return the {@link Wallet}
     */
    @Override
    public Wallet getWalletInfoByUserId(Long userId) {
        return getUserById(userId).getWallet();
    }
}
