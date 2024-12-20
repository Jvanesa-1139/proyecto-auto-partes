package edu.pe.cibertec.proyecto_auto_partes.response;

import edu.pe.cibertec.proyecto_auto_partes.dto.ProductoDto;

public record FindProductsResponse(
        String code,
        String error,
        Iterable<ProductoDto> users) {
}
