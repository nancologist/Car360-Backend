package com.nancologist.importer;

import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class CarImportModel {

    private Long id;
    private String vin;
    private String manufacturer;
    private String model;
    private String bodyStyleCode;
    private String steering;
    private boolean facelift;
    private Date productionDate;
    private String colorCode;
    private String upholsteryCode;
    private short powerInKw;
    private float displacementInLiter;
    private short doorsCount;
    private String transmission;
    private String drive;
    private List<String> equipmentCodes;
}

