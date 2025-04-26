package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.VehicleMaintenance;
import com.examly.springapp.service.VehicleService;

@RestController
public class VehicleServiceController {
    
    private final VehicleService vehicleService;

    public VehicleServiceController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/api/services")
    public ResponseEntity<?> addService(@RequestBody VehicleMaintenance service)
    {
        VehicleMaintenance vehicleMaintenance=vehicleService.addService(service);
        return ResponseEntity.status(201).body(vehicleMaintenance);
    }
    @GetMapping("/api/services/{id}")
    public ResponseEntity<?> getServiceById(@PathVariable Long id)
    {
        Optional<VehicleMaintenance> list=vehicleService.getServiceById(id);
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/api/services/name")
    public ResponseEntity<?> getServiceByName(@RequestParam String name)
    {
        List<VehicleMaintenance> list=vehicleService.findByServiceName(name);
        return ResponseEntity.status(200).body(list);
    }

    @PutMapping("/api/services/{id}")
    public ResponseEntity<?> updateService(@PathVariable Long id,@RequestBody VehicleMaintenance services)
    {
        VehicleMaintenance vehicleMaintenance=vehicleService.updateService(id, services);
        return ResponseEntity.status(200).body(vehicleMaintenance);
    }

    @GetMapping("/api/services")
    public ResponseEntity<?> getAllServices()
    {
        List<VehicleMaintenance> list=vehicleService.getAllServices();
        return ResponseEntity.status(200).body(list);
    }

    @DeleteMapping("/api/services/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id)
    {
            vehicleService.deleteSerivce(id);
            return ResponseEntity.status(204).body("Deleted Successfully");
        
    }
    
}
