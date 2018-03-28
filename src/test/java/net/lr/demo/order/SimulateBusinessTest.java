package net.lr.demo.order;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimulateBusinessTest {
    Logger log = LoggerFactory.getLogger(this.getClass());

    private Random random;
    
    String[] customers = new String[] {"001", "002", "003"};

    @Test
    public void simulate() throws InterruptedException {
        random = new Random();
        log.info("Test started");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int c=0; c < 100; c++) {
            executor.submit(this::simulateOrder);
        }
        executor.shutdown();
        executor.awaitTermination(100, TimeUnit.SECONDS);
    }

    private void simulateOrder() {
        String orderId = randomId(5);
        String deliveryId = randomId(7);
        String customerId = customers[random.nextInt(customers.length)];
        new Order(orderId, customerId, new BigDecimal(random.nextInt(1000))).place();
        randomSleep();
        Delivery delivery = new Delivery(deliveryId, orderId, "Basel");
        delivery.send();
        if (random.nextInt(10) > 2) {
            randomSleep();
            delivery.deliver();
        } else {
            delivery.lost();
        }
    }

    private void randomSleep() {
        try {
            Thread.sleep(10000 + random.nextInt(10000));
        } catch (InterruptedException e) {
        }
    }

    private String randomId(int digits) {
        return UUID.randomUUID().toString().substring(0, digits);
    }
}
