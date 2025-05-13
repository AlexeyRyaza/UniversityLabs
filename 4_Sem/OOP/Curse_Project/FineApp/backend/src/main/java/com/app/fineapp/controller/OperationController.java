package com.app.fineapp.controller;

import com.app.fineapp.dto.OperationDTO;
import com.app.fineapp.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public final class OperationController {
    OperationService operationService;

    @Autowired
    OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/operations")
    public CompletableFuture<List<OperationDTO>> getAllOperations() {
        return operationService.getAllOperations();
    }

    @GetMapping("/operations/{id}")
    public CompletableFuture<OperationDTO> getOperationById(@PathVariable int id) {
        return operationService.getOperationById(id);
    }

    @PostMapping("/operations")
    public CompletableFuture<OperationDTO> createOperation(@RequestBody OperationDTO operation) {
        return operationService.createOperation(operation);
    }

    @PutMapping("/operations")
    public CompletableFuture<OperationDTO> updateOperation(@RequestBody OperationDTO operation) {
        return operationService.updateOperation(operation);
    }

    @DeleteMapping("/operations/{id}")
    public CompletableFuture<Void> deleteOperation(@PathVariable int id) {
        return operationService.deleteOperationById(id);
    }
}
