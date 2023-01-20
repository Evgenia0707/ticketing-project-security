package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//entity(User)
        User user = userRepository.findByUserNameAndIsDeleted(username, false);//how getting user from UserRep (need for SecutityConfig)

        if (user==null){
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
        //return user detail (spring obj) -- UserPrincipal do it (we wrote this under common package)
    }//get the user from DB (repository--business), and convert to user Spring understands by using userPrincipal class (Mapping)
    //add @Service, inject user Repository(retrive user from DB)
}
