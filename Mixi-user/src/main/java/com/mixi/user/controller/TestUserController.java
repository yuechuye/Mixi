package com.mixi.user.controller;

import com.mixi.common.annotation.auth.ApiAuth;

import com.mixi.common.annotation.auth.AuthType;
import io.github.common.web.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 描述: 云接口测试类(不用管)
 * @author suifeng
 * 日期: 2024/7/6
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@ApiAuth
public class TestUserController {

    @ApiAuth(roles = 1001)
    @PostMapping(value = "/test1")
    public Result test1(){
        return Result.success("test1");
    }

    @ApiAuth(value = AuthType.NOT, roles = 1001)
    @PostMapping(value = "/test2")
    public Result test21(){
        return Result.success("test2");
    }

    @ApiAuth(AuthType.NOT)
    @PutMapping(value = "/test3")
    public Result test3(){
        return Result.success("test3");
    }

    @DeleteMapping(value = "/test4")
    public Result test4(){
        return Result.success("test4");
    }
}
