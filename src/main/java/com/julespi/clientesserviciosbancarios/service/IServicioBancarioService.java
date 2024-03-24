package com.julespi.clientesserviciosbancarios.service;

import com.julespi.clientesserviciosbancarios.exception.BbvaNotFoundException;

public interface IServicioBancarioService {

    void registrarUsuario(String codServicioBancario, Integer dniCliente) throws BbvaNotFoundException;
}
