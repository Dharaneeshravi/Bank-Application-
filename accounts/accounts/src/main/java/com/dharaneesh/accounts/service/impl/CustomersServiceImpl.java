package com.dharaneesh.accounts.service.impl;

import com.dharaneesh.accounts.dto.AccountsDto;
import com.dharaneesh.accounts.dto.CardsDto;
import com.dharaneesh.accounts.dto.CustomerDetailsDto;
import com.dharaneesh.accounts.dto.LoansDto;
import com.dharaneesh.accounts.entity.Accounts;
import com.dharaneesh.accounts.entity.Customer;
import com.dharaneesh.accounts.exception.ResourceNotFoundException;
import com.dharaneesh.accounts.mapper.AccountsMapper;
import com.dharaneesh.accounts.mapper.CustomerMapper;
import com.dharaneesh.accounts.repository.AccountsRepository;
import com.dharaneesh.accounts.repository.CustomerRepository;
import com.dharaneesh.accounts.service.ICustomersService;
import com.dharaneesh.accounts.service.client.CardsClient;
import com.dharaneesh.accounts.service.client.LoansClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardsClient cardsClient;
    private final LoansClient loansClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {


        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(()->new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
