package com.example.test.tp1.commande;

import java.util.List;

import lombok.Data;

@Data
public class CommandeRequest {
private String prix_total;
private String tel;
private String localisation;
private List<ProduitCommandeDTO> maisons;
}
