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
        //Runnable runnable = new MailToSubscribers<>(subscriberList, blockingQueue);
        //Thread thread = new Thread(runnable, "Mailer");
        //thread.start();
        //mailer.execute();
        mailer.execute(new Runnable() {
            @Override
            public void run() {
                while (running == true) {
                    T item = blockingQueue.poll();
                    //System.out.println("polled");
                    if (item != null) {
                        subscriberList.forEach((subscriber) -> subscriber.onEvent(item));
                    }
                }
            }
        });

        //mailer.execute(new Runnable() {
        //    @Override
        //    public void run() {
        //        System.out.println("ran the poll");
        //        while (running == true) {
        //            while (blockingQueue.isEmpty() == false) {
        //                T item = blockingQueue.poll();
        //                //subscriberList.forEach((subscriber) -> subscriber.onEvent(item));
        //                if (item != null) {
        //                    for (int k = 0; k < subscriberList.size(); k++) {
        //                        //System.out.println(k);
        //                        Subscriber subscriber = subscriberList.get(k);
        //                        subscriber.onEvent(item);
        //                        //logger.info(String.valueOf(subscriber));
        //                    }
        //                }
        //            }
        //        }
        //    }
        //});
    }

    @Override
    public void publish(T item) {
        if (running == true) {
            readLock.lock();
            try {
                blockingQueue.put(item);
            } finally {
                readLock.unlock();
            }
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
        System.out.println("all threads finished");
    }
}
