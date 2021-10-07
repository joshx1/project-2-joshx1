package project2;

import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The SynchronousOrderedDispatchBroker class implements the Broker interface and has
 * the following properties:
 *     Synchronous - A newly published item will be synchronously delivered to all subscribers.
 *     The publish method will not return to the publisher until all subscribers have completed
 *     the onEvent method.
 *     Ordered - The Broker guarantees that items from different publishers may not interleave.
 *     If a publisher is delivering to subscribers the next publisher must block until the first
 *     has finished.
 *     Description taken from: https://github.com/CS601-F21/Project2
 *     Referenced https://examples.javacodegeeks.com/core-java/util/concurrent/locks-concurrent/readwritelock/java-readwritelock-example/
  */
public class SynchronousOrderedDispatchBroker <T> implements Broker <T> {
    private ArrayList<Subscriber> subscriberList;
    private boolean running = true;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    /**
     * Constructor for SynchronousOrderedDispatchBroker class.
     * @param subscriberList
     */
    public SynchronousOrderedDispatchBroker(ArrayList<Subscriber> subscriberList) {
        this.subscriberList = subscriberList;
    }

    /**
     * The publish method publishes an item and delivers the item to all subscribers.
     * @param item
     */
    @Override
    public synchronized void publish(T item) {
        if (running) {
            readLock.lock();
            try {
                for (int k = 0; k < subscriberList.size(); k++) {
                    Subscriber subscriber = subscriberList.get(k);
                    subscriber.onEvent(item);
                }
            } finally {
                readLock.unlock();
            }
        }
    }

    /**
     * The subscribe method adds a given subscriber to the subscriber list.
     * @param subscriber
     */
    @Override
    public void subscribe(Subscriber<T> subscriber) {
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
     * The shutdown method shuts this broker down.
     */
    @Override
    public void shutdown() {
        running = false;
    }
}