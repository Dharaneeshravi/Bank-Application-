package com.dharaneesh.accounts.controller;

import com.dharaneesh.accounts.constants.AccountsConstants;
import com.dharaneesh.accounts.dto.CustomerDto;
import com.dharaneesh.accounts.dto.ResponseDto;
import com.dharaneesh.accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping(path = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AccountsController {

    private final IAccountService accountService;


    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto)
    {
        accountService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }

    @GetMapping(path = "/fetch")
    public ResponseEntity<CustomerDto> fetchAccountsDetails(@RequestParam String mobileNumber)
    {
        return ResponseEntity.ok(accountService.fetchAccount(mobileNumber));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ResponseDto>  updateAccountDetails(@RequestBody CustomerDto customerDto)
    {
        boolean isUpdated=accountService.updateAccount(customerDto);

        if(isUpdated) {
            return ResponseEntity.ok(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else {
            return  ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountsConstants.STATUS_417,AccountsConstants.MESSAGE_417_UPDATE));
        }
    }


}
