package project2;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AsyncUnorderedDispatchBroker <T> implements Broker <T>{

    private ArrayList<Subscriber> subscriberList;
    private boolean running = true;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private ExecutorService threadPool;

    /**
     * Constructor for AsyncUnorderedDispatchBroker.
     * @param subscriberList
     */
    public AsyncUnorderedDispatchBroker(ArrayList<Subscriber> subscriberList) {
        this.subscriberList = subscriberList;
        this.threadPool = Executors.newFixedThreadPool(5);
    }

    @Override
    public void publish(T item) {
        //threadPool.execute(new MailToSubscribers<>(subscriberList));
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                readLock.lock();
                try {
                //    subscriberList.forEach((subscriber) -> subscriber.onEvent(item));
                    for (int k = 0; k < subscriberList.size(); k++) {
                        Subscriber subscriber = subscriberList.get(k);
                        subscriber.onEvent(item);
                    }
                } finally {
                    readLock.unlock();
                }
           }
        });
    }

    @Override
    public void subscribe(Subscriber<T> subscriber) {
        writeLock.lock();
        try {
            subscriberList.add(subscriber);
            //System.out.println(subscriberList.toString());
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void shutdown() {
        //System.out.println("Shutting down!");
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("all threads finished");

    }
}
