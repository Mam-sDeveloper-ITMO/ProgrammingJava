package server.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import server.requests.Request;
import server.responses.Response;
import server.utils.exceptions.BadRequestStream;
import server.utils.exceptions.BadResponseStream;

/**
 * Serializer provides static methods to serialize and deserialize
 * {@code Request} and {@Response} objects to byte streams.
 */
public class Serializer {
    /**
     * Deserialize request from stream.
     *
     * @param requestBuffer stream with request
     * @return deserialized request
     * @throws BadRequestStream if stream is not valid
     */
    public static Request deserializeRequest(ByteArrayInputStream requestBuffer) throws BadRequestStream {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(requestBuffer);) {
            Request request = (Request) objectInputStream.readObject();
            return request;
        } catch (IOException | ClassNotFoundException e) {
            throw new BadRequestStream();
        }
    }

    /**
     * Serialize request to stream.
     *
     * @param request request to serialize
     * @return stream with serialized request
     */
    public static ByteArrayOutputStream serializeRequest(Request request) {
        // Java Google Style must die
        try (ByteArrayOutputStream requestBuffer = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(requestBuffer);) {
            objectOutputStream.writeObject(request);
            return requestBuffer;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Deserialize response from stream.
     *
     * @param responseBuffer stream with response
     * @return deserialized response
     * @throws BadRequestStream if stream is not valid
     */
    public static Response deserializeResponse(ByteArrayInputStream responseBuffer) throws BadResponseStream {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(responseBuffer);) {
            Response response = (Response) objectInputStream.readObject();
            return response;
        } catch (IOException | ClassNotFoundException e) {
            throw new BadResponseStream();
        }
    }

    /**
     * Serialize response to stream.
     *
     * @param response response to serialize
     * @return stream with serialized response
     */
    public static ByteArrayOutputStream serializeResponse(Response response) {
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
