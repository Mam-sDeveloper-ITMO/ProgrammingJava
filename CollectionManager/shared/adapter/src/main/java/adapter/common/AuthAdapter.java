package adapter.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import adapter.Adapter;
import adapter.exceptions.ReceiveResponseFailed;
import adapter.exceptions.SendRequestFailed;
import adapter.exceptions.SocketInitFailed;
import auth.AuthToken;
import lombok.Getter;
import lombok.Setter;
import server.responses.Response;

/**
 * Adapter to service that requite authorization.
 */
public class AuthAdapter extends Adapter {
    /**
     * Auth token.
     */
    @Getter
    @Setter
    private Optional<AuthToken> token = Optional.empty();

    /**
     * Create adapter to service that support authorization.
     *
     * @param ip   service ip
     * @param port service port
     */
    public AuthAdapter(String ip, int port) {
        super(ip, port);
    }

    /**
     * Update token and send request to service.
     *
     * @inheritDoc
     */
    @Override
    public Response triggerServer(String trigger, Map<String, Serializable> data, Integer attempts)
            throws SocketInitFailed, SendRequestFailed, ReceiveResponseFailed {
        Map<String, Serializable> dataWithToken = new HashMap<>(data);
        dataWithToken.put("token", this.token.get());
        return super.triggerServer(trigger, dataWithToken, attempts);
    }

    /**
     * Update token and send request to service.
     *
     * @inheritDoc
     */
    @Override
    public Response triggerServer(String trigger, Map<String, Serializable> data)
            throws SocketInitFailed, SendRequestFailed, ReceiveResponseFailed {
        return this.triggerServer(trigger, data, 3);
    }
}
