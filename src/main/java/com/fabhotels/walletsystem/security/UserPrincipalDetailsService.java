package com.fabhotels.walletsystem.security;

import com.fabhotels.walletsystem.dao.UserDao;
import com.fabhotels.walletsystem.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userDao.findUserByUsername(username);
        if(optionalUser.isEmpty())
            throw new UsernameNotFoundException(username+" doesn't exist");
        UserPrincipal userPrincipal = new UserPrincipal(optionalUser.get());
        return userPrincipal;
    }
}
