package Server;

/**
 * Global class, which build one response to client (especially for not sending one Response through all methods)
 */
public class ResponseBuilder {
    private static Response response;

    private ResponseBuilder() {}

    static void createNewResponse() {
        response = new Response();
    }

    static void createNewResponse(String line) {
        createNewResponse(); add(line);
    }

    public static void add(String line) {
        response.addMessage(line);
    }

    static Response getResponse() {
        return response;
    }
}
