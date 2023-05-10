package server;

import org.junit.Test;

import server.routing.Router;
import server.routing.exceptions.IncorrectHandlerParams;
import server.routing.exceptions.IncorrectHandlerReturns;
import server.routing.exceptions.UnhandledRequest;

public class RouterTest {
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
