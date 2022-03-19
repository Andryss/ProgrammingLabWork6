package Server;

public class Response {

    private StringBuilder message = new StringBuilder();

    void addMessage(String message) {
        if (this.message == null) {
            this.message = (new StringBuilder()).append(message);
        } else {
            this.message.append("\n").append(message);
        }
    }

    public String getMessage() {
        return message.toString();
    }

}
