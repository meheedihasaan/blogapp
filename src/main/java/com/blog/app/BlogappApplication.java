package com.blog.app;

import com.blog.app.configs.AppConstants;
import com.blog.app.entities.Role;
import com.blog.app.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@SpringBootApplication
public class BlogappApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlogappApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        //To print the encoded password
        System.out.println(passwordEncoder.encode("1234"));

        //To create roles
        try {
            Role role1 = new Role();
            role1.setId(AppConstants.ADMIN_USER);
            role1.setName("ROLE_ADMIN");

            Role role2 = new Role();
            role2.setId(AppConstants.NORMAL_USER);
            role2.setName("ROLE_NORMAL");

            this.roleRepository.saveAll(List.of(role1, role2));
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
