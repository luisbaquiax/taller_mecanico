package org.ayd.apimecahnicalworkshop.repositories;

import org.ayd.apimecahnicalworkshop.entities.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
    List<Vehicle> getVehicleByClientIsNull();

    Vehicle getByLicencePlate(String licencePlate);
}
