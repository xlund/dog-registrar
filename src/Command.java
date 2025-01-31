public enum Command {
    LIST_COMMANDS,
    REGISTER_NEW_OWNER,
    REMOVE_OWNER,
    REGISTER_NEW_DOG,
    REMOVE_DOG,
    LIST_OWNERS,
    LIST_DOGS,
    INCREASE_AGE,
    GIVE_DOG_TO_OWNER,
    REMOVE_DOG_FROM_OWNER,
    EXIT;

    // Convert input string to Command enum (case-insensitive)
    public static Command fromString(String input) {
        for (Command command : Command.values()) {
            if (command.name().equalsIgnoreCase(input.replace(" ", "_"))) {
                return command;
            }
        }
        // Would prefer to throw an exception here probably
        return null;
    }

    public static void printCommands() {
        Printer.printHeader("Commands", "");
        for (Command command : Command.values()) {
            Printer.printRow("* " + command.toString());
        }
    }

    @Override
    public String toString() {
        return this.name().replace("_", " ").toLowerCase();
    }
}
