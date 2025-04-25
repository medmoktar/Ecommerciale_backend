package com.example.test.tp1;

import com.example.test.tp1.commande.CommandeModel;
import com.example.test.tp1.maison.Maisons;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;
@Entity
@Data
public class CommandeMaison {
    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commande_id", nullable = false)
    private CommandeModel commande;

    @ManyToOne
    @JoinColumn(name = "maison_id", nullable = false)
    private Maisons maison;

    private int quantite;
}
