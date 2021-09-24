package org.abondar.experimental.async.vertx.verticle.eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.abondar.experimental.async.vertx.service.MessageDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
public class MessageDataVerticleTest {

    private MessageDataService dataService;


    @BeforeEach
    public void prepare(Vertx vertx,VertxTestContext context) {
        vertx.deployVerticle(new MessageDataVerticle(), context.succeeding(id -> {
            dataService = MessageDataService.createProxy(vertx, "message.data-service");
            context.completeNow();
        }));
    }

    @Test
    public void noMessageSourcesTest(VertxTestContext context) {
        String msgId = UUID.randomUUID().toString();
        dataService.valueFor(msgId, context.failing(err -> context.verify(() -> {
            assertTrue(err.getMessage().startsWith("No value"));
            context.completeNow();
        })));

        dataService.specialMessage(context.succeeding(data -> context.verify(() -> {
            String msg = data.getString("body");
            assertFalse(msg.contains("-"));
            context.completeNow();
        })));
    }

    @Test
    public void messageSourcesTest(Vertx vertx,VertxTestContext context) {
        String msgId1 = UUID.randomUUID().toString();
        JsonObject m1 = new JsonObject();
        m1.put("id",msgId1);
        m1.put("body","test");

        String msgId2 = UUID.randomUUID().toString();
        JsonObject m2 = new JsonObject();
        m2.put("id",msgId2);
        m2.put("body","test-test");

        vertx.eventBus()
                        .publish("message.updates",m1)
                                .publish("message.updates",m2);

        dataService.valueFor(msgId1, context.succeeding(data -> context.verify(() -> {
           assertEquals(msgId1,data.getString("id"));
            assertEquals("test",data.getString("body"));
            context.completeNow();
        })));

        dataService.specialMessage(context.succeeding(data -> context.verify(() -> {
            String msg = data.getString("special");
            assertTrue(msg.contains("-"));
            context.completeNow();
        })));
    }

}
