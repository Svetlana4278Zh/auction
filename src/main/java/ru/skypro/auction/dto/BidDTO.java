package ru.skypro.auction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BidDTO {
    @NotBlank
    @Size(min = 2, max = 20)
    private String bidderName;

    private OffsetDateTime bidDate;
}
