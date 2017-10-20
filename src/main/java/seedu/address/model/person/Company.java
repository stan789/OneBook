package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's Company in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompany(String)}
 */
public class Company {

    public static final String MESSAGE_COMPANY_CONSTRAINTS =
            "Person company can take any values";

    /*
     * The first character of the company must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String COMPANY_VALIDATION_REGEX = "[^\\s].*";
    public static final String COMPANY_NOT_ASSIGNED = "-";

    public final String value;

    /**
     * Validates given company.
     *
     * @throws IllegalValueException if given company string is invalid.
     */
    public Company(String company) throws IllegalValueException {

        if (company == null) {
            this.value = COMPANY_NOT_ASSIGNED;
        } else {
            if (!isValidCompany(company)) {
                throw new IllegalValueException(MESSAGE_COMPANY_CONSTRAINTS);
            }
            this.value = company;
        }
    }

    /**
     * Returns true if a given string is a valid person company.
     */
    public static boolean isValidCompany(String test) {
        return test.matches(COMPANY_VALIDATION_REGEX) || test.matches(COMPANY_NOT_ASSIGNED);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && this.value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
