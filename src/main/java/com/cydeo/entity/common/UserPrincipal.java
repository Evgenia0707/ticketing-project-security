package com.cydeo.entity.common;

import com.cydeo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {//override implm - connect to user(tell Spring looking for our own User class) --will use like mapper
//user from DB - convert

    private User user; //entity// we use this obj

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//originally spring don't know what role /user to use (we tell using this GrantedAuthority)
        //   spring understand
        List<GrantedAuthority> authorityList = new ArrayList<>();
         //                                                           from DB
        GrantedAuthority authority = new SimpleGrantedAuthority((this.user.getRole().getDescription()));
        authorityList.add(authority);

        return authorityList;
    }

    @Override
    public String getPassword() {//get passw filed from DB (entity) and set to Spring secur passw filed
        return this.user.getPassWord(); //how I can access to password field of the user obj (create obj - has a relation)
        //
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {//not expired - true
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }

    public Long getId(){
        return this.user.getId();//mapp
    }
}
