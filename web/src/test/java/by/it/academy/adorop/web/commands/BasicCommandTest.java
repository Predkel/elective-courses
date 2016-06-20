package by.it.academy.adorop.web.commands;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class BasicCommandTest extends CommandTest {

    BasicCommand command;

    @Test
    public void testGetLogger() throws Exception {
        assertNotNull(command.getLogger());
    }
}