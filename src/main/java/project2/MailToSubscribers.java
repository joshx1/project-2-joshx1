package project2;

import java.util.ArrayList;

public class MailToSubscribers<T> implements Runnable{

    private static ArrayList<Subscriber> subscriberList;
    CS601BlockingQueue <T> blockingQueue;

    public MailToSubscribers(ArrayList<Subscriber> subscriberList, CS601BlockingQueue<T> blockingQueue) {
        this.subscriberList = subscriberList;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {

        System.out.println("Ran!!!");
        this.notifyAll();
        //sendMail(item);
    }

    public <T> void sendMail(T item) {
        if (item == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        subscriberList.forEach((subscriber) -> subscriber.onEvent(item));
    }

}
