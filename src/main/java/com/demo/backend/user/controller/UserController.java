package com.demo.backend.user.controller;
import com.demo.backend.common.result.HttpResult;
import com.demo.backend.user.dto.RegisterRequest;
import com.demo.backend.user.entity.User;
import com.demo.backend.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.*;

import com.demo.backend.Utils.JwtUtil;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public HttpResult<String> login(@RequestBody Map<String, String> loginRequest) {
        System.out.println("----------进入了login----------");
        String username = loginRequest.get("email");
        String password = loginRequest.get("password");
        System.out.println("登录时用户邮箱：:"+username);
         System.out.println("登录时用户密码：:"+password);

        if (username == null || password == null) {
            throw new IllegalArgumentException("username 和 password 不能为空");
        }

      User user =  this.userService.getUserByEamilAndPassword(username, password);
      if(user == null){
        return HttpResult.error("用户名或密码错误");
      }
      String role = user.getRole();
      String token = JwtUtil.createToken(username,role,user.getId());
      System.out.println("登录成功，token："+token);
      return HttpResult.success(token);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> addUser(@Valid @RequestBody RegisterRequest registerRequest){
        HttpResult<User> user = this.userService.addUser(registerRequest);
      return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PostMapping("/auth/register/sendCode")
    public void registerSendCode(@Valid @RequestBody HashMap<String,String> json){
     this.userService.registerSendCode(json);

    }


    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return this.userService.getUserById(id);
    }

    @GetMapping ("/list")
    public List<User> getAllUsers(){
        System.out.println("获取所有的用户");
        return this.userService.getAllUsers();

    }

    @PutMapping("/{id}")
    public HttpResult<User> updateUser(@PathVariable Long id, @RequestBody User user){
        return this.userService.updateUser(id,user);
    }

    @DeleteMapping("/{id}")
    public HttpResult<String> deleteUser(@PathVariable Long id,Authentication authentication){
         System.out.println(
            "当前用户:"
            + authentication.getName()
        );

        System.out.println(
            "我的权限1:"
            + authentication.getAuthorities()
        );

       return this.userService.deleteUser(id,authentication);
    }

    @PostMapping("/auth/logout")
    public HttpResult<String> logout(HttpServletRequest request) {

    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return HttpResult.error("Token不存在");
    }

    String token = authHeader.substring(7);

   return this.userService.logout(token);

  

   
}

}
