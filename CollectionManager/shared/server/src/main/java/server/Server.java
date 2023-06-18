package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import server.dispatcher.Dispatcher;
import server.exceptions.ChannelInitFailed;
import server.exceptions.ServerRunningFailed;
import server.requests.Request;
import server.responses.Response;
import server.utils.Serializer;
import server.utils.StatusCodes;
import server.utils.exceptions.BadRequestStream;

/**
 * Non-blocking UDP server.
 *
 * Use Dispatcher to process requests (@see Handler).
 */
@RequiredArgsConstructor
public class Server {
    /**
     * Dispatcher is used to process requests.
     */
    private Dispatcher dispatcher;

    /**
     * Channel on which server is running.
     */
    private DatagramSocket socket;

    /**
     * Pool with suppressed exceptions.
     */
    @Getter
    private final Queue<Exception> exceptionsPull = new LinkedList<>();

    /**
     * Size of thread pool.
     */
    @Getter
    @Setter
    private Integer threadPoolSize = 10;

    /**
     * Size of buffer for reading and writing.
     */
    private static final Integer BUFFER_SIZE = 1024 * 10;

    /**
     * Create server on specified port.
     *
     * @param port       port on which server is running
     * @param dispatcher dispatcher used to process requests
     * @throws ChannelInitFailed if channel initialization failed
     */
    public Server(String ip, int port, Dispatcher dispatcher) throws ChannelInitFailed {
        this.dispatcher = dispatcher;
        this.socket = initSocket(ip, port);
    }

    /**
     * Create selector and run infinite serving loop.
     *
     * @throws ServerRunningFailed if server running failed
     */
    public void run() throws ServerRunningFailed {
        try {
            runLoop();
        } catch (Exception e) {
            throw new ServerRunningFailed();
        }
    }

    /**
     * Infinite serving loop.
     *
     * Get requests from channel and dispatch them to dispatcher.
     * Then send response to client.
     *
     * @throws Exception if loop failed
     */
    private void runLoop() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        while (true) {
            try {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                executorService.execute(new RequestHandler(socket, packet));
            } catch (IOException e) {
                exceptionsPull.add(e);
            }
        }
    }

    /**
     * Attachment for selection key.
     *
     * Contains response stream and client address.
     */
    @Data
    private class ResponseAttachment {
        public final ByteArrayOutputStream responseStream;
        public final InetSocketAddress clientAddress;
    }

    /**
     * Initialize socket on specified port.
     *
     * @param port port on which server is running
     * @return socket
     * @throws ChannelInitFailed if channel initialization failed
     */
    private DatagramSocket initSocket(String ip, int port) throws ChannelInitFailed {
        try {
            return new DatagramSocket(new InetSocketAddress(ip, port));
        } catch (IOException e) {
            throw new ChannelInitFailed();
        }
    }

    @RequiredArgsConstructor
    class RequestHandler implements Runnable {
        private final DatagramSocket socket;

        private final DatagramPacket packet;

        @Override
        public void run() {
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(packet.getData());

                ByteArrayOutputStream responseStream = dispatchStreams(inputStream);
                ByteBuffer buffer = ByteBuffer.wrap(responseStream.toByteArray());

                DatagramPacket responsePacket = new DatagramPacket(
                        buffer.array(), buffer.array().length,
                        packet.getAddress(), packet.getPort());

                socket.send(responsePacket);
            } catch (IOException e) {
                exceptionsPull.add(e);
            }
        }

        /**
         * Proceed deserialized request too dispatcher and return serialized response.
         *
         * @param requestStream request stream
         * @return response stream
         * @throws IOException if reading or writing failed
         */
        private ByteArrayOutputStream dispatchStreams(ByteArrayInputStream requestStream) throws IOException {
            Request request;
            try {
                request = Serializer.deserializeRequest(requestStream);
                Response response = dispatcher.dispatch(request);
                return Serializer.serializeResponse(response);
            } catch (BadRequestStream e) {
                Response response = Response.failure("Bad request stream", StatusCodes.BAD_REQUEST_STREAM);
                return Serializer.serializeResponse(response);
            } catch (Exception e) {
                Response response = Response.failure("Internal server error", StatusCodes.INTERNAL_SERVER_ERROR);
                return Serializer.serializeResponse(response);
            }
        }
    }
}
