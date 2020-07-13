package www.lok.hemantics;

public class DB_data {

    String id;
    String variable;
    String value;


    public DB_data(String id, String variable, String value) {
        this.id = id;
        this.variable = variable;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
