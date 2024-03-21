package com.julespi.clientesserviciosbancarios.dto;

import com.julespi.clientesserviciosbancarios.model.ServicioBancario;
import com.julespi.clientesserviciosbancarios.model.TipoCliente;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
public class ClienteDto {

    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    private String calle;

    private Integer numero;

    private Integer codigoPostal;

    private String telefono;

    private String celular;

    private TipoCliente tipoCliente;

    private Set<ServicioBancario> serviciosBancarios;
}
