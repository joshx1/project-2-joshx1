package project2;
import com.google.gson.Gson;

import java.io.*;

/**
 * The Publisher class calls the broker publish method to publish items.
 */

public class Publisher <T> {
    private java.io.FileInputStream fileInput;
    private java.io.InputStreamReader inputStream;

    // This is the constructor to create AmazonFileReader objects.
    public Publisher(String inputFileName) throws UnsupportedEncodingException, FileNotFoundException {
        fileInput = new java.io.FileInputStream(inputFileName);
        inputStream = new java.io.InputStreamReader(fileInput, "ISO-8859-1");
    }

    public void callPublish(Broker broker) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(inputStream)) {
            Gson gson = new Gson();
            String line = "";
            int lineNumber = 0;
            while (line != null) {
                lineNumber++;
                line = bufferedReader.readLine();
                if (line != null) {
                    broker.publish(line);
                    int flag = 0;
                }
            }
        }
    }
}
