package com.endava.grocy.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends Entity{


    @JsonProperty("location_id")
    private Integer locationId;
    @JsonProperty("qu_id_purchase")
    private Integer quantityPurchaseId;
    @JsonProperty("qu_id_stock")
    private Integer quantityStockId;
    @JsonProperty("min_stock_amount")
    private Integer minStockAmount;
    @JsonProperty("qu_factor_purchase_to_stock")
    private Double quantityFactorPurchaseToStock;


}
