package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication  //this includes @Configuration
public class TicketingProjectSecurityApplication {

    public static void main(String[] args) {

        SpringApplication.run(TicketingProjectSecurityApplication.class, args);
    }

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

    @Bean//belong to Spring not me
    public PasswordEncoder passwordEncoder(){//interface
        return new BCryptPasswordEncoder();//need impl (use polymor for create obj) - responsible for take our password and put logic , and change to encoded structure //return obj
    }
    //Whenever we create a user in the user interface, Abc1 password in the database
    // we cannot save as Abc1 we need to save as an encoded.
    // How I will make this encoded? by using this method which is coming from this object.
    //use in the UserServImpl - for convert pass to encoded
}
