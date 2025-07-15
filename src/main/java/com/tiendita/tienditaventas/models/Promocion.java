package com.tiendita.tienditaventas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "PROMOCIONES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Promocion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promocion_seq_gen")
    @SequenceGenerator(name = "promocion_seq_gen", sequenceName = "PROMOCIONES_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "PRODUCTO_ID", nullable = false)
    private Long productoId;
    
    @Column(name = "DESCRIPCION", length = 120)
    private String descripcion;
    
    @Column(name = "DESCUENTO")
    private Double descuento;
    
    @Column(name = "FECHA_VENCIMIENTO")
    private LocalDate fechaVencimiento;
    
    @Column(name = "VALIDO")
    private Integer valido = 1;
}
