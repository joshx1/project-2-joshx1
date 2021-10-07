package project2;

import com.google.gson.Gson;

import java.io.*;

/**
 * The class ReadConfigSettings is responsible for taking the file from the command line arguments and returning the
 * configuration settings stored there.
 */
public class ReadConfigSettings {

    private java.io.FileInputStream fileInput;
    private java.io.InputStreamReader inputStream;
    private String[] args;

    /**
     * The constructor method for ReadConfigSettings.
     * @param args
     */
    public ReadConfigSettings(String[] args) {
        this.args = args;
    }

    /**
     * The extractConfigSettings method reads the config file given by the command line and returns settings.
     * @return
     */
    public ConfigInputs extractConfigSettings() {
        String configFileName = null;
        if (args.length == 2) {
            if (args[0].equals("-config")) {
                configFileName = args[1];
            } else {
                System.out.println("Incorrect command line arguments.");
                System.exit(0);
            }
        } else {
            System.out.println("Incorrect command line arguments.");
            System.exit(0);
        }

        try {
            fileInput = new FileInputStream(configFileName);
            inputStream = new InputStreamReader(fileInput, "ISO-8859-1");
        } catch (FileNotFoundException e) {
            System.out.println("Problem with accessing the config file.");
            System.exit(1);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Problem with accessing the config file.");
            System.exit(1);
        }
        String configSettingsLine = null;
        ConfigInputs configInputs = null;
        try (BufferedReader bufferedReader = new BufferedReader(inputStream)) {
            configSettingsLine = bufferedReader.readLine();
            Gson gson = new Gson();
            configInputs = gson.fromJson(String.valueOf(configSettingsLine), ConfigInputs.class);

        } catch (IOException e) {
            System.out.println("Problem with the format of the config file.");
            System.exit(1);
        }
        return configInputs;
    }
}