package project2;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The AsynchronousUnorderedDispatchBroker class implements the Broker interface and has the following properties:
 *     Asynchronous - A newly published item will be asynchronously delivered to all subscribers. The publish method
 *     will return to the publisher immediately, and the item will be delivered to the subscribers after the publish
 *     method completes.
 *     Unordered - The Broker makes no guarantees about the order in which items are delivered to the subscribers.
 *     Description taken from: https://github.com/CS601-F21/Project2
 */
public class AsyncUnorderedDispatchBroker <T> implements Broker <T>{

    private ArrayList<Subscriber> subscriberList;
    private boolean running = true;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private ExecutorService threadPool;

    /**
     * Constructor for AsyncUnorderedDispatchBroker. A threadpool with 5 threads is created.
     * @param subscriberList
     */
    public AsyncUnorderedDispatchBroker(ArrayList<Subscriber> subscriberList) {
        this.subscriberList = subscriberList;
        this.threadPool = Executors.newFixedThreadPool(5);
    }

    /**
     * The publish method publishes an item and delivers the item to all subscribers.
     * @param item
     */
    @Override
    public void publish(T item) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                readLock.lock();
                try {
                    for (int k = 0; k < subscriberList.size(); k++) {
                        Subscriber subscriber = subscriberList.get(k);
                        synchronized (subscriber) {
                            subscriber.onEvent(item);
                        }
                    }
                } finally {
                    readLock.unlock();
                }
            }
        });
    }

    /**
     * The subscribe method adds a given subscriber to the subscriber list.
     * @param subscriber
     */
    @Override
    public void subscribe(Subscriber<T> subscriber) {
        writeLock.lock();
        try {
            subscriberList.add(subscriber);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * The shutdown method shuts this broker down. The threadpool is shutdown and will give the remaining jobs a maximum
     * of 10 hours to complete.
     */
    @Override
    public void shutdown() {
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            System.out.println("Problem with the broker shutting down.");
            System.exit(1);
        }
    }
}