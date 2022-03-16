package Server;

public class Request {

    private StringBuilder message;

    public void addMessage(String message) {
        this.message.append(message == null ? "" : "\n").append(message);
    }

    public String getMessage() {
        return message.toString();
    }

}
