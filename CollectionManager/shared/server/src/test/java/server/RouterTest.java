package server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

import server.requests.Request;
import server.responses.Response;
import server.routers.BasicRouter;
import server.routers.MiddlewareRouter;
import server.routing.Router;
import server.routing.exceptions.IncorrectHandlerParams;
import server.routing.exceptions.IncorrectHandlerReturns;
import server.routing.exceptions.UnhandledRequest;
import server.routing.handlers.Handler;
import server.routing.middlewares.InnerMiddlewareFunction;

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
    public void testMiddlewaresResolve() {
        Router router;
        router = new MiddlewareRouter("testPrefix");

        Optional<InnerMiddlewareFunction> innerMiddleware;
        Response response;

        innerMiddleware = router.resolveInnerMiddleware("testPrefix.foo");
        assertTrue(innerMiddleware.isPresent());

        try {
            response = innerMiddleware.get().handle(null, null);
            assertEquals(response.getMessage(), "Hello from foo!");
        } catch (Exception e) {
            assert false;
            return;
        }

        innerMiddleware = router.resolveInnerMiddleware("testPrefix.bar");
        assertTrue(innerMiddleware.isPresent());
        try {
            response = innerMiddleware.get().handle(null, null);
            assertEquals(response.getMessage(), "Hello from all!");
        } catch (Exception e) {
            assert false;
            return;
        }

        innerMiddleware = router.resolveInnerMiddleware("testPrefix.baz");
        innerMiddleware = router.resolveInnerMiddleware("testPrefixbar");
        innerMiddleware = router.resolveInnerMiddleware("foo");
    }

    @Test
    public void testHandlersResolve() {
        Router router;
        try {
            router = new BasicRouter();
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
            router = new BasicRouter("");
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
