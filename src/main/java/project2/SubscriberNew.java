package project2;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class SubscriberNew <T> implements Subscriber <T>{

    private ArrayList<T> inbox;
    private int UNIX_REVIEW_TIME_SPLIT = 1362268800;
    Gson gson = new Gson();
    BufferedWriter bufferedWriter;
    private String outputFileName;

    public SubscriberNew(String outputFileName) {
        this.inbox = new ArrayList<T>();
        this.outputFileName =  outputFileName;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFileName);
            OutputStreamWriter outputStream = new java.io.OutputStreamWriter(fileOutputStream, "ISO-8859-1");
            bufferedWriter = new BufferedWriter(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEvent(T item) {
        //inbox.add(item);
        ReviewInputs reviewInputs = gson.fromJson(String.valueOf(item), ReviewInputs.class);
        int time = Integer.parseInt(reviewInputs.getUnixReviewTime());
        if (time >= UNIX_REVIEW_TIME_SPLIT) {
            try {
                bufferedWriter.write(String.valueOf(item));
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void shutdown() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized ArrayList getInbox() {
        return inbox;
    }
    public synchronized void printInbox() {
        System.out.println(inbox.toString());
        System.out.println(inbox.size());
    }
}