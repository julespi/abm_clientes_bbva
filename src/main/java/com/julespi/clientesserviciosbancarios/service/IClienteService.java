package com.julespi.clientesserviciosbancarios.service;

import com.julespi.clientesserviciosbancarios.BbvaBusinessException;
import com.julespi.clientesserviciosbancarios.BbvaNotFoundException;
import com.julespi.clientesserviciosbancarios.dto.ClienteDto;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface IClienteService {
    ClienteDto crearCliente(ClienteDto clienteDto) throws BbvaBusinessException;

    List<ClienteDto> listarClientes(@Nullable String servicioBancario);

    ClienteDto editarCliente(Integer dniCliente, Map<String, Object> body) throws BbvaNotFoundException, BbvaBusinessException;
}
