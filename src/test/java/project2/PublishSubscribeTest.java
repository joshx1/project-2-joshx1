package project2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class PublishSubscribeTest {
    String outputFileName1 = "KitchenOld1.json";
    String outputFileName2 = "KitchenOld2.json";
    String outputFileName3 = "KitchenOld3.json";
    SubscriberOld<String> subscriber1 = new SubscriberOld<>(outputFileName1);
    SubscriberOld<String> subscriber2 = new SubscriberOld<>(outputFileName2);
    SubscriberOld<String> subscriber3 = new SubscriberOld<>(outputFileName3);
    SynchronousOrderedDispatchBroker syncBroker = new SynchronousOrderedDispatchBroker(new ArrayList<>());
    AsyncUnorderedDispatchBroker asyncUnordBroker = new AsyncUnorderedDispatchBroker(new ArrayList<>());
    AsyncOrderedDispatchBroker asyncOrdBroker = new AsyncOrderedDispatchBroker(new ArrayList<>());

    @Test
    public void maintest() {
        String[] args = {"-config", "config.json"};
        final long startTime = System.currentTimeMillis();
        PublishSubscribe publishSubscribe = null;
        publishSubscribe.main(args);
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }

    @Test
    public void asynctestpublisherthreads() {
        final long startTime = System.currentTimeMillis();
        asyncOrdBroker.subscribe(subscriber1);
        asyncOrdBroker.subscribe(subscriber2);
        asyncOrdBroker.subscribe(subscriber3);
        Thread publisherThread1 = new Thread() {
            public void run() {
                Publisher publisher1 = null;
                try {
                    publisher1 = new Publisher("reviews_Home_and_Kitchen_5.json");
                } catch (UnsupportedEncodingException exc) {
                    exc.printStackTrace();
                } catch (FileNotFoundException exc) {
                    exc.printStackTrace();
                }
                try {
                    publisher1.callPublish(asyncOrdBroker);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread publisherThread2 = new Thread() {
            public void run() {
                Publisher publisher2 = null;
                try {
                    publisher2 = new Publisher("reviews_Apps_for_Android_5.json");
                } catch (UnsupportedEncodingException exc) {
                    exc.printStackTrace();
                } catch (FileNotFoundException exc) {
                    exc.printStackTrace();
                }
                try {
                    publisher2.callPublish(asyncOrdBroker);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        publisherThread1.start();
        publisherThread2.start();
        try {
            publisherThread1.join(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            publisherThread2.join(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        asyncOrdBroker.shutdown();
        subscriber1.shutdown();
        subscriber2.shutdown();
        subscriber3.shutdown();
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }

    //@RepeatedTest(100)
    @Test
    public void synctestpublisherthreads() {
        final long startTime = System.currentTimeMillis();
        syncBroker.subscribe(subscriber1);
        syncBroker.subscribe(subscriber2);
        syncBroker.subscribe(subscriber3);
        Thread publisherThread1 = new Thread() {
            public void run() {
                Publisher publisher1 = null;
                try {
                    publisher1 = new Publisher("reviews_Home_and_Kitchen_5.json");
                } catch (UnsupportedEncodingException exc) {
                    exc.printStackTrace();
                } catch (FileNotFoundException exc) {
                    exc.printStackTrace();
                }
                try {
                    publisher1.callPublish(syncBroker);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread publisherThread2 = new Thread(){
            public void run() {
                Publisher publisher2 = null;
                try {
                    publisher2 = new Publisher("reviews_Apps_for_Android_5.json");
                } catch (UnsupportedEncodingException exc) {
                    exc.printStackTrace();
                } catch (FileNotFoundException exc) {
                    exc.printStackTrace();
                }
                try {
                    publisher2.callPublish(syncBroker);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        publisherThread1.start();
        publisherThread2.start();
        try {
            publisherThread1.join(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            publisherThread2.join(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        syncBroker.shutdown();
        subscriber1.shutdown();
        subscriber2.shutdown();
        subscriber3.shutdown();
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
    }

    //@RepeatedTest(100)
    //public void asynctestpublisherthreads() {
    //    syncBroker.subscribe(subscriber1);
    //    syncBroker.subscribe(subscriber2);
    //    syncBroker.subscribe(subscriber3);
    //    Thread publisherThread1 = new Thread() {
    //        public void run() {
    //            Publisher publisher1 = null;
    //            try {
    //                publisher1 = new Publisher("reviews_Home_and_Kitchen_5.json");
    //            } catch (UnsupportedEncodingException exc) {
    //                exc.printStackTrace();
    //            } catch (FileNotFoundException exc) {
    //                exc.printStackTrace();
    //            }
    //            try {
    //                publisher1.callPublish(syncBroker);
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //        }
    //    };

    //    Thread publisherThread2 = new Thread() {
    //        public void run() {
    //            Publisher publisher2 = null;
    //            try {
    //                publisher2 = new Publisher("reviews_Home_and_Kitchen_5.json");
    //            } catch (UnsupportedEncodingException exc) {
    //                exc.printStackTrace();
    //            } catch (FileNotFoundException exc) {
    //                exc.printStackTrace();
    //            }
    //            try {
    //                publisher2.callPublish(syncBroker);
    //            } catch (IOException e) {
    //                e.printStackTrace();
    //            }
    //        }
    //    };
    //    publisherThread1.start();
    //    publisherThread2.start();
    //    try {
    //        publisherThread1.join(10000000);
    //    } catch (InterruptedException e) {
    //        e.printStackTrace();
    //    }

    //    try {
    //        publisherThread2.join(10000000);
    //    } catch (InterruptedException e) {
    //        e.printStackTrace();
    //    }
    //    syncBroker.shutdown();
    //    ArrayList inbox1 = subscriber1.getInbox();
    //    ArrayList inbox2 = subscriber2.getInbox();
    //    ArrayList inbox3 = subscriber3.getInbox();
    //    System.out.println(inbox1.get(0));
    //    System.out.println(inbox2.get(0));
    //    System.out.println(inbox1.size());
    //    System.out.println(inbox2.size());
    //    System.out.println(inbox3.size());
    //}

    //@RepeatedTest(100)
    //public void synctest() {
    //    syncBroker.subscribe(subscriber1);
    //    syncBroker.subscribe(subscriber2);
    //    syncBroker.subscribe(subscriber3);
    //    ExecutorService publisher1 = Executors.newSingleThreadExecutor();
    //    ExecutorService publisher2 = Executors.newSingleThreadExecutor();
    //    int testPubItemsLength = 10000;
    //    publisher1.execute(new Runnable() {
    //        @Override
    //        public void run() {
    //            for (int i = 0; i < testPubItemsLength; i++) {
    //                syncBroker.publish("0");
    //            }
    //        }
    //    });
    //    publisher2.execute(new Runnable() {
    //        @Override
    //        public void run() {
    //            for (int i = 0; i < testPubItemsLength; i++) {
    //                syncBroker.publish("1");
    //            }
    //        }
    //    });
    //    publisher1.shutdown();
    //    publisher2.shutdown();
    //    try {
    //        publisher1.awaitTermination(10, TimeUnit.HOURS);
    //        publisher2.awaitTermination(10, TimeUnit.HOURS);
    //    } catch (InterruptedException e) {
    //        e.printStackTrace();
    //    }
    //    syncBroker.shutdown();
    //    ArrayList inbox1 = subscriber1.getInbox();
    //    ArrayList inbox2 = subscriber2.getInbox();
    //    ArrayList inbox3 = subscriber3.getInbox();
    //    int zeroCount = 0;
    //    int oneCount = 0;
    //    for (int i = 0; i < testPubItemsLength; i++) {
    //        if (inbox1.get(i) == "0") {
    //            zeroCount++;
    //        } else if (inbox1.get(i) == "1") {
    //            oneCount++;
    //        }
    //    }
    //    assertTrue(inbox1.equals(inbox2));
    //    assertTrue(inbox2.equals(inbox3));
    //}

    //@RepeatedTest(1000)
    //public void asyncUnordTest() {
    //    asyncUnordBroker.subscribe(subscriber1);
    //    asyncUnordBroker.subscribe(subscriber2);
    //    asyncUnordBroker.subscribe(subscriber3);
    //    //ExecutorService publisher1 = Executors.newSingleThreadExecutor();
    //    //ExecutorService publisher2 = Executors.newSingleThreadExecutor();
    //    int testPubItemsLength = 10000;
    //    Thread publisher1 = new Thread(){
    //        public void run(){
    //            for (int i = 0; i < testPubItemsLength; i++) {
    //                //System.out.println("TO publish ONE item number:" + i);
    //                asyncUnordBroker.publish("1");
    //                //System.out.println("Published ONE item number:" + i);
    //            }
    //        }
    //    };

    //    Thread publisher2 = new Thread(){
    //        public void run(){
    //            for (int j = 0; j < testPubItemsLength; j++) {
    //                //System.out.println("Published TWO item number:" + j);
    //                asyncUnordBroker.publish("0");
    //                //System.out.println("Published TWO item number:" + j);
    //            }
    //        }
    //    };
    //    publisher1.start();
    //    publisher2.start();
    //    try {
    //        publisher1.join(10000000);
    //    } catch (InterruptedException e) {
    //        e.printStackTrace();
    //    }

    //    try {
    //        publisher2.join(10000000);
    //    } catch (InterruptedException e) {
    //        e.printStackTrace();
    //    }
    //    //publisher1.execute(new Runnable() {
    //    //    @Override
    //    //    public void run() {
    //    //        for (int i = 0; i < testPubItemsLength; i++) {
    //    //            asyncUnordBroker.publish("0");
    //    //        }
    //    //    }
    //    //});

    //    //publisher2.execute(new Runnable() {
    //    //    @Override
    //    //    public void run() {
    //    //        for (int i = 0; i < testPubItemsLength; i++) {
    //    //            asyncUnordBroker.publish("1");
    //    //        }
    //    //    }
    //    //});
    //    //publisher1.shutdown();
    //    //publisher2.shutdown();
    //    //try {
    //    //    publisher1.awaitTermination(10, TimeUnit.HOURS);
    //    //    publisher2.awaitTermination(10, TimeUnit.HOURS);
    //    //} catch (InterruptedException e) {
    //    //    e.printStackTrace();
    //    //}
    //    asyncUnordBroker.shutdown();
    //    ArrayList inbox1 = subscriber1.getInbox();
    //    ArrayList inbox2 = subscriber2.getInbox();
    //    ArrayList inbox3 = subscriber3.getInbox();
    //    int zeroCount1 = 0;
    //    int oneCount1 = 0;
    //    int zeroCount2 = 0;
    //    int oneCount2 = 0;
    //    int zeroCount3 = 0;
    //    int oneCount3 = 0;
    //    for (int t = 0; t < inbox1.size(); t++) {
    //        if (inbox1.get(t) == "0") {
    //            zeroCount1++;
    //        } else if (inbox1.get(t) == "1") {
    //            oneCount1++;
    //        }
    //        if (inbox2.get(t) == "0") {
    //            zeroCount2++;
    //        } else if (inbox2.get(t) == "1") {
    //            oneCount2++;
    //        }
    //        if (inbox3.get(t) == "0") {
    //            zeroCount3++;
    //        } else if (inbox3.get(t) == "1") {
    //            oneCount3++;
    //        }
    //    }
    //    assert(zeroCount1 == testPubItemsLength);
    //    assert(oneCount1 == testPubItemsLength);
    //    assert(zeroCount2 == testPubItemsLength);
    //    assert(oneCount2 == testPubItemsLength);
    //    assert(zeroCount3 == testPubItemsLength);
    //    assert(oneCount3 == testPubItemsLength);
    //}

    //@RepeatedTest(100)
    ////@Test
    //public void asyncordtest() {
    //    asyncOrdBroker.subscribe(subscriber1);
    //    asyncOrdBroker.subscribe(subscriber2);
    //    asyncOrdBroker.subscribe(subscriber3);
    //    //ExecutorService publisher1 = Executors.newSingleThreadExecutor();
    //    //ExecutorService publisher2 = Executors.newSingleThreadExecutor();
    //    int testPubItemsLength = 1000;
    //    Thread publisher1 = new Thread(){
    //        public void run(){
    //            for (int i = 0; i < testPubItemsLength; i++) {
    //                //System.out.println("TO publish ONE item number:" + i);
    //                asyncOrdBroker.publish("1");
    //                //System.out.println("Published ONE item number:" + i);
    //            }
    //        }
    //    };

    //    Thread publisher2 = new Thread(){
    //        public void run(){
    //            for (int j = 0; j < testPubItemsLength; j++) {
    //                //System.out.println("Published TWO item number:" + j);
    //                asyncOrdBroker.publish("0");
    //                //System.out.println("Published TWO item number:" + j);
    //            }
    //        }
    //    };
    //    publisher1.start();
    //    publisher2.start();
    //    try {
    //        publisher1.join(10000000);
    //    } catch (InterruptedException e) {
    //        e.printStackTrace();
    //    }

    //    try {
    //        publisher2.join(10000000);
    //    } catch (InterruptedException e) {
    //        e.printStackTrace();
    //    }
    //    //publisher1.execute(new Runnable() {
    //    //    @Override
    //    //    public void run() {
    //    //        for (int i = 0; i < testPubItemsLength; i++) {
    //    //            asyncUnordBroker.publish("0");
    //    //        }
    //    //    }
    //    //});

    //    //publisher2.execute(new Runnable() {
    //    //    @Override
    //    //    public void run() {
    //    //        for (int i = 0; i < testPubItemsLength; i++) {
    //    //            asyncUnordBroker.publish("1");
    //    //        }
    //    //    }
    //    //});
    //    //publisher1.shutdown();
    //    //publisher2.shutdown();
    //    //try {
    //    //    publisher1.awaitTermination(10, TimeUnit.HOURS);
    //    //    publisher2.awaitTermination(10, TimeUnit.HOURS);
    //    //} catch (InterruptedException e) {
    //    //    e.printStackTrace();
    //    //}
    //    asyncOrdBroker.shutdown();
    //    ArrayList inbox1 = subscriber1.getInbox();
    //    ArrayList inbox2 = subscriber2.getInbox();
    //    ArrayList inbox3 = subscriber3.getInbox();
    //    System.out.println(inbox1.toString());
    //    int zeroCount1 = 0;
    //    int oneCount1 = 0;
    //    int zeroCount2 = 0;
    //    int oneCount2 = 0;
    //    int zeroCount3 = 0;
    //    int oneCount3 = 0;
    //    for (int t = 0; t < inbox1.size(); t++) {
    //        if (inbox1.get(t) == "0") {
    //            zeroCount1++;
    //        } else if (inbox1.get(t) == "1") {
    //            oneCount1++;
    //        }
    //        if (inbox2.get(t) == "0") {
    //            zeroCount2++;
    //        } else if (inbox2.get(t) == "1") {
    //            oneCount2++;
    //        }
    //        if (inbox3.get(t) == "0") {
    //            zeroCount3++;
    //        } else if (inbox3.get(t) == "1") {
    //            oneCount3++;
    //        }
    //    }
    //    //System.out.println(zeroCount1);
    //    //System.out.println(oneCount1);
    //    assert(zeroCount1 == testPubItemsLength);
    //    assert(oneCount1 == testPubItemsLength);
    //    assert(zeroCount2 == testPubItemsLength);
    //    assert(oneCount2 == testPubItemsLength);
    //    assert(zeroCount3 == testPubItemsLength);
    //    assert(oneCount3 == testPubItemsLength);
    //    assertTrue(inbox1.equals(inbox2));
    //    assertTrue(inbox2.equals(inbox3));
    //}
}