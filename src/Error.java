enum Error implements MessageProvider {
    NO_OWNERS_FOUND("No owners found in register"),
    NO_DOGS_FOUND("No dogs found in register"),
    EMPTY_STRING_NOT_ALLOWED("A blank string is not allowed, try again"),
    UNSUPPORTED_COMMAND("Command not supported"),
    DOG_NOT_FOUND("Could not find the dog '%s'"),
    OWNER_NOT_FOUND("Could not find the owner '%s'"),
    DUPLICATE_OWNER("An owner '%s' already exist"),
    DUPLICATE_DOG("A dog '%s' already exist"),
    ASSIGNMENT_OF_DOG_WITH_OWNER("The dog '%s' already has an owner '%s' and can not be reassigned"),
    INPUT_ALREADY_IN_USE("Input is already in use");

    private static final String LEADING_TEXT = "Error: ";
    private final String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return LEADING_TEXT + String.format(message, args) + "\n";
    }


    @Override
    public String toString() {
        return name() + ": " + message;
    }
}
