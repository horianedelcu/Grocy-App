package com.endava.grocy.entity.product;

import com.endava.grocy.TestBaseClass;
import com.endava.grocy.model.EntityType;
import com.endava.grocy.model.Product;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

public class ModifyProductTest extends TestBaseClass {

    @Test
    public void shouldModifyProduct2() {

        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);

        Integer locationId = grocyFixture.getLocation().getId();
        Integer quantityUnitId = grocyFixture.getQuantityUnit().getId();
        Integer productId = grocyFixture.getProduct().getId();

        Product product2 = dataProvider.getProduct(locationId,quantityUnitId,quantityUnitId);

        //WHEN
        Response modifyProductResponse = entityClient.modifyEntity(EntityType.PRODUCTS,productId, product2);

        //THEN
        modifyProductResponse.then().statusCode(HttpStatus.SC_NO_CONTENT);

    }
}
