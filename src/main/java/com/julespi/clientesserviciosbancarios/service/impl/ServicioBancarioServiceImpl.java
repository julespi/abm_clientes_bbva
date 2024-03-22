package com.julespi.clientesserviciosbancarios.service.impl;

import com.julespi.clientesserviciosbancarios.BbvaNotFoundException;
import com.julespi.clientesserviciosbancarios.model.Cliente;
import com.julespi.clientesserviciosbancarios.model.ServicioBancario;
import com.julespi.clientesserviciosbancarios.repository.IClienteRepository;
import com.julespi.clientesserviciosbancarios.repository.IServicioBancarioRepository;
import com.julespi.clientesserviciosbancarios.service.IServicioBancarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioBancarioServiceImpl implements IServicioBancarioService {

    private final IServicioBancarioRepository repository;
    private final IClienteRepository clienteRepository;

    public ServicioBancarioServiceImpl(IServicioBancarioRepository repository, IClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ServicioBancario crearServicio(ServicioBancario servicioBancario) {
        return repository.save(servicioBancario);
    }

    @Override
    public List<ServicioBancario> listarServiciosBancarios() {
        return repository.findAll();
    }

    @Override
    public void registrarUsuario(Long idServicio, Integer dniCliente) throws BbvaNotFoundException {
        ServicioBancario servicio = repository.findById(idServicio).orElseThrow(BbvaNotFoundException::new); //TODO agregar mensaje
        Cliente cliente = clienteRepository.findByDni(dniCliente).orElseThrow(BbvaNotFoundException::new); //TODO agregar mensaje
        servicio.getClientes().add(cliente);
        repository.save(servicio);
    }
}
