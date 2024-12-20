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

public class Producto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoProducto;
    private String nombreProducto;
    private String serie;
    private Double precioUnitario;


    @ManyToOne
    @JoinColumn(name = "idLinea")
    private LineaProducto lineaProducto;

    @ManyToOne
    @JoinColumn(name = "idGrupo")
    private GrupoProducto grupoProducto;

    @ManyToOne
    @JoinColumn(name = "idFamilia")
    private FamiliaProducto familiaProducto;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DetallePedido> detallePedidos;

}