package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.entities.InventaryMovment;
import org.ayd.apimecahnicalworkshop.repositories.InventoryMovmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventaryMovmentService {
    @Autowired
    private InventoryMovmentRepository repository;

    /**
     * Get all inventary movements
     * @return List of inventary movements
     */
    public List<InventaryMovment> findAll() {
        return (List<InventaryMovment>) repository.findAll();
    }

    /**
     * Save an inventary movement
     * @param inventaryMovment inventary movement to save
     * @return saved inventary movement
     */
    public InventaryMovment save(InventaryMovment inventaryMovment) {
        return repository.save(inventaryMovment);
    }
}
