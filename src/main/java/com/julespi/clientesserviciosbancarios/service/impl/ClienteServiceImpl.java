package com.julespi.clientesserviciosbancarios.service.impl;

import com.julespi.clientesserviciosbancarios.BbvaBusinessException;
import com.julespi.clientesserviciosbancarios.BbvaNotFoundException;
import com.julespi.clientesserviciosbancarios.dto.ClienteDto;
import com.julespi.clientesserviciosbancarios.mapper.IClienteMapper;
import com.julespi.clientesserviciosbancarios.model.Cliente;
import com.julespi.clientesserviciosbancarios.repository.IClienteRepository;
import com.julespi.clientesserviciosbancarios.service.IClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ClienteServiceImpl implements IClienteService {

    private final IClienteRepository repository;
    private final IClienteMapper mapper;
    private static final List<String> READ_ONLY_ATTRS = Arrays.asList("id", "dni", "serviciosBancarios");

    public ClienteServiceImpl(IClienteRepository repository, IClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ClienteDto crearCliente(ClienteDto clienteDto) throws BbvaBusinessException {
        if(repository.existsByDni(clienteDto.getDni())){
            throw new BbvaBusinessException("Ya existe un cliente con el dni '" + clienteDto.getDni() + "'");
        }
        return mapper.toClienteDto(repository.save(mapper.toCliente(clienteDto)));
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
        Cliente cliente = repository.findByDni(dniCliente).orElseThrow(BbvaNotFoundException::new);

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
