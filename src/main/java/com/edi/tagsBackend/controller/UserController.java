package com.edi.tagsBackend.controller;

import com.edi.tagsBackend.config.UserDetailsServiceImpl;
import com.edi.tagsBackend.models.*;
import com.edi.tagsBackend.service.UserService;
import com.edi.tagsBackend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO registerUserDTO) throws Exception {
        if (userService.findByEmail(registerUserDTO.getEmail()) != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage("User already exists."));
        else if (registerUserDTO.getEmail()==null || registerUserDTO.getPassword()==null ||  registerUserDTO.getUsername()==null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage("Check your input."));
        User user = new User();
        user.setEmail(registerUserDTO.getEmail());
        user.setUsername(registerUserDTO.getUsername());
        user.setPassword(registerUserDTO.getPassword());
        userService.save(user);

        return ResponseEntity.ok("User " + user.getUsername() + " registered.");
    }

}
