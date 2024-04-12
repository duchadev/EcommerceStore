package com.example.EcommerceStore;


import com.example.EcommerceStore.entity.User;
import com.example.EcommerceStore.repository.UserRepository;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DatabaseInit {
    private  static  final Logger logger = LoggerFactory.getLogger(DatabaseInit.class);
    @Bean
    CommandLineRunner intDB(UserRepository userRepository){
        return  new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                if(userRepository.existsByUserEmail("admin@admin.123")== null){
                    User admin = new User();
                    admin.setUserEmail("admin@admin.123");
                    admin.setUser_name("admin");
                    admin.setRoles("ADMIN");
                    admin.setVerified(1);
                    admin.setUser_phoneNumber("1999/01/01");
                    admin.setBirthday(null);
                    admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
                    userRepository.save(admin);
                }
            }
        };
    }

}
