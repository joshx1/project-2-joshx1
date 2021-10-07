package project2;

import java.io.*;

/**
 * The Publisher class calls the broker publish method to publish items.
 */
public class Publisher <T> {
    private java.io.FileInputStream fileInput;
    private java.io.InputStreamReader inputStream;

    /**
     * The constructor to create AmazonFileReader objects.
     * @param inputFileName
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    public Publisher(String inputFileName) throws UnsupportedEncodingException, FileNotFoundException {
        fileInput = new java.io.FileInputStream(inputFileName);
        inputStream = new java.io.InputStreamReader(fileInput, "ISO-8859-1");
    }

    /**
     * The callPublish method reads the file and uses the given broker to publish each line.
     * @param broker
     * @throws IOException
     */
    public void callPublish(Broker broker) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(inputStream)) {
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                if (line != null) {
                    broker.publish(line);
                }
            }
        }
    }
}