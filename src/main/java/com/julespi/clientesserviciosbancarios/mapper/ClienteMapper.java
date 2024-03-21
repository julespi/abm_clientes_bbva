package com.julespi.clientesserviciosbancarios.mapper;

import com.julespi.clientesserviciosbancarios.dto.ClienteDto;
import com.julespi.clientesserviciosbancarios.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteDto toClienteDto(Cliente cliente);
    List<ClienteDto> toClienteListDto(List<Cliente> clientes);

    Cliente toCliente(ClienteDto dto);
}
