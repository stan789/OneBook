package seedu.address.logic.commands;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class ModeCommand extends Command {

    public static final String COMMAND_WORD = "mode";

    public static final String MESSAGE_SUCCESS = "New mode enabled.";

//    StackPane root = new StackPane();
//    final Scene scene = new Scene(root, 450, 600);
//    private String theme1Url = getClass().getResource("WhiteTheme.css").toExternalForm();
//    private String theme2Url = getClass().getResource("DarkTheme.css").toExternalForm();

    @Override
    public CommandResult execute() {
//        if(scene.getStylesheets().contains(theme1Url)) {
//            scene.getStylesheets().remove(theme1Url);
//            scene.getStylesheets().add(theme2Url);
//        }
//        else{
//            scene.getStylesheets().remove(theme1Url);
//            scene.getStylesheets().add(theme2Url);
//        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
