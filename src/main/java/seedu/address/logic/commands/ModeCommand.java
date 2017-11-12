package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ModeChangeRequestEvent;

/**
 * This is to enable change mode.
 */
public class ModeCommand extends Command {

    public static final String COMMAND_WORD = "mode";

    public static final String MESSAGE_SUCCESS = "New mode enabled.";
    private static final String LIGHT_MODE = "view/LightTheme.css";
    private static final String DARK_MODE = "view/DarkTheme.css";

    //@@author darrinloh
    @Override
    public CommandResult execute() {

        EventsCenter.getInstance().post(new ModeChangeRequestEvent());

        return new CommandResult(MESSAGE_SUCCESS);
    }
    //@@author
}
