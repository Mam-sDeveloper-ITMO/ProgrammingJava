package cliapp.commands.collection;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cliapp.cliclient.CLIClient;
import cliapp.cliclient.exceptions.CommandNotFoundError;
import cliapp.cliclient.exceptions.InlineParamsError;
import commands.Command;
import humandeque.manager.CollectionManager;
import humandeque.manager.local.LocalManager;

public class CLIClientTest {
    private CLIClient client;

    @Before
    @Test
    public void testCommandsRegistration() {
        client = new CLIClient();

        CollectionManager manager = new LocalManager();
        client.registerCommand("add", new AddElementCommand(manager));
        client.registerCommand("update", new UpdateElementCommand(manager));
        client.registerCommand("remove", new RemoveByIdCommand(manager));
    }

    @Test
    public void testParseInlineParams() {
        assertEquals("add", client.parseInlineParams("add").get(0));
        assertEquals("3", client.parseInlineParams("add 3 1").get(1));
        assertEquals("1", client.parseInlineParams("add 3 1").get(2));
    }

    @Test
    public void testNotFuzzyResolveCommand() {
        client.setFuzzyMatching(false);
        Command command = client.getCommands().get("add");
        try {
            assertEquals(command, client.resolveCommand("add"));
        } catch (CommandNotFoundError e) {
            Assert.fail("CommandNotFoundError thrown");
        }
    }

    @Test
    public void testFuzzyResolveCommand() {
        client.setFuzzyMatching(true);
        Command command = client.getCommands().get("add");
        try {
            assertEquals(command, client.resolveCommand("ad"));
        } catch (CommandNotFoundError e) {
            Assert.fail("CommandNotFoundError thrown");
        }
    }

    @Test
    public void tesMapStaticRequirements() {
        Command command = client.getCommands().get("update");
        List<String> params = client.parseInlineParams("update 3");
        params.remove(0);
        try {
            Map<String, String> requirements =
                client.mapStaticRequirements(command.getStaticRequirements(), params);
            assertEquals(requirements.get("id"), "3");
        } catch (InlineParamsError e) {
            Assert.fail("Exception thrown");
        }
    }

    @Test
    public void testExecuteCommand() {
        Command command = client.getCommands().get("remove");
        client.executeCommand(command, List.of("1"));
    }
}
