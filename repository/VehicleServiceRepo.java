package com.examly.springapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.examly.springapp.model.VehicleMaintenance;

@Repository
public interface VehicleServiceRepo extends JpaRepository<VehicleMaintenance,Long>{

    @Query("select v from VehicleMaintenance v where v.serviceName like :serviceName")
    public List<VehicleMaintenance> findByServiceName(String serviceName);
    
}
