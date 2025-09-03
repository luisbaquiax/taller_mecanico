package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.clientdto.ClientDTO;
import org.ayd.apimecahnicalworkshop.entities.Client;
import org.ayd.apimecahnicalworkshop.repositories.ClientRepository;
import org.ayd.apimecahnicalworkshop.utils.ClientConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;
    @Autowired
    private ClientConvert convert;
    /**
     * Get client by NIT
     * @param nit - Client NIT
     * @return Client or null if not found
     */
    public Client getClientByNit(String nit) {
        return repository.findClientByNit(nit);
    }

    /**
     * List of clients
     * @return List of clients
     */
    public List<ClientDTO> getAllClients() {
        List<Client> list = (List<Client>) repository.findAll();
        return list.stream().map(convert::mapToDTO).toList();
    }
}
