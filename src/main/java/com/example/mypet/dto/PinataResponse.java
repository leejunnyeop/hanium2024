package com.example.mypet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PinataResponse {
    @Schema(example = "QmPAJV9Q2sHd673eqwa1onhTXRRCKRvXtQFwK87bDH5Hcy")
    private String IpfsHash;
    @Schema(example = "32612")
    private int PinSize;
    @Schema(example = "2024-07-21T09:07:01.867Z")
    private String Timestamp;
    // getters and setters
}