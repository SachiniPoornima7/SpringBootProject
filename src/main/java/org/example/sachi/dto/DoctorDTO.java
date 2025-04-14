package org.example.sachi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class DoctorDTO {
    private int doctorId;
    private String name;
    private String gender;
    private String email;
    private String contactNum;
    private int hospitalID;


}
