package org.example.sachi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorID;
    @Pattern(regexp = "^[a-zA-Z\\s]{5,20}$", message = "Invalid doctor Name !")
    private String name;
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid Email Address! Please enter a valid email.")
    private String email;
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid Contact Number !")
    private String contactNum;

    @ManyToOne()  // Cascade all operations (persist, merge, remove, etc.)
    @JoinColumn(name = "hospital_id")
    private Hospital hospitalID;

    @OneToMany(mappedBy = "doctorID")
    @JsonIgnore
    private List<Patient> patientsid;
}
