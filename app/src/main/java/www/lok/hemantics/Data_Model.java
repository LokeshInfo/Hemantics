package www.lok.hemantics;

public class Data_Model {

    String variable;
    String value;

    public Data_Model(String variable, String value) {
        this.variable = variable;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }
}
