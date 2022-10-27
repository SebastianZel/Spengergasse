package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder

public class PersonDTO {

    private Long id;
    private String name;

    private String email;

    private String telNumber;

    private String address;

    private String city;


}
