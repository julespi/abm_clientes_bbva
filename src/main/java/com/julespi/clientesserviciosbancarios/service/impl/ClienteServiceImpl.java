package com.julespi.clientesserviciosbancarios.service.impl;

import com.julespi.clientesserviciosbancarios.exception.BbvaBusinessException;
import com.julespi.clientesserviciosbancarios.exception.BbvaNotFoundException;
import com.julespi.clientesserviciosbancarios.dto.ClienteDto;
import com.julespi.clientesserviciosbancarios.mapper.IClienteMapper;
import com.julespi.clientesserviciosbancarios.model.Cliente;
import com.julespi.clientesserviciosbancarios.model.ServicioBancario;
import com.julespi.clientesserviciosbancarios.repository.IClienteRepository;
import com.julespi.clientesserviciosbancarios.repository.IServicioBancarioRepository;
import com.julespi.clientesserviciosbancarios.service.IClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Slf4j
@Service
public class ClienteServiceImpl implements IClienteService {

    private final IClienteRepository repository;
    private final IServicioBancarioRepository servicioBancarioRepository;
    private final IClienteMapper mapper;
    private static final List<String> READ_ONLY_ATTRS = Arrays.asList("id", "dni", "serviciosBancarios");

    public ClienteServiceImpl(IClienteRepository repository, IServicioBancarioRepository servicioBancarioRepository, IClienteMapper mapper) {
        this.repository = repository;
        this.servicioBancarioRepository = servicioBancarioRepository;
        this.mapper = mapper;
    }

    @Override
    public ClienteDto crearCliente(ClienteDto clienteDto) throws BbvaBusinessException, BbvaNotFoundException {
        if(repository.existsByDni(clienteDto.getDni())){
            throw new BbvaBusinessException("Ya existe un cliente con el dni '" + clienteDto.getDni() + "'");
        }
        Cliente tempCliente = mapper.toCliente(clienteDto);
        for(String codServ:clienteDto.getServiciosBancarios()){
            ServicioBancario serv = servicioBancarioRepository.findByCodigo(codServ)
                    .orElseThrow(() -> new BbvaNotFoundException("Servicio '" + codServ + "' no valido"));
            tempCliente.addServicioBancario(serv);
        }
        return mapper.toClienteDto(repository.save(tempCliente));
    }

    @Override
    public List<ClienteDto> listarClientes(@Nullable String codServicioBancario) {
        if(codServicioBancario == null){
            return mapper.toClienteListDto(repository.findAll());
        }else{
            return mapper.toClienteListDto(repository.findByServiciosBancarios_Codigo(codServicioBancario));
        }
    }

    @Override
    public ClienteDto editarCliente(Integer dniCliente, Map<String, Object> body) throws BbvaNotFoundException, BbvaBusinessException {
        Cliente cliente = repository.findByDni(dniCliente)
                .orElseThrow(() -> new BbvaNotFoundException("Cliente con dni '" + dniCliente + "' inexistente"));

        for (Map.Entry<String, Object> entry : body.entrySet()) {
            String nombreAttr = entry.getKey();
            Object valorAttr = entry.getValue();

            if(READ_ONLY_ATTRS.contains(nombreAttr)){
                log.info("El atributo '" + nombreAttr + "' no puede ser modificado");
                throw new BbvaBusinessException("El atributo '" + nombreAttr + "' no puede ser modificado");
            }

            try {
                Field field = Cliente.class.getDeclaredField(nombreAttr);
                field.setAccessible(true);
                field.set(cliente, valorAttr);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("Error updating property '" + nombreAttr + "'", e);
                throw new BbvaBusinessException("Error updating property '" + nombreAttr + "'");
            }
        }

        cliente = repository.save(cliente);

        return mapper.toClienteDto(cliente);
    }
}
