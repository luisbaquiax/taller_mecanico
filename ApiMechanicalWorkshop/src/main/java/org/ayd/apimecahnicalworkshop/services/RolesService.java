package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.entities.RolUsers;
import org.ayd.apimecahnicalworkshop.repositories.RolUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {
    @Autowired
    private RolUserRepository rolesRepository;

    public List<RolUsers> getRoles() {
        return rolesRepository.findAll();
    }

}
