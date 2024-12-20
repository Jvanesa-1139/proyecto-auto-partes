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
public class GrupoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGrupo;
    private String descripcion;

    @OneToMany(mappedBy = "grupoProducto",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Producto> productos;
}

