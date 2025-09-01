package org.ayd.apimecahnicalworkshop.utils;

import org.ayd.apimecahnicalworkshop.dto.clientdto.ClientDTO;
import org.ayd.apimecahnicalworkshop.entities.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientConvert {

    public ClientDTO mapToDTO(Client client){
        return new ClientDTO(
                client.getClientId(),
                client.getUserId(),
                client.getNit(),
                client.getAddress()
        );
    }

    public Client mapToEntity(ClientDTO clientDTO){
        return new Client(
                clientDTO.getClientId(),
                clientDTO.getUserId(),
                clientDTO.getNit(),
                clientDTO.getAddress()
        );
    }
}
