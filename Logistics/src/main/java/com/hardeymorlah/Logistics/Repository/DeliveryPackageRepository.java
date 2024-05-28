package com.hardeymorlah.Logistics.Repository;

import com.hardeymorlah.Logistics.Model.DeliveryPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPackageRepository extends JpaRepository<DeliveryPackage, Long> {
   DeliveryPackage findPackagesByPackageName(String packageName);

}
