package Server;

import java.io.Serializable;

/**
 * Response class contains all information the server can send to client in one class
 */
public class Response implements Serializable {

    private StringBuilder message;

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
