package server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import org.junit.Test;

import server.requests.Request;
import server.responses.Response;
import server.utils.Serializer;
import server.utils.exceptions.BadRequestStream;
import server.utils.exceptions.BadResponseStream;

public class StructuresTest {
    @Test
    public void testResponseCreation() {
        Response response = Response.success("test", new HashMap<>(), 200);
        assertTrue(response.getOk());

        response = Response.success(200);
        assertTrue(response.getOk());
        assertEquals("", response.getMessage());

        response = Response.failure("test", 200);
        assertFalse(response.getOk());

        response = new Response(true, "test", new HashMap<>(), 200);

        Response response2 = new Response(true, "test", new HashMap<>(), 200);
        assertEquals(response, response2);
    }

    @Test
    public void testResponseSerialization() {
        Response response = Response.success("test", new HashMap<>(), 200);
        ByteArrayOutputStream outputStream = Serializer.serializeResponse(response);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        try {
            Response desResponse = Serializer.deserializeResponse(inputStream);
            assertTrue(desResponse.getOk());
            assertEquals(desResponse.getMessage(), "test");
            assertEquals(desResponse.getData(), new HashMap<>());
        } catch (BadResponseStream e) {
            assert false;
        }

        response = Response.failure("test2", 200);
        outputStream = Serializer.serializeResponse(response);
        inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        try {
            Response desResponse = Serializer.deserializeResponse(inputStream);
            assertFalse(desResponse.getOk());
            assertEquals(desResponse.getMessage(), "test2");
            assertEquals(desResponse.getData(), new HashMap<>());
        } catch (BadResponseStream e) {
            assert false;
        }

        response = Response.success("test3", new HashMap<>(), 200);
        outputStream = Serializer.serializeResponse(response);
        inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        try {
            Response desResponse = Serializer.deserializeResponse(inputStream);
            assertTrue(desResponse.getOk());
            assertEquals(desResponse.getMessage(), "test3");
            assertEquals(desResponse.getData(), new HashMap<>());
        } catch (BadResponseStream e) {
            assert false;
        }
    }

    @Test
    public void testRequestCreation() {
        Request request = new Request("test", new HashMap<>());
        assertEquals(request.getTrigger(), "test");
        assertEquals(request.getData(), new HashMap<>());

        Request request2 = new Request("test", new HashMap<>());
        assertEquals(request, request2);
    }

    @Test
    public void testRequestSerialization() {
        Request request = new Request("test", new HashMap<>());
        ByteArrayOutputStream outputStream = Serializer.serializeRequest(request);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        try {
            Request desRequest = Serializer.deserializeRequest(inputStream);
            assertEquals(desRequest.getTrigger(), "test");
            assertEquals(desRequest.getData(), new HashMap<>());
        } catch (BadRequestStream e) {
            assert false;
        }

        request = new Request("test2", new HashMap<>());
        outputStream = Serializer.serializeRequest(request);
        inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        try {
            Request desRequest = Serializer.deserializeRequest(inputStream);
            assertEquals(desRequest.getTrigger(), "test2");
            assertEquals(desRequest.getData(), new HashMap<>());
        } catch (BadRequestStream e) {
            assert false;
        }
    }
}
