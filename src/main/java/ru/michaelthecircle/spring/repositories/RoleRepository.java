package ru.michaelthecircle.spring.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.michaelthecircle.spring.entities.Role;

import java.util.Optional;
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
