package com.amigoscode.springsecuritydemo.auth;

import com.amigoscode.springsecuritydemo.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("fake")
public class FakeApplicationUserDAOService implements ApplicationUserDAO {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public FakeApplicationUserDAOService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUserName(String username) {
        return getApplicationUser().stream()
                                   .filter(applicationUser -> applicationUser.getUsername()
                                                                             .equals(username))
                                   .findFirst();
    }

    public List<ApplicationUser> getApplicationUser() {
        return List.of(
                new ApplicationUser(ApplicationUserRole.STUDENT.getAllGrantedAuthorities(),
                        passwordEncoder.encode("password"),
                        "alexa",
                        true,
                        true,
                        true,
                        true
                ), new ApplicationUser(ApplicationUserRole.ADMIN.getAllGrantedAuthorities(),
                        passwordEncoder.encode("password123"),
                        "brie",
                        true,
                        true,
                        true,
                        true
                ), new ApplicationUser(ApplicationUserRole.ADMIN_TRAINEE.getAllGrantedAuthorities(),
                        passwordEncoder.encode("password123"),
                        "john",
                        true,
                        true,
                        true,
                        true
                )
        );
    }
}
