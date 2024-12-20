package edu.pe.cibertec.proyecto_auto_partes.response;

import edu.pe.cibertec.proyecto_auto_partes.dto.ProductoDetailDto;


public record FindProductResponse(String code,
                                  String error,
                                  ProductoDetailDto producto) {

}
