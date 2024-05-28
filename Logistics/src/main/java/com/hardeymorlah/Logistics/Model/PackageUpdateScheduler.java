package com.hardeymorlah.Logistics.Model;



import com.hardeymorlah.Logistics.Service.PackageService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Data
public class PackageUpdateScheduler {
    @Autowired
    private PackageService packageService;
    @Scheduled(fixedRate = 120000)
   public void updatePackageTrackingStatus(){

       packageService.updateTrackingStatus();


//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        ResponseEntity<List<DeliveryPackage>> packages = packageService.getAllDeliveryPackages();
//        for (DeliveryPackage packages1 : Objects.requireNonNull(packages.getBody())) {
//            if (packages1.getPackageStatus() == Status.SUBMITTED) {
//
//                packageTrackingService.updateTrackingStatus(packages1.getId(), Status.IN_TRANSIT);
//            } else if (packages1.getPackageStatus() == Status.IN_TRANSIT) {
//                packageTrackingService.updateTrackingStatus(packages1.getId(), Status.AVAILABLE_FOR_PICKUP);
//            } else if(packages1.getPackageStatus() == Status.AVAILABLE_FOR_PICKUP){
//                packageTrackingService.updateTrackingStatus(packages1.getId(), Status.DELIVERED);
//
//            }
        }
    }


