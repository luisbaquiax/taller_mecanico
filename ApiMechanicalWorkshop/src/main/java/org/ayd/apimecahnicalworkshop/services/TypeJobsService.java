package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.entities.TypeJob;
import org.ayd.apimecahnicalworkshop.repositories.TypeJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeJobsService {
    @Autowired
    private TypeJobRepository repository;

    public List<TypeJob> getAllTypeJobs() {
        return (List<TypeJob>) repository.findAll();
    }
}
