package com.apostolis.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.apostolis.config.JwtProvider;
import com.apostolis.model.Cart;
import com.apostolis.model.USER_ROLE;
import com.apostolis.model.User;
import com.apostolis.repository.CartRepository;
import com.apostolis.repository.UserRepository;
import com.apostolis.request.LoginRequest;
import com.apostolis.response.AuthResponse;
import com.apostolis.service.CustomerUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception{

        //If a user with this email exists, we won't allow another user with the same email to be created
        User isEmailExist = userRepository.findByEmail(user.getEmail()); 
        if(isEmailExist!=null){
            throw new Exception("Email already used with another account.");
        }

        //Create user
        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        //Save user to database
        User savedUser = userRepository.save(createdUser);

        //Create cart for new user
        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        /*
         * Authenticates the user with their email and password and 
         * sets the authentication context, allowing the application to recognize the user as 
         * authenticated for subsequent requests.
         */
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(savedUser.getRole());
        
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest req ){

        String username = req.getEmail();
        String password = req.getPassword();

        //Match provided credentials with user from database
        Authentication authentication = authenticate(username,password); 

        //Get user role and save it to role
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        
        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Log in successfull");
        authResponse.setRole(USER_ROLE.valueOf(role));
        
        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }
        
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
        
        if(userDetails==null){
            throw new BadCredentialsException("Invalid username.");
        }

        
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    
}
