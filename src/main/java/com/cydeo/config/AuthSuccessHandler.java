package com.cydeo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

 @Component
// @Configuration// dont have to put it(only need Bean), can use component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {//polimor - inject in SecurConfig

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //what user access, manager, employee//after success login where to land  // can use List<> - in User we have assign only 1 user have 1 role
        //Spring provide Set<> - why we use it too
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());//catch user role (user can have more than 1 role)

        if (roles.contains("Admin")){
            response.sendRedirect("/user/create");//sent this user to /user/create" page
        }
        if (roles.contains("Manager")){
            response.sendRedirect("/task/create");
        }
        if (roles.contains("Employee")){
            response.sendRedirect("/task/employee/pending-tasks");
        }
    }
}