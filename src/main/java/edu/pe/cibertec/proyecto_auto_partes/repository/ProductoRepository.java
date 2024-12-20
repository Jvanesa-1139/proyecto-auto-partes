package edu.pe.cibertec.proyecto_auto_partes.repository;

import edu.pe.cibertec.proyecto_auto_partes.entity.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository<Producto, Integer> {
}
