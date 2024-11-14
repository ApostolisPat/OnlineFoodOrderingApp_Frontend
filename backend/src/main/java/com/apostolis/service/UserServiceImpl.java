package com.apostolis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apostolis.config.JwtProvider;
import com.apostolis.model.User;
import com.apostolis.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception{

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("user not found");
        }

        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception{
        
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("user not found");
        }
        
        return user;
    }

}
