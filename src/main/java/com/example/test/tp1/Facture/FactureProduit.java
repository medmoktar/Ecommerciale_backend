package com.example.test.tp1.Facture;

import lombok.Data;

@Data
public class FactureProduit {
  private String nom;
  private int quantite;
  private double prix;
  private double prixtotal;
}
