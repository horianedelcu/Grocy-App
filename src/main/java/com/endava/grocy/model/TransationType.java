package com.endava.grocy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TransationType {

    @JsonProperty("purchase")
    PURCHASE,
    @JsonProperty("consume")
    CONSUME,
    @JsonProperty("inventort-correction")
    INVENTORY_CORRECTION,
    @JsonProperty("product-opened")
    PRODUCT_OPENED;
}
