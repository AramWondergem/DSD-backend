package com.example.demo.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ComplaintType {
    NOISE("noise"),
    REPAIR("repair"),
    OTHER("other");
    private final String complaintType;

    ComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    @JsonValue
    public String getComplaintType() {
        return complaintType;
    }

    //this makes values of enum in json passed insensitive
    @JsonCreator
    public static ComplaintType fromValue(String complaintType) {
        for (ComplaintType complaintTypeEnum : values()) {
            if (complaintTypeEnum.getComplaintType().equalsIgnoreCase(complaintType)) {
                return complaintTypeEnum;
            }
        }
        throw new IllegalArgumentException("Invalid Complaint Type");
    }
}
