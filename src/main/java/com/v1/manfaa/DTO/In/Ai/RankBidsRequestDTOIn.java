package com.v1.manfaa.DTO.In.Ai;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankBidsRequestDTOIn {
    @NotNull(message = "request_id not blank")
    private Integer request_id;
    @NotNull(message = "request_id not blank")
    private Integer top_k;
}
