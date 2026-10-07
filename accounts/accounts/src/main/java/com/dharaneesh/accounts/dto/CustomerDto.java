package com.dharaneesh.accounts.dto;

import com.dharaneesh.accounts.entity.Accounts;
import lombok.Data;

@Data
public class CustomerDto {

    private String name;

    private String email;

    private String mobileNumber;

    private AccountsDto accountsDto;
}
