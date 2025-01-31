public enum Prompt implements MessageProvider {
    ENTER_DOG_NAME("dog name"),
    ENTER_DOG_TAIL_LENGTH("dog tail length"),
    ENTER_OWNER_NAME("owner name"),
    ENTER_DOG_BREED("dog breed"),
    ENTER_DOG_AGE("dog age"),
    ENTER_DOG_WEIGTH("dog weight"),
    ENTER_COMMAND("command");

    private final static String PREFIX = "Enter ";
    private final static String SUFFIX = "?> ";

    private final String message;

    Prompt(String message) {
        this.message = message;
    }


    @Override
    public String getMessage(Object... args) {
        return PREFIX + String.format(message, args) + SUFFIX;
    }
}
