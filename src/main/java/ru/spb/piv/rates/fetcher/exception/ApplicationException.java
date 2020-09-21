package ru.spb.piv.rates.fetcher.exception;

public class ApplicationException extends RuntimeException {

    public static final String SOMETHING_IS_WRONG_MSG = "Something is wrong: %s";

    public ApplicationException(Throwable t) {
        super(String.format(SOMETHING_IS_WRONG_MSG, t.getMessage()), t);
    }

    public ApplicationException(String message) {
        super(String.format(SOMETHING_IS_WRONG_MSG, message));
    }
}
