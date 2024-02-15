package ru.michaelthecircle.spring.dtos;

import jakarta.persistence.*;
import lombok.Data;
import ru.michaelthecircle.spring.entities.Role;

import java.util.Collection;

@Data
public class RegistratoinUserDto {
    private String name;
    private String password;
    private String confirmPassword;
    private String email;
}
