package project2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// SyncOrdered - iterate and call onEvent
// aSyncUnordered - pool
// ASyncOrdered - bounded blocking queue
public class PublishSubscribe {

    static Broker broker = null;

    public static void main(String[] args) {
        ReadConfigSettings readConfigSettings = new ReadConfigSettings(args);
        ConfigInputs configInputs = readConfigSettings.extractConfigSettings();
        SubscriberOld<String> subscriberOld1 = new SubscriberOld<>(configInputs.getOutputFileNameOld1());
        SubscriberOld<String> subscriberOld2 = new SubscriberOld<>(configInputs.getOutputFileNameOld2());
        SubscriberNew<String> subscriberNew1 = new SubscriberNew<>(configInputs.getOutputFileNameNew1());
        SubscriberNew<String> subscriberNew2 = new SubscriberNew<>(configInputs.getOutputFileNameNew2());
        if (configInputs.getBrokerType().equals("syncOrdered")) {
            broker = new SynchronousOrderedDispatchBroker(new ArrayList<>());
        } else if (configInputs.getBrokerType().equals("asyncUnordered")) {
            broker = new AsyncUnorderedDispatchBroker(new ArrayList<>());
        } else if (configInputs.getBrokerType().equals("asyncOrdered")) {
            broker = new AsyncOrderedDispatchBroker(new ArrayList<>());
        } else {
            System.out.println("Broker config setting specified does not exist.");
            System.exit(1);
        }

        broker.subscribe(subscriberOld1);
        broker.subscribe(subscriberOld2);
        broker.subscribe(subscriberNew1);
        broker.subscribe(subscriberNew2);
        Thread publisherThread1 = new Thread() {
            public void run() {
                Publisher publisher1 = null;
                try {
                    publisher1 = new Publisher("reviews_Home_and_Kitchen_5.json");
                } catch (UnsupportedEncodingException exc) {
                    exc.printStackTrace();
                } catch (FileNotFoundException exc) {
                    exc.printStackTrace();
                }
                try {
                    publisher1.callPublish(broker);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread publisherThread2 = new Thread() {
            public void run() {
                Publisher publisher2 = null;
                try {
                    publisher2 = new Publisher("reviews_Apps_for_Android_5.json");
                } catch (UnsupportedEncodingException exc) {
                    exc.printStackTrace();
                } catch (FileNotFoundException exc) {
                    exc.printStackTrace();
                }
                try {
                    publisher2.callPublish(broker);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        publisherThread1.start();
        publisherThread2.start();
        try {
            publisherThread1.join(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            publisherThread2.join(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        broker.shutdown();
        subscriberOld1.shutdown();
        subscriberOld2.shutdown();
        subscriberNew1.shutdown();
        subscriberNew2.shutdown();
    }
}