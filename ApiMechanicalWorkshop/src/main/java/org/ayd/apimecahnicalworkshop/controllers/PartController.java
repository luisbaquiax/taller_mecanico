package org.ayd.apimecahnicalworkshop.controllers;

import org.ayd.apimecahnicalworkshop.dto.partdto.PartDTO;
import org.ayd.apimecahnicalworkshop.entities.Part;
import org.ayd.apimecahnicalworkshop.services.PartService;
import org.ayd.apimecahnicalworkshop.utils.PartConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parts")
public class PartController {
    @Autowired
    private PartService service;

    @Autowired
    private PartConvert convert;

    @GetMapping("/all")
    public List<PartDTO> getAll() {
        return  service.findAll().stream().map(convert::mapToDTO).toList();
    }
    @GetMapping("/active/{active}")
    public List<PartDTO> getPartByActive(@PathVariable Boolean active) {
        return service.getPartByActive(active);
    }

    @PostMapping("/createPart")
    public PartDTO save(@RequestBody PartDTO partDTO) {
        return service.save(partDTO);
    }

    /**
     * In this method we update the part, and we can add or remove stock,
     * the parameter addStock is a boolean that indicates if we want to add or remove stock
     * @param partDTO
     * @param addStock
     * @return
     */
    @PutMapping("/updatePart/{addStock}")
    public PartDTO update(@RequestBody PartDTO partDTO, @PathVariable boolean addStock) {
        return service.update(partDTO, addStock);
    }

    /*
    @GetMapping("/findByNamePartAndSupplierId/{namePart}/{supplierId}")
    public PartDTO getByNamePartAndSupplierId(@PathVariable String namePart, @PathVariable Long supplierId) {
        return service.findPartByNamePartIgnoreCaseAndSupplierId(namePart, supplierId);
    }
    */
}
