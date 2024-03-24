package com.julespi.clientesserviciosbancarios.service.impl;

import com.julespi.clientesserviciosbancarios.exception.BbvaBusinessException;
import com.julespi.clientesserviciosbancarios.exception.BbvaNotFoundException;
import com.julespi.clientesserviciosbancarios.dto.ClienteDto;
import com.julespi.clientesserviciosbancarios.mapper.IClienteMapper;
import com.julespi.clientesserviciosbancarios.model.Cliente;
import com.julespi.clientesserviciosbancarios.model.ServicioBancario;
import com.julespi.clientesserviciosbancarios.model.TipoCliente;
import com.julespi.clientesserviciosbancarios.repository.IClienteRepository;
import com.julespi.clientesserviciosbancarios.repository.IServicioBancarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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

    @Mock
    private IServicioBancarioRepository servicioBancarioRepository;

    @Spy
    private IClienteMapper mapper = IClienteMapper.INSTANCE;

    @InjectMocks
    private ClienteServiceImpl service;

    @Test
    void dadoDniExistente_cuandoCrearCliente_entoncesBusinessException(){
        //given
        given(repository.existsByDni(any())).willReturn(true);

        //when
        try{
            service.crearCliente(buildClienteDto(12345678));
            fail("Exception no lanzada");
        }catch (BbvaBusinessException ignore){

        }catch (BbvaNotFoundException e){
            fail("Exception incorrecta");
        }

        //then
        then(repository).should(times(1)).existsByDni(any());
        then(servicioBancarioRepository).should(never()).findByCodigo(any());
        then(repository).should(never()).save(any());
    }

    @Test
    void dadoServicioInexistente_cuandoCrearCliente_entoncesNotFoundException(){
        //given
        ClienteDto cDto = buildClienteDto(12345678);
        cDto.setServiciosBancarios(new HashSet<>(Collections.singletonList("ASD")));
        given(repository.existsByDni(any())).willReturn(false);
        given(servicioBancarioRepository.findByCodigo(any())).willReturn(Optional.empty());

        //when
        try{
            service.crearCliente(cDto);
            fail("Exception no lanzada");
        }catch (BbvaBusinessException e){
            fail("Exception incorrecta");
        }catch (BbvaNotFoundException ignore){

        }

        //then
        then(repository).should(times(1)).existsByDni(any());
        then(servicioBancarioRepository).should(times(1)).findByCodigo(any());
        then(repository).should(never()).save(any());
    }

    @Test
    void dadoDniNoExistente_cuandoCrearCliente_entoncesOk() throws BbvaBusinessException, BbvaNotFoundException {
        //given
        ClienteDto cDto = buildClienteDto(123);
        cDto.setServiciosBancarios(new HashSet<>(Arrays.asList("CHEQ", "PZOF")));
        given(repository.existsByDni(any())).willReturn(false);
        given(servicioBancarioRepository.findByCodigo(any())).willReturn(Optional.of(new ServicioBancario()));
        given(repository.save(any())).willReturn(buildCliente(123));

        //when
        ClienteDto response = service.crearCliente(cDto);

        //then
        assertNotNull(response);
        then(repository).should(times(1)).existsByDni(any());
        then(servicioBancarioRepository).should(times(2)).findByCodigo(any());
        then(repository).should(times(1)).save(any());
    }

    @Test
    void dadoCodServicioBancarioNull_cuandoListar_entoncesFindAll() throws BbvaBusinessException {
        //given
        given(repository.findAll()).willReturn(Arrays.asList(buildCliente(123), buildCliente(321)));

        //when
        List<ClienteDto> response = service.listarClientes(null);

        //then
        assertNotNull(response);
        then(repository).should(times(1)).findAll();
        then(repository).should(never()).findByServiciosBancarios_Codigo(any());
    }

    @Test
    void dadoCodServicioBancario_cuandoListar_entoncesFindByServiciosBancarios() throws BbvaBusinessException {
        //given
        given(repository.findByServiciosBancarios_Codigo(any())).willReturn(Arrays.asList(buildCliente(123), buildCliente(321)));

        //when
        List<ClienteDto> response = service.listarClientes("COD");

        //then
        assertNotNull(response);
        then(repository).should(never()).findAll();
        then(repository).should(times(1)).findByServiciosBancarios_Codigo(any());
    }

    @Test
    void dadoDniInexistente_cuandoEditarCliente_entoncesNotFoundException(){
        //given
        given(repository.findByDni(any())).willReturn(Optional.empty());

        //when
        try{
            service.editarCliente(123, new HashMap<>());
            fail("Exception no lanzada");
        }catch (BbvaNotFoundException ignore){

        }catch (BbvaBusinessException e){
            fail("Exception incorrecta");
        }

        //then
        then(repository).should(times(1)).findByDni(any());
        then(repository).should(never()).save(any());
    }

    @Test
    void dadoAtributoInexistente_cuandoEditarCliente_entoncesBusinessException(){
        //given
        Map<String, Object> entries = new HashMap<>();
        entries.put("asd",123);
        given(repository.findByDni(any())).willReturn(Optional.of(buildCliente(123)));

        //when
        try{
            service.editarCliente(123, entries);
            fail("Exception no lanzada");
        }catch (BbvaNotFoundException e){
            fail("Exception incorrecta");
        }catch (BbvaBusinessException ignore){
        }

        //then
        then(repository).should(times(1)).findByDni(any());
        then(repository).should(never()).save(any());
    }

    @Test
    void dadoAtributoDni_cuandoEditarCliente_entoncesBusinessException(){
        //given
        Map<String, Object> entries = new HashMap<>();
        entries.put("dni",123);
        given(repository.findByDni(any())).willReturn(Optional.of(buildCliente(123)));

        //when
        try{
            service.editarCliente(123, entries);
            fail("Exception no lanzada");
        }catch (BbvaNotFoundException e){
            fail("Exception incorrecta");
        }catch (BbvaBusinessException ignore){
        }

        //then
        then(repository).should(times(1)).findByDni(any());
        then(repository).should(never()).save(any());
    }

    @Test
    void dadoAtributoId_cuandoEditarCliente_entoncesBusinessException(){
        //given
        Map<String, Object> entries = new HashMap<>();
        entries.put("id", 123);
        given(repository.findByDni(any())).willReturn(Optional.of(buildCliente(123)));

        //when
        try{
            service.editarCliente(123, entries);
            fail("Exception no lanzada");
        }catch (BbvaNotFoundException e){
            fail("Exception incorrecta");
        }catch (BbvaBusinessException ignore){
        }

        //then
        then(repository).should(times(1)).findByDni(any());
        then(repository).should(never()).save(any());
    }

    @Test
    void dadoAtributoApellido_cuandoEditarCliente_entoncesBusinessException(){
        //given
        Map<String, Object> entries = new HashMap<>();
        entries.put("serviciosBancarios", new HashSet<>());
        given(repository.findByDni(any())).willReturn(Optional.of(buildCliente(123)));

        //when
        try{
            service.editarCliente(123, entries);
            fail("Exception no lanzada");
        }catch (BbvaNotFoundException e){
            fail("Exception incorrecta");
        }catch (BbvaBusinessException ignore){
        }

        //then
        then(repository).should(times(1)).findByDni(any());
        then(repository).should(never()).save(any());
    }

    @Test
    void dadoAtributosCorrectos_cuandoEditarCliente_entoncesOk() throws BbvaBusinessException, BbvaNotFoundException {
        //given
        Map<String, Object> entries = new HashMap<>();
        entries.put("nombre", "Nuevo Nombre");
        entries.put("tipoCliente", TipoCliente.EMPRESA);
        given(repository.findByDni(any())).willReturn(Optional.of(buildCliente(123)));
        given(repository.save(any())).willReturn(buildCliente(123));

        //when
        ClienteDto response = service.editarCliente(123, entries);

        //then
        assertNotNull(response);
        then(repository).should(times(1)).findByDni(any());
        then(repository).should(times(1)).save(any());
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
