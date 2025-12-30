package com.v1.manfaa.DTO.In.Ai;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryRagDTOIn {
    @NotBlank(message = "question must not be empty")
    private String question;
}
