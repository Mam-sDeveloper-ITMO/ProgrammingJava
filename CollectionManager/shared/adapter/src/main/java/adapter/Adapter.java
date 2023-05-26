package adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;

import adapter.exceptions.ReceiveResponseFailed;
import adapter.exceptions.SendRequestFailed;
import adapter.exceptions.SocketInitFailed;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import server.requests.Request;
import server.responses.Response;
import server.utils.Serializer;
import server.utils.exceptions.BadResponseStream;

/**
 * Adapter provides communication with server
 * and utils for sending triggers requests to server
 *
 * Adapter use DatagramSocket for sending and receiving data.
 */
@RequiredArgsConstructor
public class Adapter {
    /**
     * Socket for sending and receiving data.
     */
    private final String ip;

    /**
     * Port on which server is running.
     */
    private final int port;

    /**
     * Timeout
     */
    private Integer timeout = 4000;

    /**
     * Buffer size
     */
    private Integer bufferSize = 1024 * 10;

    /**
     * Send trigger request to server and receive response.
     *
     * @param trigger  trigger name
     * @param data     trigger data
     * @param attempts number of attempts to send request
     * @return response from server
     * @throws SendRequestFailed     if sending request failed
     * @throws ReceiveResponseFailed if receiving response failed
     */
    public Response triggerServer(String trigger, Map<String, Serializable> data, Integer attempts)
            throws SocketInitFailed, SendRequestFailed, ReceiveResponseFailed {
        for (int i = 0; i < attempts; i++) {
            @Cleanup
            DatagramSocket socket = this.initSocket(ip, port);
            this.sendRequest(socket, new Request(trigger, data));
            try {
                return this.receiveResponse(socket);
            } catch (ReceiveResponseFailed e) {
                continue;
            }
        }
        throw new SendRequestFailed();
    }

    /**
     * Send trigger request to server and receive response
     * with default number of attempts (3).
     */
    public Response triggerServer(String trigger, Map<String, Serializable> data)
            throws SocketInitFailed, SendRequestFailed, ReceiveResponseFailed {
        return this.triggerServer(trigger, data, 3);
    }

    /**
     * Convert request to bytes and send it to server.
     *
     * @param request request to send
     * @throws SendRequestFailed if sending request failed
     */
    private void sendRequest(DatagramSocket socket, Request request) throws SendRequestFailed {
        byte[] requestBytes = Serializer.serializeRequest(request).toByteArray();

        try {
            socket.send(new DatagramPacket(requestBytes, requestBytes.length));
        } catch (IOException e) {
            throw new SendRequestFailed();
        }
    }

    /**
     * Receive response from server and convert it to Response object.
     *
     * @return response from server
     * @throws ReceiveResponseFailed if receiving response failed
     */
    private Response receiveResponse(DatagramSocket socket) throws ReceiveResponseFailed {
        byte[] buffer = new byte[bufferSize];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while (true) {
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new ReceiveResponseFailed();
            }
            outputStream.write(packet.getData(), 0, packet.getLength());
            if (packet.getLength() < buffer.length) {
                break;
            }
        }

        byte[] receivedData = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(receivedData);

        try {
            return Serializer.deserializeResponse(inputStream);
        } catch (BadResponseStream e) {
            throw new ReceiveResponseFailed();
        }
    }

    /**
     * Initialize socket on specified ip and port.
     *
     * @param ip   ip on which server is running
     * @param port port on which server is running
     * @return initialized socket
     * @throws SocketInitFailed if socket initialization failed
     */
    private DatagramSocket initSocket(String ip, int port) throws SocketInitFailed {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            socket.connect(new java.net.InetSocketAddress(ip, port));
            socket.setSoTimeout(timeout);
        } catch (Exception e) {
            throw new SocketInitFailed();
        }
        return socket;
    }
}
