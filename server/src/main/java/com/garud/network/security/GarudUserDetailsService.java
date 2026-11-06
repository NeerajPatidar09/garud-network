package com.garud.network.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.garud.network.model.User;
import com.garud.network.repository.UserRepository;

@Service
public class GarudUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;

    public GarudUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                    new UsernameNotFoundException("User not found")
                );

        return org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPasswordHash())
        .authorities(
            user.getRoles().stream()
                .flatMap(role -> {

                    Stream<String> roleAuthority =
                            Stream.of(role.getName());

                    Stream<String> permissionAuthorities =
                            role.getPermissions().stream()
                                .map(Permission::getName);

                    return Stream.concat(
                            roleAuthority,
                            permissionAuthorities
                    );
                })
                .distinct()
                .toArray(String[]::new)
        )
        .accountLocked(!user.isAccountNonLocked())
        .disabled(!user.isEnabled())
        .build();
    }
}
