package com.example.assetmanagementsystem.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.example.assetmanagementsystem.entities.Users;

@Component
public class CustomSecurityExpressions {

    public boolean isEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Users) {
            Users user = (Users) authentication.getPrincipal();
            return "EMPLOYEE".equals(user.getUserType());
        }
        return false;
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Users) {
            Users user = (Users) authentication.getPrincipal();
            return "ADMIN".equals(user.getUserType());
        }
        return false;
    }
}
