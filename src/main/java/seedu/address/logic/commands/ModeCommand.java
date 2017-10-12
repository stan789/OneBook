package seedu.address.logic.commands;
import javafx.scene.Scene;
import seedu.address.ui.MainWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

import static seedu.address.ui.MainWindow.scene;


public class ModeCommand extends Command {

    public static final String COMMAND_WORD = "mode";

    public static final String MESSAGE_SUCCESS = "New mode enabled.";

    private Stage primaryStage;
    private Logic logic;
    private Config config;
    private UserPrefs prefs;

    private static final String LIGHT_MODE = "view/LightTheme.css";
    private static final String DARK_MODE = "view/DarkTheme.css";


    @Override
    public CommandResult execute() {



        System.out.println(scene.getStylesheets());
        if(scene.getStylesheets().contains(DARK_MODE)) {
            scene.getStylesheets().remove(DARK_MODE);
            scene.getStylesheets().add(LIGHT_MODE);
        }
        else{
            scene.getStylesheets().remove(LIGHT_MODE);
            scene.getStylesheets().add(DARK_MODE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
