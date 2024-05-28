package com.hardeymorlah.Logistics.Service;

import com.hardeymorlah.Logistics.Model.AdminPackage;
import com.hardeymorlah.Logistics.Model.DTOs.PackageSubmissionRequest;
import com.hardeymorlah.Logistics.Model.DTOs.Status;
import com.hardeymorlah.Logistics.Model.DeliveryPackage;
import com.hardeymorlah.Logistics.Repository.AdminPackageRepository;
import com.hardeymorlah.Logistics.Repository.DeliveryPackageRepository;
import com.hardeymorlah.Logistics.Repository.UserRepository;
import com.hardeymorlah.Logistics.exception.InsufficientStockException;
import com.hardeymorlah.Logistics.exception.PackageNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@Data
public class PackageService {
    @Autowired
    private AdminPackageRepository adminPackageRepository;
    @Autowired
    private UserRepository accountUsersRepository;
    @Autowired
    private DeliveryPackageRepository deliveryPackageRepository;

    public ResponseEntity<List<AdminPackage>> getAllAdminPackages() {
        return new ResponseEntity<>(adminPackageRepository.findAll(), HttpStatus.OK);
    }
    public ResponseEntity<List<AdminPackage>> getPackagesByCategory(String category) {
        return new ResponseEntity<>(adminPackageRepository.findPackagesByCategory(category), HttpStatus.OK);
    }

    public ResponseEntity<AdminPackage> getAdminPackageById(long id) {
        return new ResponseEntity<>(adminPackageRepository.findById(id).orElseThrow(), HttpStatus.OK);
    }

    public ResponseEntity<AdminPackage> getAdminPackageByName(String packageName) {
        return new ResponseEntity<>(adminPackageRepository.findPackagesByPackageName(packageName), HttpStatus.OK);
    }

    public ResponseEntity<AdminPackage> createAdminPackage(AdminPackage packages) {
        AdminPackage adminPackageWithId = new AdminPackage();
        adminPackageWithId.setPackageName(packages.getPackageName());
        adminPackageWithId.setCategory(packages.getCategory());
        adminPackageWithId.setPackageQuantity(packages.getPackageQuantity());
        adminPackageWithId.setPackageStatus(Status.AVAILABLE_IN_STOCK);
        adminPackageWithId.setPostedDate(STATIC_DATE);
        adminPackageWithId.setPostedTimeStamp(new Date(System.currentTimeMillis()));
        return new ResponseEntity<>(adminPackageRepository.save(adminPackageWithId), HttpStatus.CREATED);
    }
    public ResponseEntity<AdminPackage> changeAdminPackage(long id, AdminPackage changedPackage) {
        AdminPackage change = adminPackageRepository.findById(id).orElseThrow();
        change.setPackageName(changedPackage.getPackageName());
        change.setCategory(changedPackage.getCategory());
        change.setPackageStatus(Status.AVAILABLE_IN_STOCK);
        change.setPackageQuantity(changedPackage.getPackageQuantity());
        change.setPostedDate(STATIC_DATE);
        change.setPostedTimeStamp(new Date(System.currentTimeMillis()));
        return new ResponseEntity<>(adminPackageRepository.save(change), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<AdminPackage> deleteAdminPackage(long id) {
        AdminPackage deletedPackage = adminPackageRepository.findById(id).orElseThrow();
        adminPackageRepository.deleteById(id);
        return new ResponseEntity<>(deletedPackage, HttpStatus.ACCEPTED);
    }
    // Delivery Package Service

    public ResponseEntity<List<DeliveryPackage>> getAllDeliveryPackages() {
        return new ResponseEntity<>(deliveryPackageRepository.findAll(), HttpStatus.OK);
    }
    public ResponseEntity<DeliveryPackage> getDeliveryPackageById(long id) {
        return new ResponseEntity<>(deliveryPackageRepository.findById(id).orElseThrow(), HttpStatus.OK);
    }
    public void submitPackageForDelivery(PackageSubmissionRequest request) {
        AdminPackage adminPackage = adminPackageRepository.findPackagesByPackageName(request.getPackageName());
        DeliveryPackage deliveryPackage;
        if (adminPackage != null && adminPackage.getPackageStatus().equals(Status.AVAILABLE_IN_STOCK)) {

            if (request.getPackageQuantity() > adminPackage.getPackageQuantity()) {
                int quantityLeft = adminPackage.getPackageQuantity();
                System.out.println(adminPackage.getPackageQuantity());
                throw new InsufficientStockException(STR."Not enough stock available. Only \{quantityLeft} packages left.");
            }
            deliveryPackage = getDeliveryPackage(request, adminPackage);
            deliveryPackageRepository.save(deliveryPackage);

            adminPackage.setPackageQuantity(adminPackage.getPackageQuantity() - request.getPackageQuantity());
            if (adminPackage.getPackageQuantity() == 0) {
                adminPackage.setPackageStatus(Status.OUT_OF_STOCK);
            }
            adminPackageRepository.save(adminPackage);
        } else {
            throw new PackageNotFoundException("Package Not Found");
        }
    }
    private  DeliveryPackage getDeliveryPackage(PackageSubmissionRequest request, AdminPackage adminPackage) {

        DeliveryPackage deliveryPackageWithId = new DeliveryPackage();
        deliveryPackageWithId.setAdminPackage(adminPackage);
        deliveryPackageWithId.setPackageName(request.getPackageName());
        deliveryPackageWithId.setPackageQuantity(request.getPackageQuantity());
        deliveryPackageWithId.setPackageStatus(Status.ORDER_PLACED);
        deliveryPackageWithId.setDeliveryLocation(request.getDeliveryLocation());
        deliveryPackageWithId.setOrderedDate(STATIC_DATE);
        deliveryPackageWithId.setOrderedTimeStamp(new Date(System.currentTimeMillis()));
        deliveryPackageWithId.setCustomerName(request.getCustomerName());
        return deliveryPackageWithId;
    }
    public void updateTrackingStatus() {
        List<DeliveryPackage> tracking = deliveryPackageRepository.findAll();
        for (DeliveryPackage packageTracking : tracking) {
            if (packageTracking.getPackageStatus().equals(Status.ORDER_PLACED)) {
                packageTracking.setPackageStatus(Status.CONFIRMED);
            } else if (packageTracking.getPackageStatus().equals(Status.CONFIRMED)) {
                packageTracking.setPackageStatus(Status.SHIPPED);
            }else if (packageTracking.getPackageStatus().equals(Status.SHIPPED)) {
                packageTracking.setPackageStatus(Status.READY_FOR_PICKUP);
            }

                packageTracking.setPickUpDate(new Date(System.currentTimeMillis() + 86400000 * 3));
                deliveryPackageRepository.save(packageTracking);
            }
        }
    private static final Date STATIC_DATE = new Date();
//    private static final Time STATIC_TIME = new Time();

    }
