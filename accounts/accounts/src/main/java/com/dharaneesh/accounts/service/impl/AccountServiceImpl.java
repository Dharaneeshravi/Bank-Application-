package com.dharaneesh.accounts.service.impl;

import com.dharaneesh.accounts.constants.AccountsConstants;
import com.dharaneesh.accounts.dto.AccountsDto;
import com.dharaneesh.accounts.dto.CustomerDto;
import com.dharaneesh.accounts.entity.Accounts;
import com.dharaneesh.accounts.entity.Customer;
import com.dharaneesh.accounts.exception.CustomerAlreadyExistException;
import com.dharaneesh.accounts.exception.ResourceNotFoundException;
import com.dharaneesh.accounts.mapper.AccountsMapper;
import com.dharaneesh.accounts.mapper.CustomerMapper;
import com.dharaneesh.accounts.repository.AccountsRepository;
import com.dharaneesh.accounts.repository.CustomerRepository;
import com.dharaneesh.accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    /**
     *
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());
       Optional<Customer> optionalCustomer=customerRepository.findByMobileNumber(customer.getMobileNumber());

       if(optionalCustomer.isPresent())
       {
           throw new CustomerAlreadyExistException("Customer already exist with mobile number "+customer.getMobileNumber());
       }
        Customer savedCustomer=customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

    }

    /**
     *
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

     Customer customer=customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));

     Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId())
             .orElseThrow(()->new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString()));

      CustomerDto customerDto=CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
      customerDto.setAccountsDto(AccountsMapper.mspToAccountsDto(accounts,new AccountsDto()));
      return customerDto;
    }

    /**
     *
     * @param customerDto
     * @return
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated=false;

        AccountsDto accountsDto=customerDto.getAccountsDto();
        if(accountsDto!=null){

            Accounts accounts=accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(()->new ResourceNotFoundException("Account","AcountNumber",accountsDto.getAccountNumber().toString()));

            AccountsMapper.mapToAccount(accountsDto,accounts);
            accounts=accountsRepository.save(accounts);

            Long customerId=accounts.getCustomerId();

            Customer customer=customerRepository.findById(customerId)
                    .orElseThrow(()->new ResourceNotFoundException("Customer","customerId",customerId.toString()));
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated=true;
        }

        return isUpdated;
    }

    /**
     *
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer=customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.delete(customer);
        return true;

    }

    private Accounts createNewAccount(Customer customer) {

        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());

        long randemAccNumber=100000000L +new Random().nextInt(900000000);
        accounts.setAccountNumber(randemAccNumber);
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADDRESS);
        return accounts;
    }
}
