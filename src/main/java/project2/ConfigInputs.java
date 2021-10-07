package project2;

/**
 * ConfigInputs is a class that sets up the data structure to store the config settings JSON data.
 */
public class ConfigInputs {
    private String brokerType;
    private String inputFileName1;
    private String inputFileName2;
    private String outputFileNameNew1;
    private String outputFileNameNew2;
    private String outputFileNameOld1;
    private String outputFileNameOld2;

    /**
     * Constructor for ConfigInputs with all the fields in the reviews JSON file.
     * @param brokerType
     * @param inputFileName1
     * @param inputFileName2
     * @param outputFileNameNew1
     * @param outputFileNameNew2
     * @param outputFileNameOld1
     * @param outputFileNameOld2
     */
    public ConfigInputs(String brokerType, String inputFileName1, String inputFileName2, String outputFileNameNew1, String outputFileNameNew2, String outputFileNameOld1, String outputFileNameOld2) {
        this.brokerType = brokerType;
        this.inputFileName1 = inputFileName1;
        this.inputFileName2 = inputFileName2;
        this.outputFileNameNew1 = outputFileNameNew1;
        this.outputFileNameNew2 = outputFileNameNew2;
        this.outputFileNameOld1 = outputFileNameOld1;
        this.outputFileNameOld2 = outputFileNameOld2;
    }

    /**
     * Getter method for brokerType.
     * @return
     */
    public String getBrokerType() {
        return brokerType;
    }

    /**
     * Setter method for brokerType.
     * @param brokerType
     */
    public void setBrokerType(String brokerType) {
        this.brokerType = brokerType;
    }

    /**
     * Getter method for inputFileName1.
     * @return
     */
    public String getInputFileName1() {
        return inputFileName1;
    }

    /**
     * Setter method for inputFileName1.
     * @param inputFileName1
     */
    public void setInputFileName1(String inputFileName1) {
        this.inputFileName1 = inputFileName1;
    }

    /**
     * Getter method for inputFileName2.
     * @return
     */
    public String getInputFileName2() {
        return inputFileName2;
    }


    /**
     * Setter method for inputFileName2.
     * @param inputFileName2
     */
    public void setInputFileName2(String inputFileName2) {
        this.inputFileName2 = inputFileName2;
    }

    /**
     * Getter method for outputFileName1.
     * @return
     */
    public String getOutputFileNameNew1() {
        return outputFileNameNew1;
    }

    /**
     * Setter method for outputFileName1.
     * @param outputFileNameNew1
     */
    public void setOutputFileNameNew1(String outputFileNameNew1) {
        this.outputFileNameNew1 = outputFileNameNew1;
    }

    /**
     * Getter method for outputFileNameNew2.
     * @return
     */
    public String getOutputFileNameNew2() {
        return outputFileNameNew2;
    }

    /**
     * Setter method for outputFileName2.
     * @param outputFileNameNew2
     */
    public void setOutputFileNameNew2(String outputFileNameNew2) {
        this.outputFileNameNew2 = outputFileNameNew2;
    }

    /**
     * Getter method for outputFileNameOld1.
     * @return
     */
    public String getOutputFileNameOld1() {
        return outputFileNameOld1;
    }

    /**
     * Setter method for outputFileNameOld1.
     * @param outputFileNameOld1
     */
    public void setOutputFileNameOld1(String outputFileNameOld1) {
        this.outputFileNameOld1 = outputFileNameOld1;
    }

    /**
     * Getter method for outputFileNameOld2.
     * @return
     */
    public String getOutputFileNameOld2() {
        return outputFileNameOld2;
    }


    /**
     * Setter method for outputFileNameOld2.
     * @param outputFileNameOld2
     */
    public void setOutputFileNameOld2(String outputFileNameOld2) {
        this.outputFileNameOld2 = outputFileNameOld2;
    }

    /**
     * To string method for all data fields in the config JSON file.
     * @return
     */
    @Override
    public String toString() {
        return "ConfigInputs{" +
            "brokerType='" + brokerType + '\'' +
            ", inputFileName1='" + inputFileName1 + '\'' +
            ", inputFileName2='" + inputFileName2 + '\'' +
            ", outputFileNameNew1='" + outputFileNameNew1 + '\'' +
            ", outputFileNameNew2='" + outputFileNameNew2 + '\'' +
            ", outputFileNameOld1='" + outputFileNameOld1 + '\'' +
            ", outputFileNameOld2='" + outputFileNameOld2 + '\'' +
            '}';
    }
}