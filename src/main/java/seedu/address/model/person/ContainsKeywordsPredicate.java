package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} or {@code Address} or {@code Email} or {@code Phone}
 * matches any of the keywords given, depending on the main keyword.
 */
public class ContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;
    private final String mainKeyword;

    public ContainsKeywordsPredicate(List<String> keywords, String mainKeyword) {
        this.keywords = keywords;
        this.mainKeyword = mainKeyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        switch (mainKeyword) {
            case "name": return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));

            case "address": return keywords.stream()
                    .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));

            case "email": return keywords.stream()
                    .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));

            case "phone": return keywords.stream()
                    .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().toString(), keyword));

        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((ContainsKeywordsPredicate) other).keywords)); // state check
    }

}
