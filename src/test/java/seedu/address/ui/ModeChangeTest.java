package seedu.address.ui;

import static org.junit.Assert.assertTrue;
import static seedu.address.ui.MainWindow.DARK_MODE;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.UserPrefs;

public class ModeChangeTest {
    private UserPrefs prefs;

    public ModeChangeTest() {
    }

    @Before
    public void setUp() {
        prefs = new UserPrefs();

    }

    @Test
    public void check_valid_css() {
        assertTrue(prefs.getTheme().contains(DARK_MODE));
    }
}
