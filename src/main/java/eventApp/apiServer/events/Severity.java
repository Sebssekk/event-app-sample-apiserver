package eventApp.apiServer.events;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Severity {
    INFO("INFO"), WARNING("WARNING"), ALARM("ALARM"), SUCCESS("SUCCESS");

    private final String value;

    Severity(String value){
        this.value=value;
    }
    public String getValue(){
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static Severity getSeverityFromValue(String value) {

        for (Severity severity : Severity.values()) {

            if (severity.getValue().equals(value)) {

                return severity;
            }
        }

        throw new IllegalArgumentException();
    }
}
