package org.abondar.experimental.async.vertx.cluster.eventbus;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.cluster.infinispan.InfinispanClusterManager;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventbusInstance {

    private static final Logger logger = LoggerFactory.getLogger(EventbusInstance.class);

    public void runInstance(){
        ClusterManager clusterManager = new InfinispanClusterManager();
        VertxOptions vertxOptions = new VertxOptions();
        vertxOptions.setClusterManager(clusterManager);

        Vertx.clusteredVertx(vertxOptions,asyncResult -> {
            if (asyncResult.succeeded()){
                logger.info("Instance Launched");
                Vertx vertx = asyncResult.result();
                vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.eventbus.MessageSource",
                        new DeploymentOptions().setInstances(2));
                vertx.deployVerticle("org.abondar.experimental.async.vertx.verticle.eventbus.HttpServer");
            } else {
                logger.error("Couldn't start", asyncResult.cause());
            }
        });
    }

}
