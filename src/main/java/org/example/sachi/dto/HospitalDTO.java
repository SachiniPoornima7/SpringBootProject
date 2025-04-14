package org.example.sachi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class HospitalDTO {
    private int hospitalID;
    private String hospitalName;
    private String address;
    private String contactNum;
    private String email;
}
