package project2;

import com.google.gson.Gson;
import java.io.*;

/**
 * The AmazonSubscriber class implements the Subscriber interface for the Amazon reviews test application. It contains
 * all functionality of the subscribers which includes subscribing, receiving publications and shutting down.
 * @param <T>
 */
public class AmazonSubscriber<T> implements Subscriber <T>{

    private int UNIX_REVIEW_TIME_SPLIT = 1362268800;
    Gson gson = new Gson();
    BufferedWriter bufferedWriter;
    private String outputFileName;
    String oldOrNew;

    /**
     * The constructor for the AmazonSubscriber class.
     * @param outputFileName
     * @param oldOrNew
     */
    public AmazonSubscriber(String outputFileName, String oldOrNew) {
        this.outputFileName =  outputFileName;
        this.oldOrNew = oldOrNew;
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

    /**
     * The onEvent method is called when an item is published. Depeinding upon the subscriber type and the unix review
     * time of the published review, this method will or will not write the item to the subscribers' respective output
     * file.
     * @param item
     */
    @Override
    public void onEvent(T item) {
        ReviewInputs reviewInputs = gson.fromJson(String.valueOf(item), ReviewInputs.class);
        int time = Integer.parseInt(reviewInputs.getUnixReviewTime());
        if (oldOrNew.equals("NEW")) {
            if (time >= UNIX_REVIEW_TIME_SPLIT) {
                try {
                    bufferedWriter.write(String.valueOf(item));
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (oldOrNew.equals("OLD")) {
            if (time < UNIX_REVIEW_TIME_SPLIT) {
                try {
                    bufferedWriter.write(String.valueOf(item));
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This shutdown method closes the BufferedWriter.
     */
    public synchronized void shutdown() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}