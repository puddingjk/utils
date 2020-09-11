package org.puddingjk.controller;

import org.puddingjk.entity.User;
import org.puddingjk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @ClassName : IndexController
 * @Description :
 * @Author : LuoHongyu
 * @Date: 2020-08-24 10:32
 */
@RestController
@Slf4j
public class IndexController {

    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String test(){
        return "success";
    }

    @GetMapping("/t")
    public List<User> queryData(){
        List<User> allUser = userService.findAllUser();
        return allUser;
    }


    @GetMapping("/ipT")
    public void ipTest(HttpServletRequest request) throws UnknownHostException {
        if (request == null) {
            log.info("request null: "+"unknown");
        }
        log.info("request.getHeader(\"x-forwarded-for\"): "+request.getHeader("x-forwarded-for"));
        log.info("request.getHeader(\"Proxy-Client-IP\"): "+request.getHeader("Proxy-Client-IP"));
        log.info("request.getHeader(\"WL-Proxy-Client-IP\"): "+request.getHeader("WL-Proxy-Client-IP"));
        log.info("request.getHeader(\"X-Real-IP\"): "+request.getHeader("X-Real-IP"));
        try {
            log.info("InetAddress.getLocalHost(): "+ InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            log.error("InetAddress.getLocalHost():{} "+ e);
        }
    }
}
