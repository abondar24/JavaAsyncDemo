package org.abondar.experimental.async.javarx.data;

/**
 * Created by alexabon on 2/9/2017.
 */
public class Dish {
    private final byte[] oneKb = new byte[1024];
    private final int id;

    public Dish(int id){
        this.id=id;
        System.out.println("Created: "+id);
    }


    public String toString(){
        return String.valueOf(id);
    }
}
