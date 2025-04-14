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
public class Ambulance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ambulanceID;
    private String vehicalNum;
    @Pattern(regexp = "^[a-zA-Z\\s]{5,20}$", message = "Invalid driver Name !")
    private String driverName;
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid driver Contact Number !")
    private String driverContactNum;
    @Pattern(regexp = "^[a-zA-Z\\s]{5,20}$", message = "Invalid hospitalAffiliation Name !")
    private String hospitalAffiliation;

    @ManyToOne()  // Apply cascading operations to Hospital
    @JoinColumn(name = "hospital_id")
    private Hospital hospitalID;
}
