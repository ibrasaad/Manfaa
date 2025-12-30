package com.v1.manfaa.DTO.In.Ai;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstimateHoursDTOIn {
    @NotBlank(message = "category must not be empty")
    private String category;
    @NotBlank(message = "description must not be empty")
    private String description;
    @NotBlank(message = "description must not be empty")
    private String deliverables;

}
