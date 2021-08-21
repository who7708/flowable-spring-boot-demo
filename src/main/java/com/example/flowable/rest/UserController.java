package com.example.flowable.rest;

import io.reactivex.Flowable;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Chris
 * @version 1.0.0
 * @since 2021/08/21
 */
@RestController
@RequestMapping("api/user")
public class UserController {
    @GetMapping("/detail")
    public Flowable<User> detail() {
        // ThreadContext.put("a", "helloworld");
        User user = new User();
        user.setAge(11);
        user.setName("Chris");
        user.setBirth(new Date());
        return Flowable.just(user);
    }

    @Data
    public static class User implements Serializable {
        private int age;
        private String name;
        private String address;
        private Date birth;
    }
}
