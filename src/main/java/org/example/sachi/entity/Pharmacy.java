package org.example.sachi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pharmacyID;
    @Pattern(regexp = "^[a-zA-Z\\s]{5,20}$", message = "Invalid pharmacy Name !")
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.-]{5,50}$", message = "Invalid Address! Please enter a valid address.")
    private String address;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid Email Address! Please enter a valid email.")
    private String email;
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid Contact Number !")
    private String contactNum;

    @ManyToOne()  // Apply cascading operations to Hospital
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
}
