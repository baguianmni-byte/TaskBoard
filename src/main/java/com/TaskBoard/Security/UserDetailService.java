package com.TaskBoard.Security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.TaskBoard.Model.User;
import com.TaskBoard.Repository.UserRepository;

// import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {

    
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Aucun compte pour l'email : " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("users")
                .build();
                //.authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
    }
}