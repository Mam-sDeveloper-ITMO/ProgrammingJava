package adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;

import adapter.exceptions.ReceiveResponseFailed;
import adapter.exceptions.SendRequestFailed;
import adapter.exceptions.SocketInitFailed;
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
public class Adapter {
    /**
     * Socket used for sending and receiving data.
     */
    private DatagramSocket socket;

    /**
     * Create adapter on specified ip and port.
     *
     * @param ip   ip on which server is running
     * @param port port on which server is running
     * @throws SocketInitFailed if socket initialization failed
     */
    public Adapter(String ip, int port) throws SocketInitFailed {
        this.socket = initSocket(ip, port);
    }

    /**
     * Send trigger request to server and receive response.
     *
     * @param trigger trigger name
     * @param data    trigger data
     * @return response from server
     * @throws SendRequestFailed     if sending request failed
     * @throws ReceiveResponseFailed if receiving response failed
     */
    public Response triggerServer(String trigger, Map<String, ?> data) throws SendRequestFailed, ReceiveResponseFailed {
        this.sendRequest(new Request(trigger, data));
        return this.receiveResponse();
    }

    /**
     * Convert request to bytes and send it to server.
     *
     * @param request request to send
     * @throws SendRequestFailed if sending request failed
     */
    private void sendRequest(Request request) throws SendRequestFailed {
        byte[] requestBytes = Serializer.serializeRequest(request).toByteArray();

        try {
            this.socket.send(new DatagramPacket(requestBytes, requestBytes.length));
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
    private Response receiveResponse() throws ReceiveResponseFailed {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while (true) {
            try {
                this.socket.receive(packet);
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
        } catch (Exception e) {
            throw new SocketInitFailed();
        }
        return socket;
    }
}
