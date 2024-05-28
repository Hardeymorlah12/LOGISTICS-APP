package com.hardeymorlah.Logistics.Model.DTOs;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class PackageSubmissionRequest {
    @NotNull
    private String packageName;
    @Min(value = 1, message = "Value can't be less than 1")
    private int packageQuantity;
    private Status packageStatus;
    @NotNull
    private String deliveryLocation;
    @NotNull
    private String customerName;
    @Temporal(TemporalType.DATE)
    private Date OrderedDate;
    @Temporal(TemporalType.TIME)
    private Time orderedTimeStamp;

}
