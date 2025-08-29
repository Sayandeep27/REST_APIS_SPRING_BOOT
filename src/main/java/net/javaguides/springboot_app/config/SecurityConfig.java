package net.javaguides.springboot_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// mvn org.springframework.boot:spring-boot-maven-plugin:3.5.4:run


@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")   // password
                .roles("ADMIN")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user123")   // password
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // allow h2-console access without authentication
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()   // H2 console accessible
                .requestMatchers("/admin/**").hasRole("ADMIN")   // only admin can access
                .anyRequest().permitAll()                       // others are public
            )
            .formLogin(Customizer.withDefaults())  // login form
            .logout(Customizer.withDefaults());   // logout support

        // H2 console needs these:
        http.csrf(csrf -> csrf.disable()); // disable CSRF for H2 console
        http.headers(headers -> headers.frameOptions(frame -> frame.disable())); // allow frames

        return http.build();
    }
}
