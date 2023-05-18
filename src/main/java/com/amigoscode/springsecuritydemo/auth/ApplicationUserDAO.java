package com.amigoscode.springsecuritydemo.auth;

import java.util.Optional;

public interface ApplicationUserDAO {
    Optional<ApplicationUser> selectApplicationUserByUserName(String username);
}
