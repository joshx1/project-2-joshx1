package main.java.project2;

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


    public synchronized T take() {
        while(size == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        T item = items[start];
        start = (start+1)%items.length;
        size--;
        /*
        If the queue was previously full and a new slot has now opened
        notify any waiters in the put method.
         */
        if(size == items.length-1) {
            this.notifyAll();
        }

        return item;
    }

    public synchronized T poll() {
        boolean flag = false;
        if (size == 0) {
            //System.out.println("returned null");
            try {
                this.wait(10);
                    if (size != 0) {
                        flag = true;
                    }
            } catch (InterruptedException e) {
                e.printStackTrace();
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

    public static void main(String[] args) {
    }
}
