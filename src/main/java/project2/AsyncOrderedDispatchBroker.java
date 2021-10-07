package project2;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The AsynchronousOrderedDispatchBroker class implements the Broker interface and has the following properties:
 *     Asynchronous - A newly published item will be asynchronously delivered to all subscribers. The publish method
 *     will return to the publisher immediately, and the item will be delivered to the subscribers after the publish
 *     method completes.
 *     Ordered - The Broker guarantees that items from different publishers may not interleave.
 *     If a publisher is delivering to subscribers the next publisher must block until the first
 *     has finished.
 *     Description taken from: https://github.com/CS601-F21/Project2
 */
public class AsyncOrderedDispatchBroker <T> implements Broker <T> {

    private ArrayList<Subscriber> subscriberList;
    private boolean running = true;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    CS601BlockingQueue <T> blockingQueue = new CS601BlockingQueue(30);
    ExecutorService mailer = Executors.newSingleThreadExecutor();

    /**
     * Constructor for AsyncOrderedDispatchBroker class.
     * @param subscriberList
     */
    public AsyncOrderedDispatchBroker(ArrayList<Subscriber> subscriberList) {
        this.subscriberList = subscriberList;
        CS601BlockingQueue<T> blockingQueue = this.blockingQueue;
        mailer.execute(new Runnable() {
            @Override
            public void run() {
                while (running == true || blockingQueue.isEmpty() == false) {
                    T item = blockingQueue.poll();
                    if (item != null) {
                        subscriberList.forEach((subscriber) -> subscriber.onEvent(item));
                    }
                }
            }
        });
    }

    /**
     * The publish method publishes an item and delivers the item to all subscribers.
     * @param item
     */
    @Override
    public void publish(T item) {
        running = true;
        readLock.lock();
        try {
            blockingQueue.put(item);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * The subscribe method adds a given subscriber to the subscriber list.
     * @param subscriber
     */
    @Override
    public synchronized void subscribe(Subscriber<T> subscriber) {
        if (running) {
            writeLock.lock();
            try {
                subscriberList.add(subscriber);
            } finally {
                writeLock.unlock();
            }
        }
    }

    /**
     * The shutdown method shuts this broker down. The mailer thread is a maximum of 10 hours to finish its remaining
     * jobs.
     */
    @Override
    public void shutdown() {
        mailer.shutdown();
        running = false;
        try {
            mailer.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}