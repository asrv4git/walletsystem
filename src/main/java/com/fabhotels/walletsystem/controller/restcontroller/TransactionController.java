package com.fabhotels.walletsystem.controller.restcontroller;

import com.fabhotels.walletsystem.models.dto.TransactionDto;
import com.fabhotels.walletsystem.models.requestobjects.TransactionRequest;
import com.fabhotels.walletsystem.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@RequestMapping("/"+"${apiversion}/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @ApiOperation("get transaction summary")
    @ApiResponse(code = 200, message = "successful transaction summary retrieval")
    @GetMapping("/summary")
    public ResponseEntity<Set<TransactionDto>> getAllTransactionInAUsersWallet(@RequestParam @NotNull Long userId){
        return ResponseEntity.ok(transactionService.getAllTransactionsById(userId));
    }

    @ApiOperation("perform transaction in wallet")
    @ApiResponse(code = 200, message = "successful transaction")
    @PostMapping
    public ResponseEntity handleTransactionRequest(@RequestBody @Valid TransactionRequest transactionRequest){
        transactionService.performTransaction(transactionRequest);
        //only ok response signifies successful transaction
        return ResponseEntity.ok().build();
    }
}
