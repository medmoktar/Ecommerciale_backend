package com.example.test.tp1.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client/")
public class Clientcontroller {
@Autowired
Clientservice service;
@PostMapping("add")
public ResponseEntity<Map<String,Object>> add(@RequestBody ClientRequest request){
   return service.addService(request.getNom(), request.getPassword(), request.getTel());
}
@PostMapping("login")
public ResponseEntity<Map<String,Object>> login(@RequestBody ClientRequest request){
    return service.loginService(request.getTel(),request.getPassword());
}
@GetMapping("commande/{id}")
  public ResponseEntity<Map<String,Object>> client(@PathVariable Long id){
       return service.Commande(id);
  }
}
