package com.expressbank.dto.stoks.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Stock {
    private String company;
    private String description;
    @JsonProperty("initial_price")
    private Double initialPrice;
    @JsonProperty("price_2002")
    private Double middleprice;
    @JsonProperty("price_2007")
    private Double lastPrice;
    private String symbol;

}
