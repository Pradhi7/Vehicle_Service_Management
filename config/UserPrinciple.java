package com.examly.springapp.config;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
 
import com.examly.springapp.model.User;
 
public class UserPrinciple implements UserDetails{
 
 
    private String username ;
    private String password;
    private List<GrantedAuthority> authorities = new ArrayList<>();
 

    public UserPrinciple(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getUserRole()));
    }
   
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return this.authorities;
    }
 
    @Override
    public String getPassword() {
       return this.password;
    }
 
    @Override
    public String getUsername() {
        return this.username;
    }
 
 
    @Override
    public boolean isAccountNonExpired() {
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
        return true;
    }
   
}
 
 