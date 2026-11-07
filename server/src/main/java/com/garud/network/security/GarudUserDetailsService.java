package com.garud.network.security;

import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.garud.network.repository.UserRepository;

@Service
public class GarudUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public GarudUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {

        var user = userRepository.findByUsername(username)
            .orElseThrow(() ->
                new UsernameNotFoundException("Invalid credentials")
            );

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        user.getRoles().forEach(role -> {

            authorities.add(
                new SimpleGrantedAuthority(role.getName())
            );

            role.getPermissions().forEach(permission -> {

                authorities.add(
                    new SimpleGrantedAuthority(permission.getName())
                );

            });
        });

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities(authorities)
            .accountLocked(!user.isAccountNonLocked())
            .disabled(!user.isEnabled())
            .build();
    }
}
