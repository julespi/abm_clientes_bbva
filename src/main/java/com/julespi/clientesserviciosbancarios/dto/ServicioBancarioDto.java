package com.julespi.clientesserviciosbancarios.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class ServicioBancarioDto {

    private String codigo;

    private String nombre;

    private String sector;

    private Integer cantidadPersonasAtiende;

    private Boolean atencionExclusiva;

    private Boolean soporteTecnico;

    private Set<Integer> clientes;
}
