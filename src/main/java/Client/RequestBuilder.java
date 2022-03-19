package Client;

import Commands.Command;

public class RequestBuilder {

    private static Request request;

    private RequestBuilder() {}

    static void add(Command command) {
        request.add(command);
    }

    static Request getRequest() {
        return request;
    }

}
