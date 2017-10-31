package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organisation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        Phone phone;
        Birthday birthday;
        Email email;
        Address address;
        Organisation organisation;
        Remark remark;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_BIRTHDAY, PREFIX_EMAIL,
                                           PREFIX_ADDRESS, PREFIX_ORGANISATION, PREFIX_REMARK, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Optional<Phone> checkPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE));
            if (!checkPhone.isPresent()) {
                phone = new Phone(null);
            } else {
                phone = checkPhone.get();
            }
            Optional<Birthday> checkBirthday = ParserUtil.parseBirthday(argMultimap.getValue(PREFIX_BIRTHDAY));
            if (!checkBirthday.isPresent()) {
                birthday = new Birthday(null);
            } else {
                birthday = checkBirthday.get();
            }
            Optional<Email> checkEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL));
            if (!checkEmail.isPresent()) {
                email = new Email(null);
            } else {
                email = checkEmail.get();
            }
            Optional<Address> checkAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
            if (!checkAddress.isPresent()) {
                address = new Address(null);
            } else {
                address = checkAddress.get();
            }
            Optional<Organisation> checkOrganisation = ParserUtil
                    .parseOrganisation(argMultimap.getValue(PREFIX_ORGANISATION));
            if (!checkOrganisation.isPresent()) {
                organisation = new Organisation(null);
            } else {
                organisation = checkOrganisation.get();
            }
            Optional<Remark> checkRemark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK));
            if (!checkRemark.isPresent()) {
                remark = new Remark(null);
            } else {
                remark = checkRemark.get();
            }

            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyPerson person = new Person(name, phone, birthday, email, address, organisation, remark, tagList);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
