package com.hardeymorlah.Logistics.Model;

import com.hardeymorlah.Logistics.Model.DTOs.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Entity
@Table(name = "admin_packages_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AdminPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_package_id")
    private Long id;
    @NotBlank(message = "Please enter the package name")
    private String packageName;
//    @NotBlank(message = "Please enter the Vendor's name")
//    @Column(unique = true)
    private String category;
    @Enumerated(EnumType.STRING)
    private Status packageStatus;
    @NotNull(message = "Please enter the quantity of the package")
    private int packageQuantity;
    @Temporal(TemporalType.DATE)
    private Date postedDate;
    @Temporal(TemporalType.TIME)
    private Date postedTimeStamp;
}
