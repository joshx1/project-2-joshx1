package project2;

public class ConfigInputs {
    private String brokerType;
    private String inputFileName1;
    private String inputFileName2;
    private String outputFileNameNew1;
    private String outputFileNameNew2;
    private String outputFileNameOld1;
    private String outputFileNameOld2;

    public ConfigInputs(String brokerType, String inputFileName1, String inputFileName2, String outputFileNameNew1, String outputFileNameNew2, String outputFileNameOld1, String outputFileNameOld2) {
        this.brokerType = brokerType;
        this.inputFileName1 = inputFileName1;
        this.inputFileName2 = inputFileName2;
        this.outputFileNameNew1 = outputFileNameNew1;
        this.outputFileNameNew2 = outputFileNameNew2;
        this.outputFileNameOld1 = outputFileNameOld1;
        this.outputFileNameOld2 = outputFileNameOld2;
    }

    public String getBrokerType() {
        return brokerType;
    }

    public void setBrokerType(String brokerType) {
        this.brokerType = brokerType;
    }

    public String getInputFileName1() {
        return inputFileName1;
    }

    public void setInputFileName1(String inputFileName1) {
        this.inputFileName1 = inputFileName1;
    }

    public String getInputFileName2() {
        return inputFileName2;
    }

    public void setInputFileName2(String inputFileName2) {
        this.inputFileName2 = inputFileName2;
    }

    public String getOutputFileNameNew1() {
        return outputFileNameNew1;
    }

    public void setOutputFileNameNew1(String outputFileNameNew1) {
        this.outputFileNameNew1 = outputFileNameNew1;
    }

    public String getOutputFileNameNew2() {
        return outputFileNameNew2;
    }

    public void setOutputFileNameNew2(String outputFileNameNew2) {
        this.outputFileNameNew2 = outputFileNameNew2;
    }

    public String getOutputFileNameOld1() {
        return outputFileNameOld1;
    }

    public void setOutputFileNameOld1(String outputFileNameOld1) {
        this.outputFileNameOld1 = outputFileNameOld1;
    }

    public String getOutputFileNameOld2() {
        return outputFileNameOld2;
    }

    public void setOutputFileNameOld2(String outputFileNameOld2) {
        this.outputFileNameOld2 = outputFileNameOld2;
    }

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