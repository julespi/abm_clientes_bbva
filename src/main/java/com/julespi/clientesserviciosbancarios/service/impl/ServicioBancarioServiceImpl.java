package com.julespi.clientesserviciosbancarios.service.impl;

import com.julespi.clientesserviciosbancarios.exception.BbvaNotFoundException;
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

    public ServicioBancarioServiceImpl(IServicioBancarioRepository repository, IClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void registrarUsuario(String codServicioBancario, Integer dniCliente) throws BbvaNotFoundException {
        ServicioBancario servicio = repository.findByCodigo(codServicioBancario)
                .orElseThrow(() -> new BbvaNotFoundException("Servicio '" + codServicioBancario + "' no valido"));
        Cliente cliente = clienteRepository.findByDni(dniCliente)
                .orElseThrow(() -> new BbvaNotFoundException("Cliente con dni '" + dniCliente + "' inexistente"));
        servicio.getClientes().add(cliente);
        repository.save(servicio);
    }
}
