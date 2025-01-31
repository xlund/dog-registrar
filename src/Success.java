enum Success implements MessageProvider {
    DOG_REMOVED_FROM_OWNER("'%s' owner was unassigned.\n'%s' now has no owner."),
    DOG_ASSIGNED_TO_OWNER("'%s' was assigned '%s' as a new owner."),
    DOG_AGE_INCREASED("'%s' is now one year older"),
    DOG_REGISTERED("'%s' was added to the dog register"),
    DOG_REMOVED("'%s' has been removed from the dog register"),
    OWNER_REGISTERED("'%s' was added to the owner register"),
    OWNER_REMOVED("'%s' was removed from the owner register");

    private static final String LEADING_TEXT = "success: ";
    private final String message;

    Success(String message) {
        this.message = message;
    }

    // Method to format the message with additional information
    public String getMessage(Object... args) {
        return LEADING_TEXT + String.format(message, args) + "\n";
    }


    @Override
    public String toString() {
        return name() + ": " + message;
    }
}
