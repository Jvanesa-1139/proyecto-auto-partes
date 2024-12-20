package edu.pe.cibertec.proyecto_auto_partes.service;

import edu.pe.cibertec.proyecto_auto_partes.dto.ProductoDetailDto;
import edu.pe.cibertec.proyecto_auto_partes.dto.ProductoDto;
import edu.pe.cibertec.proyecto_auto_partes.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface MaintenanceService {

    List<ProductoDto> findAllProducts();

    ProductoDetailDto findDetailById(Integer id);
    Boolean updateProduct(ProductoDetailDto productoDetailDto);

    Boolean createProduct(ProductoDetailDto productoDetailDto);
    Boolean deleteProductById(Integer id);
    Optional<ProductoDetailDto> getProductById(int id) throws Exception;
    Boolean createUser(UserDto userDto);

    //Stored Procedure
    Integer crearPedidoConDetalles(Integer clienteId, String productos) throws Exception;
}
