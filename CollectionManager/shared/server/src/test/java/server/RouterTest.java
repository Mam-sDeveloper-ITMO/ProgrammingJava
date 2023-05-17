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
import server.routing.handlers.Handler;
import server.routing.handlers.HandlerFunction;
import server.routing.handlers.exceptions.IncorrectHandlerParams;
import server.routing.handlers.exceptions.IncorrectHandlerReturns;
import server.routing.middlewares.InnerMiddlewareFunction;
import server.routing.middlewares.OuterMiddlewareFunction;

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
    public void testMiddlewares() {
        Router router;
        router = new MiddlewareRouter("testPrefix");

        Optional<InnerMiddlewareFunction> innerMiddleware;
        Optional<OuterMiddlewareFunction> outerMiddleware;
        Response response;

        String trigger;
        trigger = router.resolveTrigger("testPrefix.foo").get();

        innerMiddleware = router.resolveInnerMiddleware(trigger);
        assertTrue(innerMiddleware.isPresent());

        outerMiddleware = router.resolveOuterMiddleware(trigger);
        assertTrue(outerMiddleware.isPresent());

        try {
            response = innerMiddleware.get().handle(null, null);
            assertEquals(response.getMessage(), "Hello from foo!");
            response = outerMiddleware.get().handle(response);
            assertEquals(response.getData().get("hint"), "foo");
        } catch (Exception e) {
            assert false;
            return;
        }

        trigger = router.resolveTrigger("testPrefix.bar").get();
        innerMiddleware = router.resolveInnerMiddleware("testPrefix.bar");
        assertTrue(innerMiddleware.isPresent());

        outerMiddleware = router.resolveOuterMiddleware(trigger);
        assertTrue(outerMiddleware.isPresent());

        try {
            response = innerMiddleware.get().handle(null, null);
            assertEquals(response.getMessage(), "Hello from all!");
            response = outerMiddleware.get().handle(response);
            assertEquals(response.getData().get("hint"), "all");
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
        Optional<HandlerFunction> handler;

        trigger = router.resolveTrigger("testPrefix.foo").get();
        handler = router.resolveHandler(trigger);
        assertTrue(handler.isPresent());

        trigger = router.resolveTrigger("testPrefix.bar").get();
        handler = router.resolveHandler(trigger);
        assertTrue(handler.isPresent());

        handler = router.resolveHandler("testPrefix.baz");
        assertFalse(handler.isPresent());
        handler = router.resolveHandler("testPrefixbar");
        assertFalse(handler.isPresent());
        handler = router.resolveHandler(".foo");
        assertFalse(handler.isPresent());

        try {
            router = new BasicRouter("");
        } catch (IncorrectHandlerParams | IncorrectHandlerReturns e) {
            assert false;
            return;
        }

        trigger = router.resolveTrigger("foo").get();
        handler = router.resolveHandler("foo");
        assertTrue(handler.isPresent());

        trigger = router.resolveTrigger("bar").get();
        handler = router.resolveHandler("bar");
        assertTrue(handler.isPresent());

        handler = router.resolveHandler("baz");
        assertFalse(handler.isPresent());
        handler = router.resolveHandler("testPrefixbar");
        assertFalse(handler.isPresent());
        handler = router.resolveHandler(".foo");
        assertFalse(handler.isPresent());
    }
}
