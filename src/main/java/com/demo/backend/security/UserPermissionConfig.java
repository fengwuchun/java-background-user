package com.demo.backend.security;

public class UserPermissionConfig {

    public static boolean hasPermission(String currentEmial, String targetEmail, String role) {
        if (role.equals("ADMIN")) {
            return true;
        }
        return currentEmial.equals(targetEmail);

    }

       public static boolean hasPermission(Long currentId, Long targetId, String role) {
        if (role.equals("ADMIN")) {
            return true;
        }
        return currentId.equals(targetId);

    }
}
