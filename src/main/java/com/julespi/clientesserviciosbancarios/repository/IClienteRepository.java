package com.julespi.clientesserviciosbancarios.repository;

import com.julespi.clientesserviciosbancarios.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {
}
