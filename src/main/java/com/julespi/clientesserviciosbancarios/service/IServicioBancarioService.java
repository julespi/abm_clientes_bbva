package com.julespi.clientesserviciosbancarios.service;

import com.julespi.clientesserviciosbancarios.BbvaNotFoundException;

public interface IServicioBancarioService {

    void registrarUsuario(Long idServicio, Integer dniUsuario) throws BbvaNotFoundException;
}
