package com.ms.springms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.springms.Exceptions.DuplicateEntryException;
import com.ms.springms.entity.UserInfo;
import com.ms.springms.model.auth.AuthRequest;
import com.ms.springms.service.jwt.JwtService;
import com.ms.springms.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody UserInfo userInfo) {
        try {
            String result = userService.register(userInfo);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (DuplicateEntryException e) {
            return new ResponseEntity<>("Awards Already Exist" + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: Unable to Register. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) throws JsonProcessingException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication); // Set authentication
                System.out.println("Logged in user: " + authRequest.getUsername());
                return ResponseEntity.ok(jwtService.generateToken(authRequest.getUsername()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
            }
        } catch (UsernameNotFoundException ex) {
            // Handle case where username is not found
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username not found"+ ex.getMessage());
        } catch (Exception ex) {
            // Handle other authentication failures
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Fail " + ex.getMessage());
        }
    }




    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);

        if (token != null) {
            // Add the token to the blacklist
            String username = jwtService.extractUsername(token);
            jwtService.addToBlackList(token);

            System.out.println("USER : " + username + " " + "logged Out");
            System.out.println("Token added to blacklist: " +   token);
            System.out.println("LOGOUT");

            SecurityContextHolder.clearContext();

            return ResponseEntity.ok("Logged out successfully");
        } else {
            return ResponseEntity.badRequest().body("Token not found in the request");
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }




    @GetMapping("/getUsers")
    public List<UserInfo> getAllUsers(@RequestHeader("Authorization") String token){
        return userService.getAllUser();
    }


    @GetMapping("/getUsers/{id}")
    public UserInfo getById(@RequestHeader("Authorization") String token, @PathVariable Long id){
        return userService.getById(id);
    }


}
