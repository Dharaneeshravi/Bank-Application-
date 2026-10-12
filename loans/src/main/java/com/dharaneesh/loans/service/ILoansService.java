package com.dharaneesh.loans.service;

import com.dharaneesh.loans.dto.LoansDto;

public interface ILoansService {

    /**
     *
     * @param mobileNumber
     */
    void createLoan(String mobileNumber);

    /**
     *
     * @param mobileNumber
     * @return
     */
    LoansDto fetchLoan(String mobileNumber);


    /**
     *
     * @param loansDto
     * @return
     */
    boolean updateLoan(LoansDto loansDto);

    /**
     *
     * @param mobileNumber
     * @return
     */
    boolean deleteLoan(String mobileNumber);
}
