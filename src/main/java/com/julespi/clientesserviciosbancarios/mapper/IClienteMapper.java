package com.julespi.clientesserviciosbancarios.mapper;

import com.julespi.clientesserviciosbancarios.dto.ClienteDto;
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

@Mapper(componentModel = "spring", uses = {IServicioBancarioMapper.class})
public interface IClienteMapper {

    IClienteMapper INSTANCE = Mappers.getMapper(IClienteMapper.class);

    @Mapping(target = "serviciosBancarios", qualifiedByName = "toSetCodigos")
    ClienteDto toClienteDto(Cliente cliente);
    List<ClienteDto> toClienteListDto(List<Cliente> clientes);

    @Mapping(target = "serviciosBancarios", ignore = true)
    Cliente toCliente(ClienteDto dto);

    @Named("toSetCodigos")
    default Set<String> toSetCodigos(Set<ServicioBancario> serviciosBancarios) {
        if(serviciosBancarios !=null){
            return serviciosBancarios.stream().map(ServicioBancario::getCodigo).collect(Collectors.toSet());
        }else{
            return new HashSet<>();
        }

    }

}
