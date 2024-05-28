package com.hardeymorlah.Logistics.Model;

import com.hardeymorlah.Logistics.Model.DTOs.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Entity(name = "delivery_package_table")
@AllArgsConstructor
@Getter

public class DeliveryPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_package_id")
    private Long id;
    @NotBlank(message = "Please enter the package name")
    private String packageName;
    @NotNull(message = "Please assign a value")
    private int packageQuantity;
    @Enumerated(EnumType.STRING)
    private Status packageStatus;
    @NotBlank(message = "Please enter your location")
    private String deliveryLocation;
    @NotBlank(message = "Please enter your Full Name")
    private String customerName;
    @Temporal(TemporalType.DATE)
    private Date OrderedDate ;
    @Temporal(TemporalType.TIME)
    private Date orderedTimeStamp;
    @ManyToOne
    @JoinColumn(name = "admin_package_id")
    private AdminPackage adminPackage;
    @Temporal(TemporalType.DATE)
    private Date pickUpDate;

    public DeliveryPackage() {

    }

}
