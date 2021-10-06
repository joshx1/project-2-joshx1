package project2;

import java.util.concurrent.ExecutorService;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// SyncOrdered - iterate and call onEvent
// aSyncUnordered - pool
// ASyncOrdered - bounded blocking queue
public class PublishSubscribe {
    public static void main(String[] args) {
        String outputFileName = "KitchenOld.json";
        SubscriberOld <String> subscriber1 = new SubscriberOld<>(outputFileName);
        SubscriberOld <String> subscriber2 = new SubscriberOld<>(outputFileName);
        SubscriberOld <String> subscriber3 = new SubscriberOld<>(outputFileName);
        //SynchronousOrderedDispatchBroker broker = new SynchronousOrderedDispatchBroker(new ArrayList<>());
        AsyncUnorderedDispatchBroker broker = new AsyncUnorderedDispatchBroker(new ArrayList<>());
        //AsyncOrderedDispatchBroker broker = new AsyncOrderedDispatchBroker(new ArrayList<>());
        broker.subscribe(subscriber1);
        broker.subscribe(subscriber2);
        broker.subscribe(subscriber3);
        ExecutorService publisher1 = Executors.newSingleThreadExecutor();
        ExecutorService publisher2 = Executors.newSingleThreadExecutor();
        publisher1.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    broker.publish("0");
                    System.out.println("AAA");
                }
            }
        });
        publisher2.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    broker.publish("1");
                    System.out.println("ZZZ");
                }
            }
        });
        publisher1.shutdown();
        publisher2.shutdown();
        try {
            publisher1.awaitTermination(10, TimeUnit.HOURS);
            publisher2.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        broker.shutdown();
        subscriber1.printInbox();
        subscriber2.printInbox();
        subscriber3.printInbox();
    }
}