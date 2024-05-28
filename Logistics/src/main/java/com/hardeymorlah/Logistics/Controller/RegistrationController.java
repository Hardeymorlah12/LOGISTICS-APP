package com.hardeymorlah.Logistics.Controller;

import com.hardeymorlah.Logistics.Model.DTOs.*;
import com.hardeymorlah.Logistics.Model.User;
import com.hardeymorlah.Logistics.Service.RegistrationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import com.hardeymorlah.Logistics.Model.DTOs.Role;

import java.util.List;

@RestController
@Secured({"ROLE_ADMIN", "ROLE_USER"})
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @GetMapping("/all_users")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<User>> getAllUsers() {
        return registrationService.findAllUsers();
    }
    @GetMapping("/user_by_id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return registrationService.getUserById(id);
    }
    @GetMapping("/user_by_username")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        return registrationService.getUserByUsername(username);
    }
    @GetMapping("/user_by_email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return registrationService.getUserByEmailAndPassword(email);
    }
    @PostMapping("/register_admin")
   public ResponseEntity<String> createAdminUser(@RequestBody @Valid AdminRegistrationRequest request) {
        return registrationService.registerAdmin(request);
   }
    @PostMapping("/register_user")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserRegistrationRequest request) {
        return registrationService.registerUser(request);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate (@RequestBody LoginRequest loginRequest) throws MessagingException {
        return registrationService.authenticate(loginRequest);
    }
    @PutMapping("/update_user/{id}")
    public ResponseEntity<User> updateUser( @PathVariable long id,@Valid @RequestBody User user) {
        return registrationService.updateUser(id, user);
    }
    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        return registrationService.deleteUser(id);
    }
}
