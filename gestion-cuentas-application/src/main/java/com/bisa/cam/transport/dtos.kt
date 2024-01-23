package com.bisa.cam.transport

import com.fasterxml.jackson.annotation.JsonRootName
import java.sql.Timestamp


@JsonRootName("CrearCuentaResponse")
open class CrearCuentaResponseDto(
    var error : ErrorResponseDto?=null,
    var nroCuenta: Long?=null
)

@JsonRootName("CrearCuentaRequest")
open  class CrearCuentaRequestDto(
    var nroCuenta: Long,
    var tipoCuenta: String,
    var moneda: String,
    var idCliente: Long
)

@JsonRootName("SaldoResponse")
open class SaldoResponseDto(
    var error : ErrorResponseDto?=null,
    var saldoCuenta: ConsultaSaldoResponseDto?=null
)

@JsonRootName("ErrorResponse")
open class ErrorResponseDto(
    var codError: Long?=0,
    var descError: String?="Sin errores"
)

@JsonRootName("ConsultaSaldoResponse")
open class ConsultaSaldoResponseDto(
    var nroCuenta: Long?=null,
    var tipoCuenta: String?=null,
    var moneda: String?=null,
    var saldo: Double?=null,
    var idCliente: Long?=null,
    var fechaApertura: Timestamp?=null,
    var lugarApertura: String?=null,
    var estado: String?=null
)




