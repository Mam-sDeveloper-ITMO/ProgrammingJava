package auth;

import lombok.RequiredArgsConstructor;

/**
 * Adapter provides communication with server
 * and utils for sending triggers requests to server
 *
 * Adapter use DatagramSocket for sending and receiving data.
 */
@RequiredArgsConstructor
public class AuthProvider {
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
}
