package eventApp.apiServer.events.errors;

public class EventNotFoundException extends  Exception{
    public EventNotFoundException (){
        super("Event Not Found");
    }
    public EventNotFoundException (String errorMessage){
        super(errorMessage);
    }

}
