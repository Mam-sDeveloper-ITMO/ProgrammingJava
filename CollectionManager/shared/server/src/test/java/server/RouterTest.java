package server;

import org.junit.Test;

import server.requests.Request;
import server.responses.Response;
import server.routing.Router;
import server.routing.exceptions.IncorrectHandlerParams;
import server.routing.exceptions.IncorrectHandlerReturns;
import server.routing.exceptions.UnhandledRequest;
import server.routing.handlers.Handler;

public class RouterTest {
    @Test
    public void incorrectHandler() {
        class TestRouter extends Router {
            public TestRouter() throws IncorrectHandlerParams, IncorrectHandlerReturns {
                super();
            }

            @Handler("testPrefix.foo")
            public Response foo(Request request) {
                return null;
            }
        }

        try {
            new TestRouter();
            assert false;
        } catch (IncorrectHandlerParams e) {
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testHandlersResolve() {
        Router router;
        try {
            router = new TestRouter();
        } catch (IncorrectHandlerParams | IncorrectHandlerReturns e) {
            assert false;
            return;
        }

        try {
            router.resolveHandler("testPrefix.foo");
        } catch (UnhandledRequest e) {
            assert false;
            return;
        }
        try {
            router.resolveHandler("testPrefix.bar");
        } catch (UnhandledRequest e) {
            assert false;
            return;
        }
        try {
            router.resolveHandler("testPrefix.baz");
            router.resolveHandler("testPrefixbar");
            router.resolveHandler("foo");
            assert false;
        } catch (UnhandledRequest e) {
            assert true;
        }
        try {
            router = new TestRouter("");
        } catch (IncorrectHandlerParams | IncorrectHandlerReturns e) {
            assert false;
            return;
        }

        try {
            router.resolveHandler("foo");
        } catch (UnhandledRequest e) {
            assert false;
            return;
        }
        try {
            router.resolveHandler("bar");
        } catch (UnhandledRequest e) {
            assert false;
            return;
        }
        try {
            router.resolveHandler("baz");
            router.resolveHandler("testPrefixbar");
            router.resolveHandler(".foo");
            assert false;
        } catch (UnhandledRequest e) {
            assert true;
        }
    }
}
