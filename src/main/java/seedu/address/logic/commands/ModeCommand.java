package seedu.address.logic.commands;

import javafx.scene.Scene;
import seedu.address.ui.AddressPanel;
import seedu.address.ui.MainWindow;

/**
 * This is to enable change mode.
 */
public class ModeCommand extends Command {

    public static final String COMMAND_WORD = "mode";
    public static final String DEFAULT_HTML = "LightTheme.css";

    public static final String MESSAGE_SUCCESS = "New mode enabled.";
    private static final String LIGHT_MODE = "view/LightTheme.css";
    private static final String DARK_MODE = "view/DarkTheme.css";


    @Override
    public CommandResult execute() {
        Scene scene = MainWindow.getScene();
        AddressPanel addressPanel = MainWindow.getAddressPanel();
        if (scene.getStylesheets().contains(DARK_MODE)) {
            scene.getStylesheets().remove(DARK_MODE);
            scene.getStylesheets().add(LIGHT_MODE);
            addressPanel.setDefaultPage();

        } else {
            scene.getStylesheets().remove(LIGHT_MODE);
            scene.getStylesheets().add(DARK_MODE);
            addressPanel.setDefaultPage();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
