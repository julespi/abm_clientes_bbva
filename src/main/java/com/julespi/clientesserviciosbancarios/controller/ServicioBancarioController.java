package com.julespi.clientesserviciosbancarios.controller;

import com.julespi.clientesserviciosbancarios.BbvaNotFoundException;
import com.julespi.clientesserviciosbancarios.service.IServicioBancarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servicios-bancarios")
public class ServicioBancarioController {

    private final IServicioBancarioService service;

    public ServicioBancarioController(IServicioBancarioService service) {
        this.service = service;
    }

    @Operation(summary = "Registra un usuario a un servicio")
    @PostMapping(value = "/{idServicio}/usuarios/{dniCliente}")
    public ResponseEntity<Void> registrarUsuario(@PathVariable Long idServicio, @PathVariable Integer dniCliente) throws BbvaNotFoundException {
        service.registrarUsuario(idServicio, dniCliente);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
