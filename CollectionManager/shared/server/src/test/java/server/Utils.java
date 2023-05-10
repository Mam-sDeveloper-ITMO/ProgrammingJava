package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import server.utils.exceptions.BadRequestStream;

public class Utils {
    public static Request deserializeRequest(ByteArrayInputStream requestBuffer) throws BadRequestStream {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(requestBuffer);) {
            Request request = (Request) objectInputStream.readObject();
            return request;
        } catch (IOException | ClassNotFoundException e) {
            throw new BadRequestStream();
        }
    }

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
