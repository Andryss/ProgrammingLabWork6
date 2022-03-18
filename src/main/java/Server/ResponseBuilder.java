package Server;

public class ResponseBuilder {
    private static Response response;

    private ResponseBuilder() {}

    public static void createNewResponse() {
        response = new Response();
    }

    public static void add(String line) {
        response.addMessage(line);
    }

    public static Response getResponse() {
        return response;
    }
}
