package ru.gb.eshop.gb_eshop.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.gb.eshop.gb_eshop.services.PersonDetailsService;

@Configuration
public class SecurityConfig {
    private final PersonDetailsService personDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/authentication", "/registration", "/error", "/resources/**", "/static/**", "/css/**", "/js/**",
                        "/pics/**", "/images/**", "/product", "/product/info/{id}", "/product/search", "/product/searchHeader", "/logout").permitAll()
                //.anyRequest().hasAnyAuthority("USER", "ADMIN")
                .anyRequest()
                .authenticated()
        );
        http.formLogin(login -> login
                .loginPage("/authentication")
                //.usernameParameter("name")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/personalAccount", true)
                .failureUrl("/authentication?error")
                .permitAll());
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/authentication")
                .permitAll());
        return http.build();
    }

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncode());
    }
}