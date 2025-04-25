package com.example.test.tp1.commande;

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
@RequestMapping("commande/")
public class CommandeController {
  @Autowired
  CommandeService service;
  @PostMapping("add/{id_client}")
  public ResponseEntity<Map<String,Object>> add(@RequestBody CommandeRequest request,@PathVariable Long id_client){
    return service.addService(id_client,request.getPrix_total(),request.getTel(),request.getLocalisation(), request.getMaisons());
  }

  @PostMapping("status")
  public ResponseEntity<?> status(@RequestBody StatusRequest request){
    return service.Status(request);
  }

  @GetMapping("All")
  public ResponseEntity<?> All(){
    return service.All();
  }
  
  
}
