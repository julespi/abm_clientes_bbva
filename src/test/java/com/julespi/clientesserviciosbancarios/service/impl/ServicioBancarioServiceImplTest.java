package com.julespi.clientesserviciosbancarios.service.impl;

import com.julespi.clientesserviciosbancarios.exception.BbvaNotFoundException;
import com.julespi.clientesserviciosbancarios.model.Cliente;
import com.julespi.clientesserviciosbancarios.model.ServicioBancario;
import com.julespi.clientesserviciosbancarios.repository.IClienteRepository;
import com.julespi.clientesserviciosbancarios.repository.IServicioBancarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ServicioBancarioServiceImplTest {

    @Mock
    private IServicioBancarioRepository servicioBancarioRepository;

    @Mock
    private IClienteRepository clienteRepository;

    @InjectMocks
    private ServicioBancarioServiceImpl service;

    @Test
    void dadoIdServicioInexistente_cuandoRegistrarUsuario_entoncesNotFoundException(){
        //given
        given(servicioBancarioRepository.findByCodigo(any())).willReturn(Optional.empty());

        //when
        try{
            service.registrarUsuario("ASD", 123);
            fail("Exception no lanzada");
        }catch (BbvaNotFoundException ignore){}

        //then
        then(servicioBancarioRepository).should(times(1)).findByCodigo(any());
        then(clienteRepository).should(never()).findByDni(any());
        then(servicioBancarioRepository).should(never()).save(any());
    }

    @Test
    void dadoDniInexistente_cuandoRegistrarUsuario_entoncesNotFoundException(){
        //given
        ServicioBancario servicioBancario = new ServicioBancario();
        given(servicioBancarioRepository.findByCodigo(any())).willReturn(Optional.of(servicioBancario));
        given(clienteRepository.findByDni(any())).willReturn(Optional.empty());

        //when
        try{
            service.registrarUsuario("ASD", 123);
            fail("Exception no lanzada");
        }catch (BbvaNotFoundException ignore){}

        //then
        then(servicioBancarioRepository).should(times(1)).findByCodigo(any());
        then(clienteRepository).should(times(1)).findByDni(any());
        then(servicioBancarioRepository).should(never()).save(any());
    }

    @Test
    void dadoDniYCodServCorrectos_cuandoRegistrarUsuario_entoncesOk() throws BbvaNotFoundException {
        //given
        ServicioBancario servicioBancario = new ServicioBancario();
        servicioBancario.setClientes(new HashSet<>());
        Cliente cliente = new Cliente();
        given(servicioBancarioRepository.findByCodigo(any())).willReturn(Optional.of(servicioBancario));
        given(clienteRepository.findByDni(any())).willReturn(Optional.of(cliente));

        //when
        service.registrarUsuario("ASD", 123);

        //then
        then(servicioBancarioRepository).should(times(1)).findByCodigo(any());
        then(clienteRepository).should(times(1)).findByDni(any());
        then(servicioBancarioRepository).should(times(1)).save(any());
    }
}
