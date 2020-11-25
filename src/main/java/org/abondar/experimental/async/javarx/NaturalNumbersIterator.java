package org.abondar.experimental.async.javarx;

import java.math.BigInteger;
import java.util.Iterator;

/**
 * Created by abondar on 2/2/17.
 */
public class NaturalNumbersIterator implements Iterator<BigInteger> {
    private BigInteger current = BigInteger.ZERO;

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public BigInteger next() {
        current = current.add(BigInteger.ONE);
        return current;
    }
}
