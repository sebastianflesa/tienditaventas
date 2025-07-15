package com.tiendita.tienditaventas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class StockUpdateDTO {
    @JsonProperty("productoId")
    private Long productoId;
    
    @JsonProperty("cantidad")
    private Integer cantidad;
    
    @JsonProperty("usuarioId")
    private Integer usuarioId;

    @JsonProperty("carroId")
    private String carroId;
}
