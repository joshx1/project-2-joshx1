package project2;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * The class ReadConfigSettings
 */
public class ReadConfigSettings {

    private java.io.FileInputStream fileInput;
    private java.io.InputStreamReader inputStream;
    private String[] args;

    public ReadConfigSettings(String[] args) {
        this.args = args;
    }

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
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String configSettingsLine = null;
        ConfigInputs configInputs = null;
        List<String> settings = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(inputStream)) {
            configSettingsLine = bufferedReader.readLine();
            Gson gson = new Gson();
            configInputs = gson.fromJson(String.valueOf(configSettingsLine), ConfigInputs.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return configInputs;
    }
}