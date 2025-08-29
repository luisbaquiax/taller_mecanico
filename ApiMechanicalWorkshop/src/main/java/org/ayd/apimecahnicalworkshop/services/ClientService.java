package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.entities.Client;
import org.ayd.apimecahnicalworkshop.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;
    /**
     * Get client by NIT
     * @param nit - Client NIT
     * @return Client or null if not found
     */
    public Client getClientByNit(String nit) {
        return repository.findClientByNit(nit);
    }
}
