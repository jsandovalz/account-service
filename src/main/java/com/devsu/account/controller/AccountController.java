package com.devsu.account.controller;

import com.devsu.account.dto.AccountDTO;
import com.devsu.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(service.getByAccountNumber(accountNumber));
    }

    @GetMapping()
    public ResponseEntity<List<AccountDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

        @PostMapping
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> update(@PathVariable String accountNumber, @RequestBody AccountDTO dto) {
        return ResponseEntity.ok(service.update(accountNumber, dto));
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> delete(@PathVariable String accountNumber) {
        service.delete(accountNumber);
        return ResponseEntity.noContent().build();
    }
}
