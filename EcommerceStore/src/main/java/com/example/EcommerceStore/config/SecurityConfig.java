package com.example.EcommerceStore.config;

import static org.springframework.security.config.Customizer.withDefaults;


import com.example.EcommerceStore.entity.User;
import com.example.EcommerceStore.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity

@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  private OurUserDetailsService userDetailsService;
@Autowired
private UserRepository  userRepository;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
      HttpSession session) throws Exception {
    httpSecurity.authorizeHttpRequests(auth -> auth
            .requestMatchers("/EcommerceStore/product", "/EcommerceStore/products/more/**",
                "/EcommerceStore/loginpage",
                "/EcommerceStore/productDetails/{product_id}", "/css/**", "/js/**", "/vendor/**",
                "/fonts/**", "/images/**", "/static/**", "/asset/**",
                "/EcommerceStore/register_form", "/EcommerceStore/register",
                "/EcommerceStore/otp_verify", "/EcommerceStore/search",
                "/EcommerceStore/productFilter/**","/EcommerceStore/productFilter/price/more/**"
                , "/EcommerceStore/productBrandFilter/**", "/EcommerceStore/productDetails/**",
                "/EcommerceStore/products/more", "/EcommerceStore/clean-booking/**").permitAll()
            .anyRequest().authenticated())
        .httpBasic(withDefaults())
        .formLogin(formLogin ->
            formLogin
                .loginPage("/EcommerceStore/loginpage")
                .permitAll()
                .loginProcessingUrl("/EcommerceStore/login")
                .defaultSuccessUrl("/EcommerceStore/product", true)
                .failureUrl("/EcommerceStore/loginpage?error=true")
                .failureHandler((request, response, exception) -> {
                  String errorMessage = "Invalid username or password";
                  request.getSession().setAttribute("errorMessage", errorMessage);
                  response.sendRedirect("/EcommerceStore/loginpage?error=true");
                })).logout(
            logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
        )
        .oauth2Login(oauth2 -> oauth2
            .loginPage("/login/oauth2/authorization/google")
            .defaultSuccessUrl("/EcommerceStore/product", true)
            .failureUrl("/EcommerceStore/loginpage?error=true")).logout(
            logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
        )
        .oauth2Login(oauth2 -> oauth2
            .loginPage("/login/oauth2/authorization/facebook")
            .defaultSuccessUrl("/EcommerceStore/product", true)
            .failureUrl("/EcommerceStore/loginpage?error=true")).logout(
            logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
        )
        .csrf(AbstractHttpConfigurer::disable)
        .addFilterAfter((request, response, chain) -> {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof OAuth2User) {
              OAuth2User oauth2User = (OAuth2User) principal;
              String email = (String) oauth2User.getAttribute("email");
              int id = userRepository.findUserByUserEmail(email).getUserId();
              session.setAttribute("user_id",id);

            } else  if(principal instanceof UserDetails userDetails)
            {
              String email = userDetails.getUsername();
              int id = userRepository.findUserByUserEmail(email).getUserId();
              session.setAttribute("user_id",id);
            }

          }
          chain.doFilter(request, response);
        }, BasicAuthenticationFilter.class); // Thêm filter sau BasicAuthenticationFilter
    ;

    return httpSecurity.build();


  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
    auth.setUserDetailsService(userDetailsService);
    auth.setPasswordEncoder(passwordEncoder());
    return auth;
  }

  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


}