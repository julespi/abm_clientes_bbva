package com.julespi.clientesserviciosbancarios.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "servicios_bancarios")
@Getter @Setter
public class ServicioBancario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 4)
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "sector")
    private String sector;

    @Column(name = "cantidad_personas_atiende")
    private Integer cantidadPersonasAtiende;

    private Boolean atencionExclusiva;

    private Boolean soporteTecnico;

    @ManyToMany
    @JoinTable(name = "clientes_servicios_bancarios",
            joinColumns = @JoinColumn(name = "servicio_bancario_id"),
            inverseJoinColumns = @JoinColumn(name = "cliente_id"))
    private Set<Cliente> clientes = new HashSet<>();

}
