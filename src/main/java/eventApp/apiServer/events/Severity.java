package eventApp.apiServer.events;

public enum Severity {
    INFO("INFO"), WARNING("WARNING"), ALARM("ALARM"), SUCCESS("SUCCESS");

    private String value;

    Severity(String value){
        this.value=value;
    }
    public String getValue(){
        return value;
    }
}
