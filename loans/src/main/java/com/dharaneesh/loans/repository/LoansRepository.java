package com.dharaneesh.loans.repository;

import com.dharaneesh.loans.entity.Loans;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoansRepository extends JpaRepository<Loans, Loans> {
    Optional<Loans> findByMobileNumber(String mobileNumber);

    Optional<Loans> findByLoanNumber(@NotEmpty(message = "Loan Number can not be a null or empty") @Pattern(regexp="(^$|[0-9]{12})",message = "LoanNumber must be 12 digits") String loanNumber);

}
