package org.example.sachi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.sachi.entity.Doctor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PatientDTO {

    private int patientID;
    private String fullName;
    private String address;
    private String contactNum;
    private String gender;
    private int age;
    private int doctorID;
}
