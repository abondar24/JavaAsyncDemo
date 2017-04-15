package org.abondar.experimental.javaasyncdemo.javarx;

import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

import java.math.BigDecimal;

/**
 * Created by alexabon on 2/9/2017.
 */
public class RestCurrencyServer {
    private static final BigDecimal RATE = new BigDecimal("1.06555");

    public static void main(String[] args) {
        HttpServer
                .newServer(8088)
                .start((req, resp) -> {
                    String amountStr = req.getDecodedPath().substring(1);
                    BigDecimal amount = new BigDecimal(amountStr);
                    Observable<String> response = Observable
                            .just(amount)
                            .map(eur -> eur.multiply(RATE))
                            .map(usd -> "{\"EUR\": " + amount + ", " +
                                    "\"USD\": " + usd + "}");
                    return resp.writeString(response);
                }).awaitShutdown();
    }
}
