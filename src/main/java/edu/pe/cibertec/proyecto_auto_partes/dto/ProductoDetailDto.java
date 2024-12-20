package edu.pe.cibertec.proyecto_auto_partes.dto;

public record ProductoDetailDto(
        Integer codigoProducto,
        String nombreProducto,
        String serie,
        Integer idFamilia,
        Integer idGrupo,
        Integer idLinea,
        Double precioUnitario
) {
}
