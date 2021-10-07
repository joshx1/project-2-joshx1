package project2;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
