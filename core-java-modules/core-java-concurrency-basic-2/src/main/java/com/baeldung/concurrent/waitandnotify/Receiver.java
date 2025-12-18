package com.baeldung.concurrent.waitandnotify;

import java.util.concurrent.ThreadLocalRandom;

public class Receiver implements Runnable {

    private Data load;
 
    public Receiver(Data load) {
        this.load = load;
    }
 
    public void run() {

        for (String recvMsg = load.receive(); !"End".equals(recvMsg); recvMsg = load.receive()) {
            
            System.out.println(recvMsg);

            Util.sleep(1000, 5000);
        }
    }
}