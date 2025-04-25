package com.example.test.tp1.client;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.test.tp1.commande.CommandeModel;
import com.example.test.tp1.commande.CommandeResponse;

@Service
public class Clientservice {
    @Autowired
    ClientRepository repository;
    public ResponseEntity<Map<String,Object>> addService(String nom,String password,String tel){
       Optional<ClientModel> existingClient=repository.findByTel(tel);
       if(existingClient.isPresent()){
         Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Cet utilisateur existe déjà");
        return ResponseEntity.badRequest().body(errorResponse);

       }
       ClientModel client = new ClientModel();
    client.setNom(nom);
    client.setPassword(password);
    client.setTel(tel);
    
    repository.save(client);

    Map<String, Object> successResponse = new HashMap<>();
    successResponse.put("message", "Utilisateur ajouté avec succès");
    successResponse.put("client", client);

    return ResponseEntity.ok(successResponse);
}

public ResponseEntity<Map<String,Object>> loginService(String tel,String password){
    Optional<ClientModel> existingClient=repository.findByTel(tel);
    Map<String,Object> reponse=new HashMap<>();
    if(existingClient.isPresent()){
       if(existingClient.get().getPassword().equals(password)){
          reponse.put("message", "authentification réussie");
          reponse.put("id", existingClient.get().getId());
          return ResponseEntity.ok().body(reponse);
       }else{
        reponse.put("message", "authentification échouée");
        return ResponseEntity.status(401).body(reponse);
       }
    }else{
        reponse.put("message", "authentification échouée");
        return ResponseEntity.status(401).body(reponse);
    }
}
    public ResponseEntity<Map<String,Object>> Commande(Long client_id){
        Map<String, Object> response = new HashMap<>();
    try{    
    Optional<ClientModel> optionalClient = repository.findById(client_id);
        if (!optionalClient.isPresent()) {
            response.put("error", "Client non trouvé");
            return ResponseEntity.badRequest().body(response);
        }
    List<CommandeModel> commandes= optionalClient.get().getCommandes();
    List<CommandeResponse> reponse=commandes.stream().map(CommandeResponse::new).collect(Collectors.toList());
    response.put("commandes", reponse);
    return ResponseEntity.ok().body(response);
    }catch(Exception e){
        response.put("error", "Erreur serveur" + e);
        return ResponseEntity.status(500).body(response);
    }

}

    }

