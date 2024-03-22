package com.julespi.clientesserviciosbancarios.repository;

import com.julespi.clientesserviciosbancarios.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByDni(Integer dni);

    Optional<Cliente> findByDni(Integer dni);


}
