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
import server.routing.exceptions.UnhandledRequest;
import server.routing.handlers.Handler;
import server.routing.handlers.exceptions.IncorrectHandlerParams;
import server.routing.handlers.exceptions.IncorrectHandlerReturns;
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

        String trigger;
        trigger = router.resolveTrigger("testPrefix.foo").get();
        innerMiddleware = router.resolveInnerMiddleware(trigger);
        assertTrue(innerMiddleware.isPresent());

        try {
            response = innerMiddleware.get().handle(null, null);
            assertEquals(response.getMessage(), "Hello from foo!");
        } catch (Exception e) {
            assert false;
            return;
        }

        trigger = router.resolveTrigger("testPrefix.bar").get();
        innerMiddleware = router.resolveInnerMiddleware("testPrefix.bar");
        assertTrue(innerMiddleware.isPresent());
        try {
            response = innerMiddleware.get().handle(null, null);
            assertEquals(response.getMessage(), "Hello from all!");
        } catch (Exception e) {
            assert false;
            return;
        }
    }

    @Test
    public void testHandlersResolve() {
        Router router;
        try {
            router = new BasicRouter("testPrefix");
        } catch (IncorrectHandlerParams | IncorrectHandlerReturns e) {
            assert false;
            return;
        }

        String trigger;
        try {
            trigger = router.resolveTrigger("testPrefix.foo").get();
            router.resolveHandler(trigger);
        } catch (UnhandledRequest e) {
            assert false;
            return;
        }
        try {
            trigger = router.resolveTrigger("testPrefix.bar").get();
            router.resolveHandler(trigger);
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
            trigger = router.resolveTrigger("foo").get();
            router.resolveHandler("foo");
        } catch (UnhandledRequest e) {
            assert false;
            return;
        }
        try {
            trigger = router.resolveTrigger("bar").get();
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
