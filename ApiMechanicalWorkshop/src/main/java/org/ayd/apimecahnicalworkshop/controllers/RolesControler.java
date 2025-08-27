package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.entities.RolUsers;
import org.ayd.apimecahnicalworkshop.services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesControler {
    @Autowired
    private RolesService rolesService;

    @GetMapping("/all")
    public List<RolUsers> getRoles() {
        return rolesService.getRoles();
    }
}
