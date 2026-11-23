package com.dharaneesh.accounts.service;

import com.dharaneesh.accounts.dto.CustomerDetailsDto;
import jakarta.validation.constraints.Pattern;

public interface ICustomersService {
    CustomerDetailsDto fetchCustomerDetails( String mobileNumber);
}
