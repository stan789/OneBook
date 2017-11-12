package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class ModeChangeRequestEvent extends BaseEvent {
    public ModeChangeRequestEvent()  {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
