package com.endava.grocy.stock;

import com.endava.grocy.TestBaseClass;
import com.endava.grocy.client.EntityClient;
import com.endava.grocy.model.EntityType;
import com.endava.grocy.model.Product;
import com.endava.grocy.model.Stock;
import com.endava.grocy.model.TransationType;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class AddStockTest extends TestBaseClass {

    @Test
    public void shouldAddStock() {

        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Integer locationId = grocyFixture.getLocation().getId();

        Stock stock = dataProvider.getStock();

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_OK)
                .body("size()", is(1))
                .body("id[0]", is(notNullValue()))
                .body("amount[0]", is(stock.getAmount().toString()))
                .body("location_id[0]", is(locationId.toString()));
    }


    //bug
    @Test
    public void shouldFailToAddStockGivenTransactionTypeNotPurchase() {

        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Integer locationId = grocyFixture.getLocation().getId();

        Stock stock = dataProvider.getStock();
        stock.setTransationType(TransationType.CONSUME);

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_OK)
                .body("size()", is(1))
                .body("id[0]", is(notNullValue()))
                .body("amount[0]", is(stock.getAmount().toString()))
                .body("location_id[0]", is(locationId.toString()));
    }

    //bug
    @Test
    public void shouldFailToAddStockGivenNegativeAmount() {

        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Integer locationId = grocyFixture.getLocation().getId();
        Stock stock = dataProvider.getStock();
        stock.setAmount(-120.5);

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_OK)
                .body("size()", is(1))
                .body("id[0]", is(notNullValue()))
                .body("amount[0]", is(stock.getAmount().toString()))
                .body("location_id[0]", is(locationId.toString()));
    }


    @Test
    public void shouldFailToAddStockGivenNegativePrice() {

        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Integer locationId = grocyFixture.getLocation().getId();
        Stock stock = dataProvider.getStock();
        stock.setPrice(-230.3);

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_OK)
                .body("size()", is(1))
                .body("id[0]", is(notNullValue()))
                .body("amount[0]", is(stock.getAmount().toString()))
                .body("location_id[0]", is(locationId.toString()));
    }

    @Test
    public void shouldFailToAddStockGivenNotExistingProduct() {

        //GIVEN
        Stock stock = dataProvider.getStock();

        //WHEN
        Response response = stockClient.addStock(Integer.MAX_VALUE, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error_message", is("Product does not exist or is inactive") );
    }


    @Test
    public void shouldFailToAddStockForADeletedProduct(){
        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);

        Integer productId = grocyFixture.getProduct().getId();
        Response deleteProductResponse = entityClient.deleteEntityById(EntityType.PRODUCTS,productId);
        deleteProductResponse.then().statusCode(HttpStatus.SC_NO_CONTENT);

        Stock stock = dataProvider.getStock();

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);

    }

//    @Test
//    public void shouldGetAllAvailableLocations() {
//        Response response = entityClient.getLocations();
//        response.then().body("id[0]",is(notNullValue()));
//    }

    @Test
    public void shouldAddStockWithDifferentLocationIdThanTheProductId() {

        Faker faker = new Faker();
        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Integer locationId = grocyFixture.getLocation().getId();

        Stock stock = dataProvider.getStock();
        Integer locationId2 = faker.number().numberBetween(1,500);
        stock.setLocationId(locationId2);

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_OK)
                .body("size()", is(1))
                .body("id[0]", is(notNullValue()))
                .body("amount[0]", is(stock.getAmount().toString()))
                .body("location_id[0]", is(locationId2.toString()));
    }


    @Test
    public void shouldAddMultipleStockEntries() {

        Faker faker = new Faker();
        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);
        Integer productId = grocyFixture.getProduct().getId();
        Integer locationId = grocyFixture.getLocation().getId();

        Stock stock1 = dataProvider.getStock();
        Stock stock2 = dataProvider.getStock();

        //WHEN
        Response response1 = stockClient.addStock(productId, stock1);
        Response response2 = stockClient.addStock(productId, stock2);

        //THEN
        response1.then().statusCode(HttpStatus.SC_OK)
                .body("size()", is(1))
                .body("id[0]", is(notNullValue()))
                .body("amount[0]", is(stock1.getAmount().toString()))
                .body("location_id[0]", is(locationId.toString()));

        response2.then().statusCode(HttpStatus.SC_OK)
                .body("size()", is(1))
                .body("id[0]", is(notNullValue()))
                .body("amount[0]", is(stock2.getAmount().toString()))
                .body("location_id[0]", is(locationId.toString()));
    }


    @Test
    public void shouldAddStockforAnUpdatedProduct() {

        //GIVEN
        grocyFixture.createEntity(EntityType.LOCATION)
                .createEntity(EntityType.QUANTITY_UNIT)
                .createEntity(EntityType.PRODUCTS);

        Integer locationId = grocyFixture.getLocation().getId();
        Integer quantityUnitId = grocyFixture.getQuantityUnit().getId();
        Integer productId = grocyFixture.getProduct().getId();

        Product product2 = dataProvider.getProduct(locationId,quantityUnitId,quantityUnitId);
        Response modifyProductResponse = entityClient.modifyEntity(EntityType.PRODUCTS,productId, product2);
        modifyProductResponse.then().statusCode(HttpStatus.SC_NO_CONTENT);

        Stock stock = dataProvider.getStock();

        //WHEN
        Response response = stockClient.addStock(productId, stock);

        //THEN
        response.then().statusCode(HttpStatus.SC_OK)
                .body("size()", is(1))
                .body("id[0]", is(notNullValue()))
                .body("amount[0]", is(stock.getAmount().toString()))
                .body("location_id[0]", is(locationId.toString()));


    }


}
