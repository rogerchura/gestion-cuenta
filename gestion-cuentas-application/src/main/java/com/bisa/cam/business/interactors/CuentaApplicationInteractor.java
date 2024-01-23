package com.bisa.cam.business.interactors;


import com.bisa.cam.business.repositories.CuentaRepository;
import com.bisa.cam.domain.Cuenta;
import com.bisa.cam.transport.ConsultaSaldoResponseDto;
import com.bisa.cam.transport.CrearCuentaRequestDto;
import com.bisa.cam.transport.CrearCuentaResponseDto;
import com.bisa.cam.transport.ErrorResponseDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaApplicationInteractor {

    private final Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private CuentaRepository repository;

    public ConsultaSaldoResponseDto consultaSaldo(Long nrocuenta) {

        Boolean esferiado = false;
        Cuenta cuenta = null;
        ConsultaSaldoResponseDto responseDto = null;
        try {
            //StringUtils.isNumeric(fechaId);
            cuenta = repository.consultaCuenta(nrocuenta);
        } catch (Exception e) {
           logger.error(e.getMessage(), e);
           cuenta = null;
        }
        if(cuenta!=null && cuenta.getId()!=null){
            responseDto = new ConsultaSaldoResponseDto();
            responseDto.setNroCuenta(cuenta.getNroCuenta());
            responseDto.setIdCliente(cuenta.getIdCliente());
            responseDto.setSaldo(cuenta.getSaldo());
            responseDto.setEstado(cuenta.getEstado());
            responseDto.setMoneda(cuenta.getMoneda());
            responseDto.setTipoCuenta(cuenta.getTipoCuenta());
            responseDto.setFechaApertura(cuenta.getFechaApertura());
            responseDto.setLugarApertura(cuenta.getLugarApertura());
        }
        return responseDto;
    }

    public CrearCuentaResponseDto registraCuenta(CrearCuentaRequestDto request) {

        Boolean registrado = false;
        Cuenta cuenta = null;
        CrearCuentaResponseDto responseDto = new CrearCuentaResponseDto();
        ErrorResponseDto error = new ErrorResponseDto();

        try {
            if(request!=null){
                cuenta = new Cuenta();
                cuenta.setNroCuenta(request.getNroCuenta());
                cuenta.setTipoCuenta(request.getTipoCuenta());
                cuenta.setMoneda(request.getMoneda());
                cuenta.setIdCliente(request.getIdCliente());
                registrado = repository.registrarCuenta(cuenta);
                if(registrado){
                    responseDto.setError(error);
                    responseDto.setNroCuenta(cuenta.getNroCuenta());
                }
            }
            if(!registrado){
                error.setCodError(260L);
                error.setDescError("No se puede crear la cuenta");
                responseDto.setError(error);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error.setCodError(270L);
            error.setDescError("No se puede crear la cuenta."+e.getMessage());
            responseDto.setError(error);
        }
        return responseDto;
    }

}
