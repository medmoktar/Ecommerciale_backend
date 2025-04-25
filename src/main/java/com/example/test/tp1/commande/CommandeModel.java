package com.example.test.tp1.commande;

import java.time.LocalDateTime;
import java.util.List;

import com.example.test.tp1.CommandeMaison;
import com.example.test.tp1.client.ClientModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class CommandeModel {
@Id
@GeneratedValue
private Long id;
private String status;
private String tel;
private String localisation;
private String prix_total;
@ManyToOne
@JoinColumn(name="client_id",nullable = false)
private ClientModel client;
@OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
private List<CommandeMaison> commandeMaisons;
private LocalDateTime dateCommande;
}
