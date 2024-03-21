package com.julespi.clientesserviciosbancarios.controller;

import com.julespi.clientesserviciosbancarios.dto.ClienteDto;
import com.julespi.clientesserviciosbancarios.service.IClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final IClienteService service;

    public ClienteController(IClienteService service) {
        this.service = service;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDto> crearCliente(@RequestBody ClienteDto clienteDto){ //TODO ver que devolver
        return new ResponseEntity<>(service.crearCliente(clienteDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClienteDto>> listarClientes(){
        return ResponseEntity.ok(service.listarClientes());
    }
}
