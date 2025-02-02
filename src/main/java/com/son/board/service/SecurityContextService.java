package com.son.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityContextService {
    private final UserDetailsServiceImpl userDetailsService;

    public void refreshUserDetails(String username) {
        UserDetailsImpl updatedUserDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                updatedUserDetails.getPassword(),
                updatedUserDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
