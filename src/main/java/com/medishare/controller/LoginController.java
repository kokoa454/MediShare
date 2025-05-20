package com.medishare.controller;

import com.medishare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String loginPage() {
        return "login";  // login.html を表示
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> loginUser(
            @RequestParam("username") String userEmail,
            @RequestParam("password") String password
    ) {
        try{
            boolean isAuthenticated = userService.authenticateUser(userEmail, password);

            if (isAuthenticated) {
                System.out.println("ログイン成功: " + userEmail);
                return ResponseEntity.ok("Login successful");
            } else {
                System.out.println("ログイン失敗: " + userEmail);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("認証されませんでした");
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("サーバーエラーが発生しました");
        }
    }
}

