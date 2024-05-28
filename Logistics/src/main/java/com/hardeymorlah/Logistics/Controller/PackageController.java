package com.hardeymorlah.Logistics.Controller;

import com.hardeymorlah.Logistics.Model.AdminPackage;
import com.hardeymorlah.Logistics.Model.DTOs.PackageSubmissionRequest;
import com.hardeymorlah.Logistics.Model.DeliveryPackage;
import com.hardeymorlah.Logistics.Service.PackageService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;
    @GetMapping("/allAdminPackages")
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<List<AdminPackage>> getAllAdminPackages(){
        return packageService.getAllAdminPackages();
    }

    @GetMapping("/search_by_category/{category}")
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<List<AdminPackage>> searchByCategory(@PathVariable String category){
        return packageService.getPackagesByCategory(category);
    }

    @GetMapping("/admin_package_id/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<AdminPackage> getAdminPackageById(@PathVariable long id){
        return packageService.getAdminPackageById(id);
    }
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/admin_package_name")
    public ResponseEntity<AdminPackage> getAdminPackageByName(@RequestParam String packageName){
        return packageService.getAdminPackageByName(packageName);
    }
    @PostMapping("/post_package")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<AdminPackage> postAdminPackage(@RequestBody @Valid AdminPackage adminPackage){
        return packageService.createAdminPackage(adminPackage);
    }
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/submit_package")
    public ResponseEntity<?> submitPackageForDelivery(@RequestBody @Valid PackageSubmissionRequest request) {
         packageService.submitPackageForDelivery(request);
        return ResponseEntity.ok("Submission Successful");
    }

    @PutMapping("/update_admin_package/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<AdminPackage> updateAdminPackage(@PathVariable long id, @RequestBody AdminPackage packages){
        return packageService.changeAdminPackage(id, packages);
    }

    @DeleteMapping("/delete_adminPackage/{id}")
    public ResponseEntity<AdminPackage> deleteAdminPackage(@PathVariable long id){
        return packageService.deleteAdminPackage(id);
    }

    // Delivery Package Service
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/all_deliveryPackages")
    public ResponseEntity<List<DeliveryPackage>> getAllDeliveryPackage() {
        return packageService.getAllDeliveryPackages();
    }
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/delivery_package_id/{id}")
    public ResponseEntity<DeliveryPackage> getDeliveryPackageById(@PathVariable long id) {
        return packageService.getDeliveryPackageById(id);
    }
}
