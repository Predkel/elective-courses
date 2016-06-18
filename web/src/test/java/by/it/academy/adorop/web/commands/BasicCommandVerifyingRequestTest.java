package by.it.academy.adorop.web.commands;

import org.junit.Test;

import static org.junit.Assert.*;

public class BasicCommandVerifyingRequestTest extends CommandTest {

    BasicCommandVerifyingRequest command;

    @Test
    public void testGetLogger() throws Exception {
        assertNotNull(command.getLogger());
    }
}