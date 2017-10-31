package seedu.address.commons.events.commands;

import seedu.address.commons.events.BaseEvent;

/** Indicates the AddressBook in the display list is now filtered*/
public class DisplayBinEvent extends BaseEvent {

    public DisplayBinEvent () {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
