package com.demo.backend.user.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.demo.backend.common.constant.ResultCode;
import com.demo.backend.common.redis.RedisConstant;
import com.demo.backend.common.redis.RedisKey;
import com.demo.backend.common.redis.RedisUtils;
import com.demo.backend.common.result.HttpResult;
import com.demo.backend.security.UserPermissionConfig;
import com.demo.backend.user.captcha.CaptchaService;
import com.demo.backend.user.dto.RegisterRequest;
import com.demo.backend.user.entity.User;
import com.demo.backend.user.enums.CaptchaType;
import com.demo.backend.user.respository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.List;

import com.demo.backend.Utils.CurrentUserUtil;



@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    private final CaptchaService captchaService;

    private final RedisUtils redisUtils;

    public UserService(UserRepository userRepository,CaptchaService captchaService,RedisUtils redisUtils) {
        this.userRepository = userRepository;
        this.captchaService = captchaService;
        this.redisUtils = redisUtils;
    }

    @Transactional
    public HttpResult<User> addUser(RegisterRequest registerRequest){
          System.out.println("进入注册接口");
         
        String email = registerRequest.getEmail();
        User existing = getUserByEmail(email);
        if(existing != null){
           // throw new RuntimeException("邮箱已存在",code = 400);
           return HttpResult.error(ResultCode.CONFLICT,"邮箱已存在");
        }

        String phone = registerRequest.getPhone();
        User u = getUserByPhone(phone);
        if(u != null){
           return HttpResult.error(ResultCode.CONFLICT,"手机号已存在");
        }


        String code = registerRequest.getCode();
        if(code == null){
            //throw new RuntimeException("验证码不能为空");
           return HttpResult.error(ResultCode.PARAM_ERROR,"验证码不能为空");
    
        }
        //验证code 是否过期
        if (!captchaService.verify(CaptchaType.REGISTER, phone, registerRequest.getCode())) {
           return HttpResult.error(ResultCode.PARAM_ERROR,"验证码已过期");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail()); 
        user.setPhone(registerRequest.getPhone()); 
        user.setPassword(registerRequest.getPassword());
        user.setRole("USER");
        User saveUser = this.userRepository.save(user);
        
        //redis 保存
        redisUtils.set(
            RedisKey.USER(saveUser.getId()), 
            saveUser,
            RedisConstant.USER_EXPIRE_TIME,
            RedisConstant.USER_EXPIRE_TIME_UNIT);

        return HttpResult.success(saveUser);
        
    }

    public void registerSendCode(HashMap<String,String> json){
        String phone = json.get("phone");
      this.captchaService.sendCode(CaptchaType.REGISTER,phone);
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public User getUserByEamilAndPassword(String email, String password) {
        return this.userRepository.findByEmailAndPassword(email, password);
    }

    public User getUserById(Long id){
         if(!this.userRepository.existsById(id)){
            throw new RuntimeException("User not found id:"+id);
        }
        //先从redis 中取
        User user = (User) redisUtils.get(RedisKey.USER(id));
        if(user != null){
             System.out.println("从redis中取用户:"+user);
            return user;
        }
        //从数据库中取
        user = this.userRepository.findById(id).get();
        //保存到redis
         redisUtils.set(
            RedisKey.USER(user.getId()), 
            user,
            RedisConstant.USER_EXPIRE_TIME,
            RedisConstant.USER_EXPIRE_TIME_UNIT);
       System.out.println("从数据库中取用户:"+user);
        return user;
    }
    public User getUserByEmail(String email){
        //通过邮箱查询用户
        return this.userRepository.findByEmail(email);
    }

    public User getUserByPhone(String phone){
        //通过手机号查询用户
        return this.userRepository.findByPhone(phone);
    }

    @Transactional
    public HttpResult<User> updateUser(Long id, User user){
        if(!this.userRepository.existsById(id)){
            return HttpResult.error(ResultCode.NOT_FOUND,"用户不存在");
        }

           String currentEmail = CurrentUserUtil.getEmail();
           String targetEmail = user.getEmail();
           String role = CurrentUserUtil.getRole();
           if(!UserPermissionConfig.hasPermission(currentEmail, targetEmail, role)){
         
           return HttpResult.error(ResultCode.FORBIDDEN,"权限不足");
           }
   

        
        User userToUpdate = this.userRepository.findById(id).get();
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setRole(user.getRole());
        userToUpdate.setPhone(user.getPhone());

        User saveUser = this.userRepository.save(userToUpdate);
        //删除redis 中的缓存
        redisUtils.delete(RedisKey.USER(id));
        System.out.println("更新用户:"+saveUser);
    
        return HttpResult.success(saveUser);
    }

    @Transactional
    public HttpResult<String> deleteUser(Long id,Authentication authentication){
        if(!this.userRepository.existsById(id)){
           return HttpResult.error(ResultCode.NOT_FOUND,"用户不存在");
        }
        long currentId = CurrentUserUtil.getId();
        String role = CurrentUserUtil.getRole();
        if(!UserPermissionConfig.hasPermission(currentId, id, role)){
           return HttpResult.error(ResultCode.FORBIDDEN,"权限不足");
        }
        this.userRepository.deleteById(id);
        //删除redis 中的缓存
        redisUtils.delete(RedisKey.USER(id));
        return HttpResult.success("删除成功");
    }
    

   
}
