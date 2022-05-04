package com.endava.grocy.client;

import com.endava.grocy.model.Entity;
import com.endava.grocy.model.EntityType;
import com.endava.grocy.model.Product;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EntityClient extends BaseClient {

    public Response createEntity(EntityType entityType, Entity entity) {

        return getBasicRestConfig()
                .contentType(ContentType.JSON)
                .body(entity)
                .pathParam("entity", entityType)
                .post("/objects/{entity}");
    }

    public Response deleteEntityById(EntityType entityType, Integer productId) {
        return getBasicRestConfig()
                .pathParam("entity", entityType)
                .pathParam("objectId", productId)
                .delete("objects/{entity}/{objectId}");

    }

    public Response getLocations(){
        return getBasicRestConfig()
                .get("objects/locations");
    }

    public Response modifyEntity(EntityType entityType, Integer productId, Entity entity){
        return getBasicRestConfig()
                .contentType(ContentType.JSON)
                .body(entity)
                .pathParam("entity",entityType)
                .pathParam("objectId", productId)
                .put("/objects/{entity}/{objectId}");
    }
}
