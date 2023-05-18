package com.amigoscode.springsecuritydemo.security;

import com.amigoscode.springsecuritydemo.auth.ApplicationUserService;
import com.amigoscode.springsecuritydemo.jwt.JwtConfig;
import com.amigoscode.springsecuritydemo.jwt.JwtSecretKey;
import com.amigoscode.springsecuritydemo.jwt.JwtTokenVerifier;
import com.amigoscode.springsecuritydemo.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.amigoscode.springsecuritydemo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    private final JwtConfig jwtConfig;

    private final JwtSecretKey jwtSecretKey;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder,
                                 ApplicationUserService applicationUserService,
                                 JwtConfig jwtConfig,
                                 JwtSecretKey jwtSecretKey) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.jwtConfig = jwtConfig;
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, jwtSecretKey.secretKey()))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig, jwtSecretKey.secretKey()),
                        JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
//                .antMatchers(HttpMethod.POST, "/management/api/**")
//                .hasAuthority(COURSE_WRITE.getPermission()).antMatchers(HttpMethod.PUT, "/management/api/**")
//                .hasAuthority(COURSE_WRITE.getPermission()).antMatchers(HttpMethod.DELETE, "/management/api/**")
//                .hasAuthority(COURSE_WRITE.getPermission()).antMatchers(HttpMethod.GET, "/management/api/**")
//                .hasAnyRole(ADMIN.name(), ADMIN_TRAINEE.name())
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
   public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setPasswordEncoder(passwordEncoder);
       provider.setUserDetailsService(applicationUserService);
       return provider;
   }
}
