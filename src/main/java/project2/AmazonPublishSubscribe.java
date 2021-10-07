/**
 * This program is a test application for an implementation of a message broker framework that will support
 * publish/subscribe functionality. There are the three following brokers in the framework: synchronous ordered
 * (SynchronousOrderedDispatchBroker), asynchronous unordered (AsyncUnorderedDispatchBroker) and asyncrhonous ordered
 * (AsynOrderedDispatchBroker).
 * The details upon each broker is specified in the respective class file.
 * This test application will deliver two Amazon review files using two publishers and four subscribers. There will be
 * two subscribers only receiving reviews older than a specified time and two subscribers receiving reviews newer than
 * the same specified time. The subscribers will output the items they receive into separate files.
 *
 * Author: Josh Li
 * Contact email: jxli2@dons.usfca.edu
 */

package project2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * AmazonPublishSubscribe is the main class for this test application. It contains the main method which will read the
 * config file, and apply the functionality of the message broker framework.
 */
public class AmazonPublishSubscribe {

    static Broker broker = null;

    /**
     * The main method of the test application. The broker, subscribers and publishers are initialized and shutdown
     * after they finish their tasks.
     * The configuration file must be correctly given in the arguments.
     * @param args
     */
    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        ReadConfigSettings readConfigSettings = new ReadConfigSettings(args);
        ConfigInputs configInputs = readConfigSettings.extractConfigSettings();
        AmazonSubscriber<String> amazonSubscriberOld1 = new AmazonSubscriber<>(configInputs.getOutputFileNameOld1(), "OLD");
        AmazonSubscriber<String> amazonSubscriberOld2 = new AmazonSubscriber<>(configInputs.getOutputFileNameOld2(), "OLD");
        AmazonSubscriber<String> amazonSubscriberNew1 = new AmazonSubscriber<>(configInputs.getOutputFileNameNew1(), "NEW");
        AmazonSubscriber<String> amazonSubscriberNew2 = new AmazonSubscriber<>(configInputs.getOutputFileNameNew2(), "NEW");
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

        broker.subscribe(amazonSubscriberOld1);
        broker.subscribe(amazonSubscriberOld2);
        broker.subscribe(amazonSubscriberNew1);
        broker.subscribe(amazonSubscriberNew2);

        // A thread is created for publisher1.
        Thread publisherThread1 = new Thread() {
            public void run() {
                Publisher publisher1 = null;
                try {
                    publisher1 = new Publisher("reviews_Home_and_Kitchen_5.json");
                } catch (UnsupportedEncodingException exc) {
                    System.out.println("Problem with accessing reviews file.");
                    System.exit(1);
                } catch (FileNotFoundException exc) {
                    System.out.println("Problem with accessing reviews file.");
                    System.exit(1);
                }
                try {
                    publisher1.callPublish(broker);
                } catch (IOException e) {
                    System.out.println("Problem with accessing broker.");
                    System.exit(1);
                }
            }
        };

        // A thread is created for publisher1.
        Thread publisherThread2 = new Thread() {
            public void run() {
                Publisher publisher2 = null;
                try {
                    publisher2 = new Publisher("reviews_Apps_for_Android_5.json");
                } catch (UnsupportedEncodingException exc) {
                    System.out.println("Problem with accessing reviews file.");
                    System.exit(1);
                } catch (FileNotFoundException exc) {
                    System.out.println("Problem with accessing reviews file.");
                    System.exit(1);
                }
                try {
                    publisher2.callPublish(broker);
                } catch (IOException e) {
                    System.out.println("Problem with accessing broker.");
                    System.exit(1);
                }
            }
        };

        publisherThread1.start();
        publisherThread2.start();

        try {
            publisherThread1.join(10000000);
        } catch (InterruptedException e) {
            System.out.println("Problem with running publisher.");
            System.exit(1);
        }

        try {
            publisherThread2.join(10000000);
        } catch (InterruptedException e) {
            System.out.println("Problem with running publisher.");
            System.exit(1);
        }

        broker.shutdown();
        amazonSubscriberOld1.shutdown();
        amazonSubscriberOld2.shutdown();
        amazonSubscriberNew1.shutdown();
        amazonSubscriberNew2.shutdown();

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds.");
    }
}