package com.paysnap.paysnap.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDto {

    @NotBlank(message = "Description boş ola bilməz")
    @Size(min = 3, max = 255, message = "Description 3-255 simvol arasında olmalıdır")
    private String description;

    @NotNull(message = "Amount boş ola bilməz")
    @Positive(message = "Amount 0-dan böyük olmalıdır")
    private Double amount;

    @NotBlank(message = "Currency boş ola bilməz")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "Currency 3 hərfli ISO formatda olmalıdır (məs: USD, EUR)"
    )
    private String currency;
}
