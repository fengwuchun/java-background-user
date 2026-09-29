package com.demo.backend.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.backend.Utils.JwtUtil;
import com.demo.backend.common.redis.RedisUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final RedisUtils redisUtils;

    public JwtFilter(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        List<String> excludePaths = List.of(
            "/user/auth/login",
             "/user/auth/register",
             "/swagger-ui",
             "/v3/api-docs",
             "/swagger-resources",
             "/webjars"
            );
            if (excludePaths.contains(path)) {
                filterChain.doFilter(request, response);
                return;
            }
        // if ("/user/auth/login".equals(path) || "/user/auth/register".equals(path)) {
        //     filterChain.doFilter(request, response);
        //     return;
        // }

        String authorization = request.getHeader("Authorization");
        

        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            try {
                
                // 检查 Token 是否在黑名单
                String blacklistKey = "jwt:blacklist:" + token;

                if (redisUtils.exists(blacklistKey)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"message\":\"Token已退出登录\"}");
                    return;
                }

                Long id = JwtUtil.parseId(token);
                String username = JwtUtil.parseUsername(token);
                String role = JwtUtil.parseRole(token);
                LoginUser loginUser = new LoginUser(id, username, role);
                System.out.println("当前用户:"+loginUser);
                //权限Collections.emptyList() 目前没有配置权限
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
                        System.out.println("我的权限:"+authorities);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(loginUser, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\":\"Invalid token\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


}
