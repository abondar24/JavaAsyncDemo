package org.abondar.experimental.async.multithread.command;

import org.abondar.experimental.async.command.Command;
import org.abondar.experimental.async.multithread.exchanger.ExchangeConsumer;
import org.abondar.experimental.async.multithread.exchanger.ExchangeProducer;

import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangerCommand implements Command {
    @Override
    public void execute() {
        Exchanger<List<Integer>> exchanger = new Exchanger<>();

        ExchangeProducer producer = new ExchangeProducer(exchanger,5);
        ExchangeConsumer consumer = new ExchangeConsumer(exchanger);

        producer.start();
        consumer.start();
    }
}
