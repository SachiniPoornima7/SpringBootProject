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
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hospitalID;
    @Pattern(regexp = "^[a-zA-Z\\s]{5,20}$", message = "Invalid hospital Name !")
    private String hospitalName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.-]{5,50}$", message = "Invalid Address! Please enter a valid address.")
    private String address;
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid Contact Number !")
    private String contactNum;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid Email Address! Please enter a valid email.")
    private String email;

    @OneToMany(mappedBy = "hospitalID", cascade = CascadeType.ALL)  // Cascade all operations
    @JsonIgnore
    private List<Doctor> doctorsId;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)  // Cascade all operations
    @JsonIgnore
    private List<Pharmacy> pharmacy;

    @OneToMany(mappedBy = "hospitalID", cascade = CascadeType.ALL)  // Cascade all operations
    @JsonIgnore
    private List<Ambulance> ambulanceId;
}
