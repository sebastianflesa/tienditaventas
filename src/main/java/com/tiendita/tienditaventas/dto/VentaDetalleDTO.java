package com.tiendita.tienditaventas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaDetalleDTO {
    private Long ventaId;
    private Long productoId;
    private Integer valor;
    private String nombre;
    private Integer cantidad;
    private String carroId;
    private Long usuarioId;
}
