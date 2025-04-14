package org.example.sachi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AmbulanceDTO {
    private int ambulanceID;
    private String vehicalNum;
    private String driverName;
    private String driverContactNum;
    private String hospitalAffiliation;
    private int hospitalID;
}
