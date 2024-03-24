package com.julespi.clientesserviciosbancarios.service;

import com.julespi.clientesserviciosbancarios.exception.BbvaBusinessException;
import com.julespi.clientesserviciosbancarios.exception.BbvaNotFoundException;
import com.julespi.clientesserviciosbancarios.dto.ClienteDto;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public interface IClienteService {
    ClienteDto crearCliente(ClienteDto clienteDto) throws BbvaBusinessException, BbvaNotFoundException;

    List<ClienteDto> listarClientes(@Nullable String codServicioBancario);

    ClienteDto editarCliente(Integer dniCliente, Map<String, Object> body) throws BbvaNotFoundException, BbvaBusinessException;
}
