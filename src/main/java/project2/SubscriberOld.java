package main.java.project2;

import java.util.ArrayList;

public class SubscriberOld <T> implements Subscriber <T>{
    private ArrayList<T> inbox;
    public SubscriberOld() {
        this.inbox = new ArrayList<T>();
    }

    @Override
    public void onEvent(T item) {
        inbox.add(item);
    }
    public synchronized ArrayList getInbox() {
        return inbox;
    }
    public synchronized void printInbox() {
        System.out.println(inbox.toString());
        System.out.println(inbox.size());
    }
}
