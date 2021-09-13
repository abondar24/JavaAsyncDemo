package org.abondar.expiermental.async.rx.server;

import io.reactivex.netty.protocol.http.server.HttpServer;
import org.abondar.experimental.async.command.Command;
import rx.Observable;

import java.math.BigDecimal;

/**
 * Created by alexabon on 2/9/2017.
 */
public class RestCurrencyServer implements Command {
    private static final BigDecimal RATE = new BigDecimal("1.06555");

    @Override
    public void execute() {
        HttpServer
                .newServer(8088)
                .start((req, resp) -> {
                    String amountStr = req.getDecodedPath().substring(9);
                    BigDecimal amount = new BigDecimal(amountStr);
                    Observable<String> response = Observable
                            .just(amount)
                            .map(eur -> eur.multiply(RATE))
                            .map(usd -> "{\"EUR\": " + amount + ", " +
                                    "\"USD\": " + usd + "}");
                    return resp.writeString(response); })
                .awaitShutdown();
    }
}
