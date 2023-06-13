package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

import lombok.Data;
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
public class Server {
    /**
     * Dispatcher is used to process requests.
     */
    private Dispatcher dispatcher;

    /**
     * Channel on which server is running.
     */
    private DatagramChannel channel;

    /**
     * Size of buffer for reading and writing.
     */
    private final int BUFFER_SIZE = 1024 * 10;

    /**
     * Create server on specified port.
     *
     * @param port       port on which server is running
     * @param dispatcher dispatcher used to process requests
     * @throws ChannelInitFailed if channel initialization failed
     */
    public Server(String ip, int port, Dispatcher dispatcher) throws ChannelInitFailed {
        this.dispatcher = dispatcher;
        this.channel = initChannel(ip, port);
    }

    /**
     * Create selector and run infinite serving loop.
     *
     * @throws ServerRunningFailed if server running failed
     */
    public void run() throws ServerRunningFailed {
        try (Selector selector = initSelector()) {
            runLoop(selector);
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
     * @param selector selector
     * @throws Exception if loop failed
     */
    private void runLoop(Selector selector) throws Exception {
        while (true) {
            if (selector.select() == 0) {
                continue;
            }
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (!key.isValid()) {
                    continue;
                }
                try {
                    if (key.isReadable()) {
                        read(channel, key);
                    } else if (key.isWritable()) {
                        write(channel, key);
                    }
                } catch (IOException e) {
                    // if UDP doesn't guarantee delivery
                    // why should we care about exceptions?
                }
                keyIterator.remove();
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
     * Read request from channel and dispatch it to dispatcher.
     *
     * @param channel channel on which server is running
     * @param key     selection key
     * @throws IOException if reading failed
     */
    private void read(DatagramChannel channel, SelectionKey key) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);
        buffer.flip();

        ByteArrayInputStream requestStream = new ByteArrayInputStream(buffer.array());
        ByteArrayOutputStream responseStream = dispatchStreams(requestStream);

        ResponseAttachment attachment = new ResponseAttachment(responseStream, clientAddress);
        key.attach(attachment);
        key.interestOps(SelectionKey.OP_WRITE);
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
            Response response = this.dispatcher.dispatch(request);
            return Serializer.serializeResponse(response);
        } catch (BadRequestStream e) {
            Response response = Response.failure("Bad request stream", StatusCodes.BAD_REQUEST_STREAM);
            return Serializer.serializeResponse(response);
        } catch (Exception e) {
            Response response = Response.failure("Internal server error", StatusCodes.INTERNAL_SERVER_ERROR);
            return Serializer.serializeResponse(response);
        }
    }

    /**
     * Write response to channel.
     *
     * @param channel channel on which server is running
     * @param key     selection key
     * @throws IOException if writing failed
     */
    private void write(DatagramChannel channel, SelectionKey key) throws IOException {
        ResponseAttachment attachment = (ResponseAttachment) key.attachment();
        if (attachment == null) {
            key.interestOps(SelectionKey.OP_READ);
            return;
        }
        ByteBuffer buffer = ByteBuffer.wrap(attachment.getResponseStream().toByteArray());
        channel.send(buffer, attachment.getClientAddress());
        key.interestOps(SelectionKey.OP_READ);
    }

    /**
     * Initialize channel on specified port.
     * Channel is non-blocking.
     *
     * @param port port on which server is running
     * @return channel
     * @throws ChannelInitFailed if channel initialization failed
     */
    private DatagramChannel initChannel(String ip, int port) throws ChannelInitFailed {
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.bind(new InetSocketAddress(ip, port));
            channel.configureBlocking(false);
            return channel;
        } catch (IOException e) {
            throw new ChannelInitFailed();
        }
    }

    /**
     * Initialize selector and register channel on it.
     *
     * @return selector
     * @throws ServerRunningFailed if selector initialization failed
     */
    private Selector initSelector() throws ServerRunningFailed {
        try {
            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);
            return selector;
        } catch (IOException e) {
            throw new ServerRunningFailed();
        }
    }
}
