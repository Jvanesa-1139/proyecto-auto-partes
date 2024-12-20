package edu.pe.cibertec.proyecto_auto_partes.repository;


import org.springframework.data.repository.CrudRepository;
import edu.pe.cibertec.proyecto_auto_partes.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {

    Optional<User> findByUsername(String username);

}
