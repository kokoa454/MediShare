package com.medishare.controller;

import com.medishare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register_account")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registerPage() {
        return "register_account";  // register_account.html を表示
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> registerUser(
            @RequestParam("username") String userEmail,
            @RequestParam("password") String password
    ) {
        try{
            boolean isRegistered = userService.registerUser(userEmail, password);

            if (!isRegistered) {
                System.out.println("すでに登録されているユーザーです");
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("このメールアドレスは既に登録されています");
            }

            System.out.println("ユーザー登録成功: " + userEmail);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("サーバーエラーが発生しました");
        }
    }
}

