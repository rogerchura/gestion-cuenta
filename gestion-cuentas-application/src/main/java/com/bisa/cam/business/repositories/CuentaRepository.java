package com.bisa.cam.business.repositories;

import com.bisa.cam.domain.Cuenta;


public interface CuentaRepository {


    Cuenta consultaCuenta(Long numCuenta) throws Exception;

    Boolean registrarCuenta(Cuenta cuenta) throws Exception;

    /*
    TransferApplication saveNewApplication(TransferApplication transferApplication) throws TransactionException;

    TransferApplication updateExistingApplication(TransferApplication transferApplication);

    Collection<TransferApplication> fetchAllApplications();

    Optional<TransferApplication> findApplicationById(TransferApplicationId uuid);
    */
}
