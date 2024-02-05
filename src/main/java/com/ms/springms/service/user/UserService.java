package com.ms.springms.service.user;

import com.ms.springms.Exceptions.DuplicateEntryException;
import com.ms.springms.entity.UserInfo;
import com.ms.springms.repository.user.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Lazy
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> user = Optional.ofNullable(userRepository.findByUsername(username));
        return user.map(MyUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("Username Not Found" + username));
    }

    public String register(UserInfo userInfo) {
        try {
            String username = userInfo.getUsername();
            String password = userInfo.getPassword();

            // Periksa apakah username dan password tidak kosong atau tidak mengandung spasi
            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
                userInfo.setPassword(passwordEncoder.encode(password));
                userRepository.save(userInfo);
                return "User Added Successfully";
            } else {
                return "Username and password cannot be empty or contain spaces.";
            }
        } catch (DataIntegrityViolationException e) {
            // DataIntegrityViolationException is thrown when there is a constraint violation, such as a duplicate key
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("Duplicate username: " + userInfo.getUsername());
                throw new DuplicateEntryException("Username Already Used.");
            } else {
                System.out.println("Data integrity violation exception: " + e.getMessage());
                return "Error: Unable to Register. Please try again later.";
            }
        }
    }


    public List<UserInfo> getAllUser() {
        return userRepository.findAll();

    }

    public UserInfo getById(Long id) {
        return userRepository.findById(id).get();
    }


}
