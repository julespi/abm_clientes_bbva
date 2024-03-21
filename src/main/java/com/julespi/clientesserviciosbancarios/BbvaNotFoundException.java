package com.julespi.clientesserviciosbancarios;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BbvaNotFoundException extends Exception{
}
