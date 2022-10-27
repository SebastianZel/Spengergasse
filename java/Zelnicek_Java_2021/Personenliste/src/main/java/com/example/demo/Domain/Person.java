package com.example.demo.Domain;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "Person")
public class Person extends AbstractPersistable<Long> {


    @Autowired
    @Column(name = "id", nullable = false)
    @Id
    private Long id;


    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank
    @Column(name = "telNumber", nullable = false)
    private String telNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank
    @Column(name = "city", nullable = false)
    private String city;

}
