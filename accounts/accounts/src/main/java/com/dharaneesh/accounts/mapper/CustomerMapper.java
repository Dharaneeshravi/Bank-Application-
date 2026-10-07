package com.dharaneesh.accounts.mapper;

import com.dharaneesh.accounts.dto.CustomerDto;
import com.dharaneesh.accounts.entity.Customer;

public class CustomerMapper {


    public static Customer mapToCustomer(CustomerDto customerDto,Customer customer) {

        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;

    }



    public static CustomerDto mapToCustomerDto(Customer customer,CustomerDto customerDto) {

        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }
}
