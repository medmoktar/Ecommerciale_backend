package com.example.test.tp1.Facture;

import java.util.List;

import lombok.Data;

@Data
public class FactureResponse {
   private String client;
   private String total;
   private List<FactureProduit> produits;
   private String date;
}
