package seedu.address.storage.exceptions;

import java.io.IOException;

/**
 * Signals that the file read is an empty file.
 */

public class EmptyFileException extends IOException {
    public EmptyFileException() {

    }
}
