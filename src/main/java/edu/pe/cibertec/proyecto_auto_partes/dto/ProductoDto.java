package edu.pe.cibertec.proyecto_auto_partes.dto;

public record ProductoDto(
        Integer codigoProducto,
        String nombreProducto,
        Double precioUnitario
) {
}
