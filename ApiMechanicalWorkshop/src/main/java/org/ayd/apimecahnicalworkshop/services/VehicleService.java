package org.ayd.apimecahnicalworkshop.services;

import org.ayd.apimecahnicalworkshop.dto.vehicledto.VehicleDTO;
import org.ayd.apimecahnicalworkshop.entities.Client;
import org.ayd.apimecahnicalworkshop.entities.Vehicle;
import org.ayd.apimecahnicalworkshop.repositories.ClientRepository;
import org.ayd.apimecahnicalworkshop.repositories.VehicleRepository;
import org.ayd.apimecahnicalworkshop.utils.ErrorApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository repository;
    private ClientRepository clientRepository;

    /**
     * Get all vehicles
     * @return List of vehicles
     */
    public List<VehicleDTO> findAll() {
        List<Vehicle> vehicles = (List<Vehicle>) repository.findAll();
        return vehicles.stream().map(this::mapToDTO).toList();
    }

    /**
     * Find vehicle by id
     * @param id - vehicle id
     * @return Vehicle or null if not found
     */
    public VehicleDTO findById(long id) {
        Vehicle vehicle = repository.findById(id).orElse(null);
        if(vehicle == null) {
            throw new ErrorApi(404, "Vehicle not found");
        }
        return mapToDTO(vehicle);
    }

    /**
     * Save or update a vehicle
     * @param vehicle - Vehicle entity
     * @return Saved vehicle
     */
    public VehicleDTO save(VehicleDTO vehicle) {
        Vehicle vehicleSaved = new Vehicle();
        vehicleSaved.setBrand(vehicle.getBrand());
        vehicleSaved.setModel(vehicle.getModel());
        vehicleSaved.setColor(vehicle.getColor());
        vehicleSaved.setYear(vehicle.getYear());

        Client aux = clientRepository.findById(vehicle.getClientId()).orElse(null);
        vehicleSaved.setClient(aux);

        return mapToDTO(repository.save(vehicleSaved));
    }

    /**
     * Get vehicles without client
     * @return List of vehicles without client
     */
    public List<VehicleDTO> getVehiclesByClientId() {
        List<Vehicle> vehicles = repository.getVehicleByClientIsNull();
        return vehicles.stream().map(this::mapToDTO).toList();
    }

    /**
     * Map Vehicle entity to VehicleDTO
     * @param save - Vehicle entity
     * @return VehicleDTO
     */
    private VehicleDTO mapToDTO(Vehicle save) {
        return new VehicleDTO(
                save.getVehicleId(),
                save.getClient() != null ? save.getClient().getClientId() : null,
                save.getBrand(),
                save.getModel(),
                save.getYear(),
                save.getColor(),
                save.getCreatedAt() != null ? save.getCreatedAt().toString() : null,
                save.getUpdatedAt() != null ? save.getUpdatedAt().toString() : null
        );
    }


}
