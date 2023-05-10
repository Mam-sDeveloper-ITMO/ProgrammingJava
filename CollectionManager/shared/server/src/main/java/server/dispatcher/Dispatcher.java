package server.dispatcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import server.Request;
import server.Response;
import server.Server;
import server.dispatcher.exceptions.BadRequestStream;
import server.routing.Handler;
import server.routing.Router;
import server.routing.exceptions.IncorrectRequestData;
import server.routing.exceptions.UnhandledRequest;

/**
 * Dispatcher is a class that handles requests from clients
 * and dispatches them to the appropriate handlers.
 *
 * Used by {@link server.Server}
 *
 * It contains a set of routers, which are used to resolve
 * handlers for requests.
 */
public class Dispatcher {
    /**
     * Set of routers, which are used to resolve handlers for requests.
     */
    @Getter
    @Setter
    private Set<Router> routers = new HashSet<>();

    /**
     * Include router to the set of routers.
     *
     * @param router
     */
    public void includeRouter(Router router) {
        this.routers.add(router);
    }

    /**
     * Remove router from the set of routers.
     *
     * @param router
     */
    public void removeRouter(Router router) {
        this.routers.remove(router);
    }

    /**
     * Dispatch request from given stream data and return response as stream.
     * Used by {@code Server}
     *
     * @see Dispatcher.dispatch
     *
     * @param requestStream input stream with Request object
     * @return output stream with Response object
     */
    public ByteArrayOutputStream dispatchStreams(ByteArrayInputStream requestStream) {
        Request request;
        try {
            request = this.deserializeRequest(requestStream);
            Response response = dispatch(request);
            return serializeResponse(response);
        } catch (BadRequestStream e) {
            Response response = new Response(false, "Bad request stream", null);
            return this.serializeResponse(response);
        }
    }

    /**
     * Dispatch request to the appropriate handler.
     * If there is no appropriate handler, return response with
     * error message.
     *
     * @param request processed request
     * @return response object
     */
    public Response dispatch(Request request) {
        for (Router router : this.routers) {
            Handler handler;
            try {
                handler = router.resolveHandler(request.trigger);
            } catch (UnhandledRequest e) {
                continue; // exception used instead of null to avoid null checks
            }
            try {
                return handler.handle(request.data);
            } catch (IncorrectRequestData e) {
                return new Response(false, "Bad request data", null);
            }
        }
        return new Response(false, "Not handlers for such trigger", null);
    }

    /**
     * Deserialize request from stream.
     *
     * @param requestBuffer stream with request
     * @return deserialized request
     * @throws BadRequestStream if stream is not valid
     */
    private Request deserializeRequest(ByteArrayInputStream requestBuffer) throws BadRequestStream {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(requestBuffer);) {
            Request request = (Request) objectInputStream.readObject();
            return request;
        } catch (IOException | ClassNotFoundException e) {
            throw new BadRequestStream();
        }
    }

    /**
     * Serialize response to stream.
     *
     * @param response response to serialize
     * @return stream with serialized response
     */
    private ByteArrayOutputStream serializeResponse(Response response) {
        // Java Google Style must die
        try (ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(responseBuffer);) {
            objectOutputStream.writeObject(response);
            return responseBuffer;
        } catch (IOException e) {
            return null;
        }
    }
}
