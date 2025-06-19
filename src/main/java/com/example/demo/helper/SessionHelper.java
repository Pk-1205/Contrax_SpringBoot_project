package com.example.demo.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

    public static void removeMessage(){
        try{
             HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.removeAttribute("message");

        } catch(Exception e){
            System.out.println("Error not working" +e);
            e.printStackTrace();
        }
       
    }
    }

    

