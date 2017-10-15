package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class ModeCommandTest {

    private ModeCommand modeCommand;

    @Before
    public void setUp() {
        modeCommand = new ModeCommand();
    }

    @Test
    public void equals() {
        assertTrue(modeCommand.DEFAULT_HTML.equals("LightTheme.css"));
    }

}
