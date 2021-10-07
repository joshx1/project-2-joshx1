package project2;

/**
 * The CS601BlockingQueue class is a blocking queue with put, poll and check is empty functionality.
 * This is taken from the CS601 in class example at
 * https://github.com/CS601-F21/code-examples/blob/main/Threads/src/main/java/concurrent/CS601BlockingQueue.java.
 * @param <T>
 */
public class CS601BlockingQueue<T> {

    private T[] items;
    private int start;
    private int end;
    private int size;

    /**
     * Queue is bounded in size.
     * @param size
     */
    public CS601BlockingQueue(int size) {
        this.items = (T[]) new Object[size];
        this.start = 0;
        this.end = -1;
        this.size = 0;
    }

    /**
     * Queue will block until new item can be inserted.
     * @param item
     */
    public synchronized void put(T item) {
        while(size == items.length) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        int next = (end+1)%items.length;
        items[next] = item;
        end = next;
        size++;
        if(size == 1) {
            this.notifyAll();
        }
    }

    /**
     * The poll method takes an item off the top of the queue. If the queue is empty then the poll method will wait
     * for 1000 milliseconds and if the queue remains empty then it will return null.
     * @return
     */
    public synchronized T poll() {
        boolean flag = false;
        if (size == 0) {
            try {
                this.wait(1000);
                    if (size != 0) {
                        flag = true;
                    }
            } catch (InterruptedException e) {
                System.out.println("Issue with the broker blocking queue.");
                System.exit(1);
            }
        }
        if (size != 0 || flag == true) {
            T item = items[start];
            start = (start + 1) % items.length;
            size--;
            /*
            If the queue was previously full and a new slot has now opened
            notify any waiters in the put method.
             */
            if (size == items.length - 1) {
                this.notifyAll();
            }
            return item;
        } else {
            return null;
        }
    }

    public synchronized boolean isEmpty() {
        return size == 0;
    }
}
