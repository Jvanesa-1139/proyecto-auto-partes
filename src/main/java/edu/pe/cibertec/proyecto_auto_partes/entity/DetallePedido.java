package edu.pe.cibertec.proyecto_auto_partes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="detalle_pedido")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalle;
    private Integer cantidad;
    @Column(name = "precio")
    private Double total;

    //private Integer idProducto;
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    //private Integer idPedido;
    @ManyToOne
    @JoinColumn(name = "idPedido")
    private Pedido pedido;
}