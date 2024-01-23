package com.bisa.cam.transport.service;


import com.bisa.cam.business.interactors.CuentaApplicationInteractor;
import com.bisa.cam.transport.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Implementacion de la API
 *
 */
@RestController
public class ServiceApplicationAPIController implements ServiceApplicationAPI {
    @Autowired
    private CuentaApplicationInteractor applicationInteractor;

    private final Logger logger = LogManager.getLogger(getClass());


    @Override
    public ResponseEntity consultaSaldo(String nroCuenta) {

        Long numcuenta = Long.valueOf(Optional.ofNullable(nroCuenta).orElse("0"));

        SaldoResponseDto responseDto = new SaldoResponseDto();
        ErrorResponseDto errorDto = new ErrorResponseDto();
        ConsultaSaldoResponseDto saldo = applicationInteractor.consultaSaldo(numcuenta);
        if(saldo!=null){
            responseDto.setError(errorDto);
            responseDto.setSaldoCuenta(saldo);
        }else{
            errorDto.setCodError(250L);
            errorDto.setDescError("Error al consutar el saldo.");
            responseDto.setError(errorDto);
            responseDto.setSaldoCuenta(new ConsultaSaldoResponseDto());
        }
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity crearCuenta(CrearCuentaRequestDto requestDto) {

        CrearCuentaResponseDto responseDto = new CrearCuentaResponseDto();
        if(requestDto==null){
            responseDto.getError().setCodError(230L);
            responseDto.getError().setDescError("La solicitud de creacion no es valida.");
            ResponseEntity.ok(responseDto);
        }
        /*if(requestDto.getNroCuenta()==null || requestDto.getMoneda() == null ){
            responseDto.getError().setCodError(240l);
            responseDto.getError().setDescError("debe especificar una cuenta o una moneda valida.");
            ResponseEntity.ok(responseDto);
        }*/
        responseDto = applicationInteractor.registraCuenta(requestDto);
        return ResponseEntity.ok(responseDto);

    }

}