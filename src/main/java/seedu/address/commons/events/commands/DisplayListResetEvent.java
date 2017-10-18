package seedu.address.commons.events.commands;

import seedu.address.commons.events.BaseEvent;

/** Indicates the AddressBook in the display list is now filtered*/
public class DisplayListResetEvent extends BaseEvent {

    public DisplayListResetEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}