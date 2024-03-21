package com.julespi.clientesserviciosbancarios.service;

import com.julespi.clientesserviciosbancarios.BbvaNotFoundException;
import com.julespi.clientesserviciosbancarios.model.ServicioBancario;

import java.util.List;

public interface IServicioBancarioService {
    ServicioBancario crearServicio(ServicioBancario servicioBancario);

    List<ServicioBancario> listarServiciosBancarios();

    void registrarUsuario(Long idServicio, Long idUsuario) throws BbvaNotFoundException;
}
