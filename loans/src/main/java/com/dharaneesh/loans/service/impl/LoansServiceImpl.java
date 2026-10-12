package com.dharaneesh.loans.service.impl;

import com.dharaneesh.loans.constants.LoansConstants;
import com.dharaneesh.loans.dto.LoansDto;
import com.dharaneesh.loans.entity.Loans;
import com.dharaneesh.loans.exception.LoanAlreadyExistsException;
import com.dharaneesh.loans.exception.ResourceNotFoundException;
import com.dharaneesh.loans.mapper.LoansMapper;
import com.dharaneesh.loans.repository.LoansRepository;
import com.dharaneesh.loans.service.ILoansService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private final LoansRepository loansRepository;

    /**
     *
     * @param mobileNumber
     */
    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loans> optionalLoans= loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    /**
     *
     * @param mobileNumber
     * @return
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {

        Loans loans=loansRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Loans","MobileNumber",mobileNumber));

        return LoansMapper.mapToLoansDto(loans,new LoansDto());
    }

    /**
     *
     * @param loansDto
     * @return
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {

        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return  true;
    }

    /**
     *
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {

        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.delete(loans);
        return true;
    }


    private Loans createNewLoan(String mobileNumber) {

        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }
}
