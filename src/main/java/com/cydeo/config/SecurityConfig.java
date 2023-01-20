package com.cydeo.config;

import org.springframework.security.core.userdetails.User;
import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration//incl @Component
public class SecurityConfig {//bigger () implementation//Bean//won provide my user, convert userDTO to Spring standard
    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }
////don't wont use hard coded users - want to use users from tables//entity - create class UserPrincipal under commom pack

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder){//create user untontac//override - for use my user
//        //Encoded means it is converting that password into something no one can understand or read.
//        //Saving it in an encrypted way.
//
//        List<UserDetails> userList = new ArrayList<>();//create users (manually)new spring user
//
//// import --rg.springframework.security.core.userdetails.User;// need constr //User = Spring user NOT ours
//        //cannot direct put password - need encoded --encoder.encode("password") - save Long character
//        //authorities - how define authority in list
//        userList.add(  //  provide const info - we choose 3 things          define authority           Spr understand
//                new User("mike",encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")))//this credential we use in appl
//        );
//        userList.add(
//                new User("ozzy",encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")))//SimpleGrantedAuthority - provide role
//        );
//        return new InMemoryUserDetailsManager(userList);    //impl UserDetailService - when will be saved - in the memory (not DB)
//    }// we override User class with encoded password - create our own User (mike) with password


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {//define filtering staff//HttpSecurity - from Spring
        return http
                .authorizeRequests()  //everything need to be authorized //where run security need authorize each pages//
//                .antMatchers("/user/**").hasRole("ADMIN")//access people sertan role(Spring auto add ROLE_)//anything under user controller will be accessible by Admine
                .antMatchers("/user/**").hasAuthority("Admin")//for match with DB (user- end point- all under user **)//antMatchers-give endPoint and what role can it use
                .antMatchers("/project/**").hasRole("Manager")//only manager user can access to this page
                .antMatchers("/task/employee/**").hasRole("Employee")
                .antMatchers("/task/**").hasRole("Manager")
//                .antMatchers("/task/**").hasAnyRole("EMPLOYEE", "ADMIN")
//                .antMatchers("/task/**").hasAuthority("ROLE_EMPLOYEE")//need to match with SimpleGrantedAuthority("ROLE_ADMIN")35 dont concantin auto wiht _
                .antMatchers(  //exclude pages
//                        "/",//when login + remember -> logout - use need to login again -> not show user creater(ex) page (+change in weconfig- addViewController("/").setViewName("welcome");
                        "/login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                ).permitAll()//- access for everyone
                .anyRequest().authenticated() //other need be authentic.
                .and()
//                .httpBasic()        //what wont use//default user name/sign in popUp box
                .formLogin()//our own login
                    .loginPage("/login")
//                    .defaultSuccessUrl("/welcome")//after login - where we are landing (we need separate by role)
                    .successHandler(authSuccessHandler)//when success land on diff page - depend on role - inject AuthSuccessHandler class and pass instance (param)
                    .failureUrl("/login?error=true")//if incorrect login - send user to this (we use error=true)
                    .permitAll()//for all users
                .and()//for combine parts(and)
                .logout()//what needs to be known by screen
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//for page logout(button) endPoint to - accept new AntPathRequestMatcher
                    .logoutSuccessUrl("/login")//when success - show login form
                .and()
                .rememberMe()
                    .tokenValiditySeconds(120)//hold long - 120 sec
                    .key("cydeo")//use to create token (tokenV) any key- save from crack - safety (can create s-q, and login in appl) (only devel know key)
                    .userDetailsService(securityService)//remember who- inject (()inside - know which user valid in the system)
                    .and()
                .build();//when finish
    }

}

