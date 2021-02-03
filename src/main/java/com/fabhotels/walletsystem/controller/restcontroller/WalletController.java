package com.fabhotels.walletsystem.controller.restcontroller;

import com.fabhotels.walletsystem.models.dto.TransactionDto;
import com.fabhotels.walletsystem.models.requestobjects.TransactionRequest;
import com.fabhotels.walletsystem.service.UserService;
import com.fabhotels.walletsystem.service.WalletService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/"+"${apiversion}/wallet")
public class WalletController {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @ApiOperation("get transaction summary")
    @ApiResponse(code = 200, message = "successful transaction summary retrieval")
    @GetMapping("/summary")
    public ResponseEntity<Set<TransactionDto>> getAllTransactionsInAUsersWallet(Principal principal){
        return ResponseEntity.ok(walletService.getAllTransactionInAUsersWallet(principal.getName()));
    }

    @ApiOperation("perform transaction in wallet")
    @ApiResponse(code = 200, message = "successful transaction")
    @PostMapping("/transaction")
    public ResponseEntity handleTransactionRequest(Principal principal,
                          @RequestBody @Valid TransactionRequest transactionRequest){
        walletService.performTransaction(principal.getName(),transactionRequest);
        //only ok response signifies successful transaction
        return ResponseEntity.ok().build();
    }

    @ApiOperation("get wallet balance")
    @ApiResponse(code = 200, message = "successful")
    @GetMapping("/balance")
    public ResponseEntity<Double> handleTransactionRequest(Principal principal){
        Double crrBalance = walletService.getCurrentBalanceInUserAccount(principal.getName());
        //only ok response signifies successful transaction
        return ResponseEntity.ok(crrBalance);
    }
}
