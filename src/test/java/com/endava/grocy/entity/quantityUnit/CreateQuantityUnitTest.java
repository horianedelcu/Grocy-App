package com.endava.grocy.entity.quantityUnit;

import com.endava.grocy.TestBaseClass;
import com.endava.grocy.model.EntityType;
import com.endava.grocy.model.QuantityUnit;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

public class CreateQuantityUnitTest extends TestBaseClass{

    @Test
    public void shouldCreateQuantityUnit() {

        //GIVEN
        QuantityUnit quantityUnit = dataProvider.getQuantityUnit();
        //does this quantity already exists?
        //GET quantityList -> check if it exits
        // if it exits, delete it


        //WHEN
        Response response = entityClient.createEntity(EntityType.QUANTITY_UNIT, quantityUnit);

        //THEN
        response.then().statusCode(HttpStatus.SC_OK)
                .body("created_object_id",notNullValue());
    }
}
