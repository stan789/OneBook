package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class ContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContainsKeywordsPredicate firstPredicate =
                new ContainsKeywordsPredicate(firstPredicateKeywordList, "name");
        ContainsKeywordsPredicate secondPredicate =
                new ContainsKeywordsPredicate(secondPredicateKeywordList, "name");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsKeywordsPredicate firstPredicateCopy =
                new ContainsKeywordsPredicate(firstPredicateKeywordList, "name");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.singletonList("Alice"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("Serangoon"), "address");
        assertTrue(predicate.test(new PersonBuilder().withAddress("Serangoon Street 30").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("example"), "email");
        assertTrue(predicate.test(new PersonBuilder().withEmail("example@123.com").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("1234567"), "phone");
        assertTrue(predicate.test(new PersonBuilder().withPhone("123456789").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("10"), "birthday");
        assertTrue(predicate.test(new PersonBuilder().withBirthday("01-10-1995").build()));

        predicate = new ContainsKeywordsPredicate(Collections.singletonList("friend"), "tag");
        assertTrue(predicate.test(new PersonBuilder().withTags("friends" + "colleagues").build()));

        // Multiple keywords
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new ContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"), "name");
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicate(Collections.emptyList(), "");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new ContainsKeywordsPredicate(Arrays.asList("Carol"), "name");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new ContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"), "name");
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
