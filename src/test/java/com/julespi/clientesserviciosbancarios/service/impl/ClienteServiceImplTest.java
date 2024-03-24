package com.julespi.clientesserviciosbancarios.service.impl;

import com.julespi.clientesserviciosbancarios.BbvaBusinessException;
import com.julespi.clientesserviciosbancarios.dto.ClienteDto;
import com.julespi.clientesserviciosbancarios.mapper.IClienteMapper;
import com.julespi.clientesserviciosbancarios.model.Cliente;
import com.julespi.clientesserviciosbancarios.repository.IClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private IClienteRepository repository;

    @Spy
    private IClienteMapper mapper = IClienteMapper.INSTANCE;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    void dadoDniExistente_cuandoCrearCliente_entoncesbusinessException(){
        //given
        given(repository.existsByDni(any())).willReturn(true);

        //when
        try{
            clienteService.crearCliente(buildClienteDto(12345678));
            fail("Exception no lanzada");
        }catch (BbvaBusinessException ignore){}

        //then
        then(repository).should(times(1)).existsByDni(any());
        then(repository).should(never()).save(any());
    }

    @Test
    void dadoDniNoExistente_cuandoCrearCliente_entoncesOk() throws BbvaBusinessException {
        //given
        Cliente clienteEnt = buildCliente(12345678);
        given(repository.existsByDni(any())).willReturn(false);
        given(repository.save(any())).willReturn(clienteEnt);

        //when
        ClienteDto response = clienteService.crearCliente(buildClienteDto(12345678));

        //then
        assertNotNull(response);
        then(repository).should(times(1)).existsByDni(any());
        then(repository).should(times(1)).save(any());
    }

    @Test
    void dadoCodServicioBancarioNull_cuandoListar_entoncesFindAll() throws BbvaBusinessException {
        //given
        given(repository.findAll()).willReturn(Arrays.asList(buildCliente(123), buildCliente(321)));

        //when
        List<ClienteDto> response = clienteService.listarClientes(null);

        //then
        assertNotNull(response);
        then(repository).should(times(1)).findAll();
        then(repository).should(never()).findByServiciosBancarios_Codigo(any());
    }

    @Test
    void dadoCodServicioBancario_cuandoListar_entoncesFindAll() throws BbvaBusinessException {
        //given
        given(repository.findByServiciosBancarios_Codigo(any())).willReturn(Arrays.asList(buildCliente(123), buildCliente(321)));

        //when
        List<ClienteDto> response = clienteService.listarClientes("COD");

        //then
        assertNotNull(response);
        then(repository).should(never()).findAll();
        then(repository).should(times(1)).findByServiciosBancarios_Codigo(any());
    }

    private ClienteDto buildClienteDto(Integer dni){
        ClienteDto dto = new ClienteDto();
        dto.setNombre("Nombre");
        dto.setApellido("Apellido");
        dto.setDni(dni);
        return dto;
    }

    private Cliente buildCliente(Integer dni){
        Cliente ent = new Cliente();
        ent.setNombre("Nombre");
        ent.setApellido("Apellido");
        ent.setDni(dni);
        return ent;
    }
}
