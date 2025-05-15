package com.app.fineapp.controller;

import com.app.fineapp.dto.OperationDTO;
import com.app.fineapp.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/operations")
public final class OperationController {
    private final OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    public CompletableFuture<List<OperationDTO>> getAllOperations() {
        return operationService.getAllOperations();
    }

    @GetMapping("/{id}")
    public CompletableFuture<OperationDTO> getOperationById(@PathVariable int id) {
        return operationService.getOperationById(id);
    }

    @PostMapping
    public CompletableFuture<OperationDTO> createOperation(@RequestBody OperationDTO operation) {
        return operationService.createOperation(operation);
    }

    @PutMapping
    public CompletableFuture<OperationDTO> updateOperation(@RequestBody OperationDTO operation) {
        return operationService.updateOperation(operation);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<Void> deleteOperation(@PathVariable int id) {
        return operationService.deleteOperationById(id);
    }
}
