package com.julespi.clientesserviciosbancarios.service.impl;

import com.julespi.clientesserviciosbancarios.BbvaNotFoundException;
import com.julespi.clientesserviciosbancarios.mapper.ServicioBancarioMapper;
import com.julespi.clientesserviciosbancarios.model.Cliente;
import com.julespi.clientesserviciosbancarios.model.ServicioBancario;
import com.julespi.clientesserviciosbancarios.repository.IClienteRepository;
import com.julespi.clientesserviciosbancarios.repository.IServicioBancarioRepository;
import com.julespi.clientesserviciosbancarios.service.IServicioBancarioService;
import org.springframework.stereotype.Service;

@Service
public class ServicioBancarioServiceImpl implements IServicioBancarioService {

    private final IServicioBancarioRepository repository;
    private final IClienteRepository clienteRepository;
    private final ServicioBancarioMapper mapper;

    public ServicioBancarioServiceImpl(IServicioBancarioRepository repository, IClienteRepository clienteRepository, ServicioBancarioMapper mapper) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    @Override
    public void registrarUsuario(Long idServicio, Integer dniCliente) throws BbvaNotFoundException {
        ServicioBancario servicio = repository.findById(idServicio).orElseThrow(BbvaNotFoundException::new); //TODO agregar mensaje
        Cliente cliente = clienteRepository.findByDni(dniCliente).orElseThrow(BbvaNotFoundException::new); //TODO agregar mensaje
        servicio.getClientes().add(cliente);
        repository.save(servicio);
    }
}
