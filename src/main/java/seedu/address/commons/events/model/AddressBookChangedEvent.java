package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook addressBook;
    public final ReadOnlyAddressBook recycleBin;

    public AddressBookChangedEvent(ReadOnlyAddressBook addressBook, ReadOnlyAddressBook recycleBin) {
        this.addressBook = addressBook;
        this.recycleBin = recycleBin;
    }

    @Override
    public String toString() {
        return "number of persons " + addressBook.getPersonList().size() + ", number of tags "
                + addressBook.getTagList().size();
    }
}
