package com.hardeymorlah.Logistics.Repository;

import com.hardeymorlah.Logistics.Model.AdminPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AdminPackageRepository extends JpaRepository<AdminPackage, Long> {
    AdminPackage findPackagesByPackageName(String packageName);
    List<AdminPackage> findPackagesByCategory(String category);
}
