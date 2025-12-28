package com.v1.manfaa.Controller;

import com.v1.manfaa.Api.ApiResponse;
import com.v1.manfaa.DTO.In.ContractAgreementDTOIn;
import com.v1.manfaa.Model.User;
import com.v1.manfaa.Service.ContractAgreementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contract")
@RequiredArgsConstructor
public class ContractAgreementController {

    private final ContractAgreementService contractAgreementService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getContracts(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(contractAgreementService.getContracts());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createContract(@Valid @RequestBody ContractAgreementDTOIn dto,
                                            @AuthenticationPrincipal User user) {
        contractAgreementService.createContract(dto, user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Contract Created Successfully"));
    }

    @DeleteMapping("/delete/{contract_id}")
    public ResponseEntity<?> deleteContract(@PathVariable Integer contract_id,
                                            @AuthenticationPrincipal User user) {
        contractAgreementService.deleteContract(user.getId(), contract_id);
        return ResponseEntity.status(200).body(new ApiResponse("Contract Deleted Successfully"));
    }

    @PostMapping("/accept/{contract_id}")
    public ResponseEntity<?> acceptContract(@PathVariable Integer contract_id,
                                            @AuthenticationPrincipal User user) {
        contractAgreementService.setAccepted(user.getId(), contract_id);
        return ResponseEntity.status(200).body(new ApiResponse("Contract Accepted Successfully"));
    }

    @PostMapping("/reject/{contract_id}")
    public ResponseEntity<?> rejectContract(@PathVariable Integer contract_id,
                                            @AuthenticationPrincipal User user) {
        contractAgreementService.setRejected(user.getId(), contract_id);
        return ResponseEntity.status(200).body(new ApiResponse("Contract Rejected Successfully"));
    }
}