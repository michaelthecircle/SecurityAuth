package ru.michaelthecircle.spring.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;

}
