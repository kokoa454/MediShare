package com.medishare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medishare.service.UserService;

@Controller
@RequestMapping("/register_account")
public class RegisterController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

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
                logger.info("User registration failed (already registered): email address={}", userEmail);
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("このメールアドレスは既に登録されています");
            }

            logger.warn("User registration successful: user ID={}, email address={}", userService.getUserIdByEmail(userEmail), userEmail);
            return ResponseEntity.ok("登録に成功しました");
        } catch (Exception e){
            logger.error("User registration failed: email address={}, error message={}", userEmail, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("サーバーエラーが発生しました");
        }
    }    
}
