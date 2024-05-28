package com.hardeymorlah.Logistics.Service;

import com.hardeymorlah.Logistics.Model.DTOs.*;
import com.hardeymorlah.Logistics.Model.User;
import com.hardeymorlah.Logistics.Repository.UserRepository;
import com.hardeymorlah.Logistics.config.AccountConfiguration;
import com.hardeymorlah.Logistics.exception.RegistrationException;
import jakarta.mail.MessagingException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountConfiguration accountConfiguration;
    public ResponseEntity<List<User>> findAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
    public ResponseEntity<User> getUserById(long id) {
        return new ResponseEntity<>(userRepository.findById(id).orElseThrow(), HttpStatus.OK);

    }
    public ResponseEntity<User> getUserByEmailAndPassword(String email) {
        return new ResponseEntity<>(userRepository.findUserByEmail(email), HttpStatus.OK);
    }
    public ResponseEntity<User> getUserByUsername(String username) {
        return new ResponseEntity<>(userRepository.findUserByUsername(username), HttpStatus.OK);
    }

    public ResponseEntity<String> registerAdmin(AdminRegistrationRequest request) {
        passwordEncoder = accountConfiguration.passwordEncoder();
        String password = (passwordEncoder.encode(request.getPassword()));
        if (request.getEmail().endsWith("@admin.tech.ng")) {
            User adminUser = new User();
            adminUser.setUsername(request.getUsername());
            adminUser.setEmail(request.getEmail());
            adminUser.setPassword(password);
            adminUser.setRole(Role.ADMIN);
            userRepository.save(adminUser);
            return ResponseEntity.ok("Admin registered successfully");
        }else throw new RegistrationException("Access Denied!!. For Admin Users Only");

    }

    public ResponseEntity<LoginResponse> authenticate(LoginRequest request) throws MessagingException {

        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if( auth != null ){
            User user = userRepository.findUserByUsername(request.getUsername());
            System.out.println(user);
            String token = jwtService.createToken(user);
            System.out.println(token);
//            messageService.loginNotification(user.getUsername(), STR."Dear \{user.getUsername()}\nThere has been a successful login into your Banking Account. Please if you did not log in call us on the following number: 01-2245845, 08004455454\nThank you for Banking with Us.");
            return new ResponseEntity<>(LoginResponse.builder().user(user).token(token).build(), HttpStatus.OK);
        } else {
            System.out.println("auth is null");
        }

        return null;
    }
    public ResponseEntity<String> registerUser(UserRegistrationRequest request) {
        passwordEncoder = accountConfiguration.passwordEncoder();
        String password = (passwordEncoder.encode(request.getPassword()));
        if (!request.getEmail().endsWith("@admin.tech.ng")) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(password);
            user.setRole(Role.USER);
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully");
        }else throw new RegistrationException("Access Denied!!. For Users Only");

    }

    public ResponseEntity<User> updateUser(long id, User user) {
        passwordEncoder = accountConfiguration.passwordEncoder();
        String password = (passwordEncoder.encode(user.getPassword()));
        User updateDetails = userRepository.findById(id).orElseThrow();
        updateDetails.setUsername(user.getUsername());
        updateDetails.setEmail(user.getEmail());
        updateDetails.setPassword(password);
        if (updateDetails.getRole().equals(Role.ADMIN)) {
            updateDetails.setRole(Role.ADMIN);
        }else if (updateDetails .getRole().equals(Role.USER)){
            updateDetails.setRole(Role.USER);
        }
        return new ResponseEntity<>(userRepository.save(updateDetails),HttpStatus.ACCEPTED);
    }
    public ResponseEntity<User> deleteUser(long id) {
        User deleteUser = userRepository.findById(id).orElseThrow();
        userRepository.deleteById(id);
        return new ResponseEntity<>(deleteUser, HttpStatus.OK);

    }
}
