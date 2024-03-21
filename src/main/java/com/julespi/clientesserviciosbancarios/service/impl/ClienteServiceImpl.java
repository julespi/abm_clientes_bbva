package com.julespi.clientesserviciosbancarios.service.impl;

import com.julespi.clientesserviciosbancarios.dto.ClienteDto;
import com.julespi.clientesserviciosbancarios.mapper.ClienteMapper;
import com.julespi.clientesserviciosbancarios.model.Cliente;
import com.julespi.clientesserviciosbancarios.repository.IClienteRepository;
import com.julespi.clientesserviciosbancarios.service.IClienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {

    private final IClienteRepository repository;
    private final ClienteMapper mapper;

    public ClienteServiceImpl(IClienteRepository repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ClienteDto crearCliente(ClienteDto clienteDto) {
        return mapper.toClienteDto(repository.save(mapper.toCliente(clienteDto)));
    }

    @Override
    public List<ClienteDto> listarClientes() {
        return mapper.toClienteListDto(repository.findAll());
    }
}
