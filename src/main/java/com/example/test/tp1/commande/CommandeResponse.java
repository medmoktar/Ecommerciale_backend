package com.example.test.tp1.commande;

import java.time.format.DateTimeFormatter;


import lombok.Data;


@Data
public class CommandeResponse {
private Long id;
private String status;
private String tel;
private String localisation;
private String prix_total;
private String date_commande;
   public CommandeResponse(CommandeModel commande) {
        this.id = commande.getId();
        this.status = commande.getStatus().toString();
        this.prix_total = commande.getPrix_total();
        this.tel=commande.getTel();
        this.localisation=commande.getLocalisation();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.date_commande = commande.getDateCommande().format(formatter);
    }
}
