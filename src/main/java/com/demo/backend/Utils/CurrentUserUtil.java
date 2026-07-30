package com.demo.backend.Utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.demo.backend.security.LoginUser;

@Component
public class CurrentUserUtil {

  public static LoginUser get() {
    Authentication authentication = SecurityContextHolder
        .getContext()
        .getAuthentication();
    if (authentication != null &&
        authentication.getPrincipal() instanceof LoginUser) {
      return (LoginUser) authentication.getPrincipal();
    }
    return null;
  }

  public static Long getId() {

    return get().getId();

  }

  public static  String getEmail() {

    return get().getEmail();

  }

  public static String getRole() {

    return get().getRole();

  }
}
