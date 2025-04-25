
package com.example.test.tp1.Facture;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.test.tp1.CommandeMaison;
import com.example.test.tp1.commande.CommandeModel;
import com.example.test.tp1.commande.CommandeRepository;
import com.example.test.tp1.maison.Maisons;
@Service
public class FactureService {
    @Autowired
    private CommandeRepository commandeRepository;
    public ResponseEntity<?> factureService(Long id){
        Optional<CommandeModel> optionCommande=commandeRepository.findById(id);
        if(!optionCommande.isPresent()){
          return ResponseEntity.badRequest().body("Cette commande n'a pas été trouvée");
        }
        CommandeModel commande=optionCommande.get();
        FactureResponse factureResponse=new FactureResponse();
        DateTimeFormatter date=DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm");
        factureResponse.setDate(commande.getDateCommande().format(date));
        factureResponse.setClient(commande.getClient().getNom());
        factureResponse.setTotal(commande.getPrix_total());
        List<FactureProduit> ListFactureproduit =new ArrayList<>();
        for(CommandeMaison commandeMaison:commande.getCommandeMaisons()){
            Maisons maisons=commandeMaison.getMaison();
            FactureProduit factureProduit=new FactureProduit();
            factureProduit.setQuantite(commandeMaison.getQuantite());
            factureProduit.setNom(maisons.getNom());
            factureProduit.setPrix(maisons.getPrix());
            int quantite=commandeMaison.getQuantite();
            double prix=maisons.getPrix();
            double prixtotal=quantite*prix;
            factureProduit.setPrixtotal(prixtotal);
            ListFactureproduit.add(factureProduit);
        };
        factureResponse.setProduits(ListFactureproduit);
        return ResponseEntity.ok().body(factureResponse);
    }
}
