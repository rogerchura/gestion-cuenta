

/**Ejectuar con usuario  root/desa  */
CREATE DATABASE dbcuentas;

SHOW DATABASES;

USE dbcuentas;

CREATE TABLE dbcuentas.cuenta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nroCuenta DECIMAL(10, 0) UNIQUE CONSTRAINT,
    tipoCuenta VARCHAR(2),
    moneda VARCHAR(3),
    idCliente INT,
    saldo DECIMAL(12, 2),
    fechaApertura TIMESTAMP,
    lugarApertura VARCHAR(30),
    estado VARCHAR(3)
);


INSERT INTO dbcuentas.cuenta
( nroCuenta,  tipoCuenta,  moneda,  idCliente,
  saldo,  fechaApertura,  lugarApertura,  estado)
VALUES
( 12345, 'CA',  'BOB',  1000,
  10.00, current_timestamp, 'LA PAZ', 'ACT');


INSERT INTO dbcuentas.cuenta
( nroCuenta,  tipoCuenta,  moneda,  idCliente,
  saldo,  fechaApertura,  lugarApertura,  estado)
VALUES
( 123456, 'CA',  'USD',  1000,
  20.00, current_timestamp, 'LA PAZ', 'ACT');

commit;

select id, nroCuenta,tipoCuenta, moneda,idCliente,
saldo,fechaApertura,lugarApertura, estado 
from dbcuentas.cuenta;


   












