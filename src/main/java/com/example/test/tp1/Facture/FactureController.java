package com.example.test.tp1.Facture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class FactureController {
    @Autowired
    private FactureService service;
    @GetMapping("facture/{id}")
    public ResponseEntity<?> Facture(@PathVariable Long id){
       return service.factureService(id);
    }

}
