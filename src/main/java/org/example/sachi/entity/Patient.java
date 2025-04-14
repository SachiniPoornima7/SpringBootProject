package org.example.sachi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patientID;
    @Pattern(regexp = "^[a-zA-Z\\s]{5,20}$", message = "Invalid patient Name !")
    private String fullName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.-]{5,50}$", message = "Invalid Address! Please enter a valid address.")
    private String address;
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid Contact Number !")
    private String contactNum;
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;

    private int age;

    @ManyToOne(cascade = CascadeType.ALL)  // Apply cascading operations to Doctor
    @JoinColumn(name = "doctor_ID")
    private Doctor doctorID;
    @Column(name = "status" , nullable = false)
    private String status = "Pending";
}
