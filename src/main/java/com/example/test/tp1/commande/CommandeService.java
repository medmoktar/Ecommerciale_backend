package com.example.test.tp1.commande;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.test.tp1.CommandeMaison;
import com.example.test.tp1.client.ClientModel;
import com.example.test.tp1.client.ClientRepository;
import com.example.test.tp1.maison.MaisonRepository;
import com.example.test.tp1.maison.Maisons;

@Service
public class CommandeService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private MaisonRepository maisonsRepository;
public ResponseEntity<Map<String,Object>> addService(Long client_id,String prix,String tel,String localisation,List<ProduitCommandeDTO> produits){
Map<String, Object> response = new HashMap<>();

        try {
            // Récupérer le client
            Optional<ClientModel> optionalClient = clientRepository.findById(client_id);
            if (!optionalClient.isPresent()) {
                response.put("error", "Client non trouvé");
                return ResponseEntity.badRequest().body(response);
            }

            ClientModel client = optionalClient.get();
           
            // Créer la commande
            CommandeModel commande = new CommandeModel();
            commande.setClient(client);

           
            commande.setStatus("ENCOURS");
            commande.setPrix_total(prix);
            commande.setTel(tel);
            commande.setLocalisation(localisation);
            commande.setDateCommande(LocalDateTime.now());

            List<CommandeMaison> commandeMaisons = new ArrayList<>();

    for (ProduitCommandeDTO p : produits) {
        Optional<Maisons> maisonOpt = maisonsRepository.findById(p.getMaisonId());
        if (!maisonOpt.isPresent()) {
            response.put("error", "Maison non trouvée avec ID: " + p.getMaisonId());
            return ResponseEntity.badRequest().body(response);
        }

        Maisons maison = maisonOpt.get();
        if(maison.getQuantite()<p.getQuantite()){
            response.put("error", "La quantité commandée dépasse le stock pour la Produit: " + maison.getNom());
            return ResponseEntity.badRequest().body(response);
        }

        CommandeMaison cm = new CommandeMaison();
        cm.setCommande(commande);
        cm.setMaison(maison);
        cm.setQuantite(p.getQuantite());
        maison.setQuantite(maison.getQuantite()-p.getQuantite());
        commandeMaisons.add(cm);
    }

    commande.setCommandeMaisons(commandeMaisons);
    commandeRepository.save(commande);

    response.put("message", "Commande enregistrée avec succès");
    return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("error", "Erreur serveur" + e);
            return ResponseEntity.status(500).body(response);
        }
    }
    public ResponseEntity<?> Status(StatusRequest request){
          Optional<CommandeModel> findcommande=commandeRepository.findById(request.getId());
          if(!findcommande.isPresent()){
            return ResponseEntity.badRequest().body("Cette commande n'a pas été trouvée");
          }
        CommandeModel commande=findcommande.get();
        commande.setStatus(request.getStatus());
        if("ANNULEE".equals(request.getStatus()) ){
           for(CommandeMaison commandeMaison:commande.getCommandeMaisons()){
            Maisons maison = commandeMaison.getMaison();
            maison.setQuantite(maison.getQuantite() + commandeMaison.getQuantite());
            maisonsRepository.save(maison);
           }
        } 
        commandeRepository.save(commande); // Sauvegarder la commande mise à jour

        return ResponseEntity.ok().body("Status mis à jour avec succès");
    } 

    public ResponseEntity<?> All(){
        List<CommandeModel> commandes=commandeRepository.findAll();
        List<CommandeResponse> reponse=commandes.stream().map(CommandeResponse::new).toList();
        return ResponseEntity.ok().body(reponse);
    }
  
}

