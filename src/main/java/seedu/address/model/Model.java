package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyAddressBookException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.storage.AddressBookData;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(AddressBookData newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the RecycleBin */
    ReadOnlyAddressBook getRecycleBin();

    /** Deletes the given person from addressbook */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException, DuplicatePersonException;

    /** Adds the given person to addressbook */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /** Restores the given person from recycle bin */
    void restorePerson(ReadOnlyPerson target) throws PersonNotFoundException, DuplicatePersonException;

    /** Deletes the given person from bin */
    void deleteFromBin(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    /** Returns an unmodifiable view of the filtered bin list */
    ObservableList<ReadOnlyPerson> getFilteredBinList();

    /**
     * Updates the filter of the filtered bin list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBinList(Predicate<ReadOnlyPerson> predicate);

    void executeSort(String sortType) throws EmptyAddressBookException;

    void executeBinSort(String sortType) throws EmptyAddressBookException;

    Integer importFile(Path fileLocation) throws IOException;
}
