package com.demo.backend.user.enums;

public enum CaptchaType {
    
REGISTER, 
FORGET_PASSWORD,
CHANGE_PHONE;

public String getPrefix(){

    return switch(this){
        case REGISTER ->
               "register:";
        case FORGET_PASSWORD ->
               "forget:";
        case CHANGE_PHONE ->
               "changePhone:";              
    };

}


}
