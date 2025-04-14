package org.example.sachi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PharmacyDTO {

    private int pharmacyID;
    private String name;
    private String address;
    private String email;
    private String contactNum;
    private int hospitalID;
}
