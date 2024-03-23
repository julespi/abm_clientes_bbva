package com.julespi.clientesserviciosbancarios.mapper;


import com.julespi.clientesserviciosbancarios.dto.ServicioBancarioDto;
import com.julespi.clientesserviciosbancarios.model.Cliente;
import com.julespi.clientesserviciosbancarios.model.ServicioBancario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ServicioBancarioMapper {

    ServicioBancarioMapper INSTANCE = Mappers.getMapper(ServicioBancarioMapper.class);

    @Mapping(target = "clientes", qualifiedByName = "toSetDnis")
    ServicioBancarioDto toServicioBancarioDto(ServicioBancario servicioBancario);

    @Named("toSetDnis")
    default Set<Integer> toSetDnis(Set<Cliente> clientes) {
        if(clientes !=null){
            return clientes.stream().map(Cliente::getDni).collect(Collectors.toSet());
        }else{
            return new HashSet<>();
        }
    }
}
