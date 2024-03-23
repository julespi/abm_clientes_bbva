package com.julespi.clientesserviciosbancarios.controller;

import com.julespi.clientesserviciosbancarios.BbvaBusinessException;
import com.julespi.clientesserviciosbancarios.BbvaNotFoundException;
import com.julespi.clientesserviciosbancarios.dto.ClienteDto;
import com.julespi.clientesserviciosbancarios.service.IClienteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final IClienteService service;

    public ClienteController(IClienteService service) {
        this.service = service;
    }

    @Operation(summary = "Crea un cliente nuevo")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDto> crearCliente(
            @Valid @RequestBody ClienteDto clienteDto) throws BbvaBusinessException { //TODO ver que devolver
        return new ResponseEntity<>(service.crearCliente(clienteDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtiene el listado de clientes")
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClienteDto>> listarClientes(@RequestParam(name = "servicio-bancario", required = false) String servicioBancario){
        return ResponseEntity.ok(service.listarClientes(servicioBancario));
    }

    @Operation(summary = "Edita el/los atributos de un cliente determinado")
    @PatchMapping(value = "/{dniCliente}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDto> editarCliente(@PathVariable Integer dniCliente, @RequestBody Map<String, Object> body) throws BbvaBusinessException, BbvaNotFoundException {
        return new ResponseEntity<>(service.editarCliente(dniCliente, body),HttpStatus.OK);
    }
}
