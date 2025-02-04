package com.example.test.tp1.register;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("Api/auth")
public class RegisterController {
    @Autowired
    private RegisterService service;
     @PostMapping("register")
     public ResponseEntity<String> postMethodName(@RequestBody RegisterRequest request) {
         try{
            service.register(request.getEmail(),request.getTel(),request.getPassword());
            return ResponseEntity.ok().body("Utilisateur inscit avec succès !");
         }
         catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
         }
         
     }
     
}
