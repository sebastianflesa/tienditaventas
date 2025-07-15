package com.tiendita.tienditaventas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VentaDTO {
    @JsonProperty("productoId")
    private Long productoId;
    
    @JsonProperty("cantidad")
    private Integer cantidad;
    
    @JsonProperty("usuarioId")
    @JsonAlias({"clienteId", "usuarioId"})
    private Long usuarioId;

    @JsonProperty("carroId")
    private String carroId;
}
