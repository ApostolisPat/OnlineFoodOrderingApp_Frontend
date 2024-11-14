package com.apostolis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apostolis.model.USER_ROLE;
import com.apostolis.model.User;
import com.apostolis.repository.UserRepository;

//To stop auto-generating password when starting SpringBoot service (because we implemented UserDetailsService)
@Service
public class CustomerUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username); //Check if user exists with this username
        if(user==null){
            throw new UsernameNotFoundException("user not found with email: " + username);
        }

        USER_ROLE role = user.getRole(); //Collect the role

        List<GrantedAuthority> authorities = new ArrayList<>(); //Empty GrantedAuthority, inside will add the role

        authorities.add(new SimpleGrantedAuthority(role.toString())); //Add role


        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

}
