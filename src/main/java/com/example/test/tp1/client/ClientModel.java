package com.example.test.tp1.client;

import java.util.List;

import com.example.test.tp1.commande.CommandeModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class ClientModel {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String password;
    private String tel;
    @OneToMany(
        mappedBy = "client"
    )
    private List<CommandeModel> commandes;

}
