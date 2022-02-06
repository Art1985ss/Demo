package com.art.demo.service;

import com.art.demo.model.User;
import com.art.demo.model.UserPrincipal;
import com.art.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user =  userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
        return new UserPrincipal(user);
    }
}
