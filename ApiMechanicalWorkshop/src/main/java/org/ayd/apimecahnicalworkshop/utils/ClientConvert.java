package org.ayd.apimecahnicalworkshop.utils;

import org.ayd.apimecahnicalworkshop.dto.clientdto.ClientDTO;
import org.ayd.apimecahnicalworkshop.entities.Client;
import org.ayd.apimecahnicalworkshop.entities.User;
import org.springframework.stereotype.Service;

@Service
public class ClientConvert {

    public ClientDTO mapToDTO(Client client){
        return new ClientDTO(
                client.getClientId(),
                client.getUser().getUserId(),
                client.getNit(),
                client.getAddress()
        );
    }

    public Client mapToEntity(ClientDTO clientDTO){
        User user = new User();
        return new Client(
                clientDTO.getClientId(),
                user,
                clientDTO.getNit(),
                clientDTO.getAddress()
        );
    }
}
