package com.julespi.clientesserviciosbancarios.repository;

import com.julespi.clientesserviciosbancarios.model.ServicioBancario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IServicioBancarioRepository extends JpaRepository<ServicioBancario, Long> {
    Optional<ServicioBancario> findByCodigo(String codigo);

}
