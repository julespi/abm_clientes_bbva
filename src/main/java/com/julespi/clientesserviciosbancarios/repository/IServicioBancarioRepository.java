package com.julespi.clientesserviciosbancarios.repository;

import com.julespi.clientesserviciosbancarios.model.ServicioBancario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServicioBancarioRepository extends JpaRepository<ServicioBancario, Long> {
}
