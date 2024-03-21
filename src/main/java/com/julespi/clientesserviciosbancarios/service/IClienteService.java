package com.julespi.clientesserviciosbancarios.service;

import com.julespi.clientesserviciosbancarios.BbvaNotFoundException;
import com.julespi.clientesserviciosbancarios.dto.ClienteDto;
import com.julespi.clientesserviciosbancarios.model.Cliente;

import java.util.List;

public interface IClienteService {
    ClienteDto crearCliente(ClienteDto clienteDto);

    List<ClienteDto> listarClientes();
}
