package edu.pe.cibertec.proyecto_auto_partes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamiliaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFamilia;
    private String descripcion;

    @OneToMany(mappedBy = "familiaProducto",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Producto> productos;
}

