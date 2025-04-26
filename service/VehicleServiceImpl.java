package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.examly.springapp.model.VehicleMaintenance;
import com.examly.springapp.repository.VehicleServiceRepo;
import jakarta.persistence.EntityNotFoundException;


@Service
public class VehicleServiceImpl implements VehicleService{

    private final VehicleServiceRepo vehicleServiceRepo;

    public VehicleServiceImpl(VehicleServiceRepo vehicleServiceRepo) {
        this.vehicleServiceRepo = vehicleServiceRepo;
    }

    @Override
    public VehicleMaintenance addService(VehicleMaintenance service) {
       return vehicleServiceRepo.save(service);
    }

    @Override
    public VehicleMaintenance updateService(Long serviceId, VehicleMaintenance service) {

        Optional<VehicleMaintenance> op=vehicleServiceRepo.findById(serviceId);
        if(op.isEmpty())
        {
            throw new EntityNotFoundException(" ID Not Found");
        }
        
        service.setServiceId(serviceId);
        return vehicleServiceRepo.save(service);
       
    }

    @Override
    public void deleteSerivce(Long serviceId) {
        vehicleServiceRepo.deleteById(serviceId);
    }

    @Override
    public List<VehicleMaintenance> getAllServices() {
       List<VehicleMaintenance> vehiclelist=vehicleServiceRepo.findAll();//used findAll method
       return vehiclelist;
    }

    @Override
    public Optional<VehicleMaintenance> getServiceById(Long serviceId) {
       Optional<VehicleMaintenance> op=vehicleServiceRepo.findById(serviceId);
       if(op.isEmpty())
       {
        throw new EntityNotFoundException("Not Found");
       }
       return vehicleServiceRepo.findById(serviceId);
    }

    @Override
    public List<VehicleMaintenance> findByServiceName(String serviceName) {
      List<VehicleMaintenance> vehiclelistname=vehicleServiceRepo.findByServiceName(serviceName);//used findByServiceName(serviceName)
      return vehiclelistname;
    }
    
}
