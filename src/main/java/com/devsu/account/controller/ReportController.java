package com.devsu.account.controller;

import com.devsu.account.dto.ReportDTO;
import com.devsu.account.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService service;

    @GetMapping
    public ResponseEntity<ReportDTO> generate(
            @RequestParam String client,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate from,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate to) {
        return ResponseEntity.ok(service.generate(client,from,to));
    }
}
