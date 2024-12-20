package edu.pe.cibertec.proyecto_auto_partes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LineaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLinea;
    private String descripcion;

    @OneToMany(mappedBy = "lineaProducto", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Producto> productos;


}

