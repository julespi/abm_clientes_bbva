package com.julespi.clientesserviciosbancarios.controller;

import com.julespi.clientesserviciosbancarios.BbvaNotFoundException;
import com.julespi.clientesserviciosbancarios.model.ServicioBancario;
import com.julespi.clientesserviciosbancarios.service.IServicioBancarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios-bancarios")
public class ServicioBancarioController {

    private final IServicioBancarioService service;

    public ServicioBancarioController(IServicioBancarioService service) {
        this.service = service;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServicioBancario> crearServicio(@RequestBody ServicioBancario servicioBancario){ //TODO ver que devolver
        return new ResponseEntity<>(service.crearServicio(servicioBancario), HttpStatus.CREATED);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ServicioBancario>> listarServicios(){
        return ResponseEntity.ok(service.listarServiciosBancarios());
    }

    @PostMapping(value = "/{idServicio}/usuarios/{idUsuario}")
    public ResponseEntity<Void> registrarUsuario(@PathVariable Long idServicio, @PathVariable Long idUsuario) throws BbvaNotFoundException {
        service.registrarUsuario(idServicio, idUsuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
