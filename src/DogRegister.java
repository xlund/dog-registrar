// Erik Näslund erns6604

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DogRegister {
    private static final String EMPTY_STRING = "";
    private static final String[] OWNER_LIST_PRINT_HEADERS = {"Owner", "Dogs"};
    private static final String[] DOG_LIST_PRINT_HEADERS = {"Name", "Breed", "Age", "Weight", "Tail", "Owner"};

    private final InputReader input = new InputReader();
    private final DogCollection dogs = new DogCollection();
    private final OwnerCollection owners = new OwnerCollection();

    private void runCommandLoop() {
        Command command;
        do {
            command = readCommand();
            handleCommand(command);
        } while (!command.equals(Command.EXIT));
    }

    private void handleCommand(Command command) {
        switch (command) {
            case Command.REGISTER_NEW_OWNER:
                registerOwner();
                break;
            case Command.REMOVE_OWNER:
                removeOwner();
                break;
            case Command.REGISTER_NEW_DOG:
                registerDog();
                break;
            case Command.REMOVE_DOG:
                removeDog();
                break;
            case Command.LIST_DOGS:
                listDogs();
                break;
            case Command.LIST_OWNERS:
                listOwners();
                break;
            case Command.INCREASE_AGE:
                increaseAge();
                break;
            case Command.GIVE_DOG_TO_OWNER:
                giveDogToOwner();
                break;
            case Command.REMOVE_DOG_FROM_OWNER:
                removeDogFromOwner();
                break;
            case Command.LIST_COMMANDS:
                Command.printCommands();
                break;
            case Command.EXIT:
                // Need to handle exit command to prevent default behaviour,
                // even though nothing should happen
                break;
            default:
                Printer.printMessage(Error.UNSUPPORTED_COMMAND);
        }
    }

    private void registerOwner() {
        String name = promptForNonEmptyString(Prompt.ENTER_OWNER_NAME);
        if (owners.containsOwner(name)) {
            Printer.printMessage(Error.DUPLICATE_OWNER, name);
            return;
        }
        owners.addOwner(new Owner(name));
        Printer.printMessage(Success.OWNER_REGISTERED, name);
    }

    private void removeOwner() {
        if (checkOwnersIsEmpty()) return;
        String name = promptForNonEmptyString(Prompt.ENTER_OWNER_NAME);
        owners.getOwner(name).ifPresentOrElse(
                this::handleOwnerRemoval,
                () -> Printer.printMessage(Error.OWNER_NOT_FOUND, name)
        );

    }

    private void registerDog() {
        String name = promptForNonEmptyString(Prompt.ENTER_DOG_NAME);
        if (!checkIfDogIsUnique(name)) return;
        String breed = promptForNonEmptyString(Prompt.ENTER_DOG_BREED);
        int age = input.readInt(Prompt.ENTER_DOG_AGE);
        int weight = input.readInt(Prompt.ENTER_DOG_AGE);
        dogs.addDog(new Dog(name, breed, age, weight));
        Printer.printMessage(Success.DOG_REGISTERED, name);
    }

    private void removeDog() {
        if (checkDogsIsEmpty()) return;
        String name = input.readString(Prompt.ENTER_DOG_NAME);
        if (isEmptyString(name)) return;
        dogs.getDog(name).ifPresentOrElse(
                this::handleDogRemoval,
                () -> Printer.printMessage(Error.DOG_NOT_FOUND, name)
        );
    }

    private void handleDogRemoval(Dog dog) {
        dog.getOwner().ifPresent(_ -> dog.setOwner(null)); // Unassign owner if present
        dogs.removeDog(dog);
        Printer.printMessage(Success.DOG_REMOVED, dog.getName());
    }

    private void listDogs() {
        if (checkDogsIsEmpty()) return;
        double minTailLength = input.readDouble(Prompt.ENTER_DOG_TAIL_LENGTH);
        if (dogs.getDogsWithMinTailLengthOf(minTailLength).isEmpty()) {
            Printer.printMessage(Error.NO_DOGS_FOUND);
            return;
        }
        ArrayList<Dog> filteredDogs = dogs.getDogsWithMinTailLengthOf(minTailLength);

        printDogs(filteredDogs);
    }

    private void listOwners() {
        if (checkOwnersIsEmpty()) return;
        Printer.printHeader(OWNER_LIST_PRINT_HEADERS);
        for (Owner owner : owners.getOwners()) {
            printOwner(owner);
        }
    }

    private void increaseAge() {
        if (checkDogsIsEmpty()) return;
        String name = input.readString(Prompt.ENTER_DOG_NAME);
        dogs.getDog(name).ifPresentOrElse(
                dog -> {
                    dog.increaseAge();
                    Printer.printMessage(Success.DOG_AGE_INCREASED, name);
                },
                () -> Printer.printMessage(Error.DOG_NOT_FOUND, name)
        );
    }

    private void giveDogToOwner() {
        if (checkDogsIsEmpty() || checkOwnersIsEmpty()) return;
        String dogName = promptForNonEmptyString(Prompt.ENTER_DOG_NAME);
        dogs.getDog(dogName).ifPresentOrElse(
                this::giveDogToOwner,
                () -> Printer.printMessage(Error.DOG_NOT_FOUND, dogName)
        );
    }

    private void giveDogToOwner(Dog dog) {
        dog.getOwner().ifPresentOrElse(
                owner -> Printer.printMessage(Error.ASSIGNMENT_OF_DOG_WITH_OWNER, dog.getName(), owner.getName()),
                () -> handleOwnerAssignment(dog)
        );
    }

    private void handleOwnerAssignment(Dog dog) {
        String name = promptForNonEmptyString(Prompt.ENTER_OWNER_NAME);
        owners.getOwner(name).ifPresentOrElse(
                owner -> {
                    dog.setOwner(owner);
                    Printer.printMessage(Success.DOG_ASSIGNED_TO_OWNER, dog.getName(), name);
                },
                () -> Printer.printMessage(Error.OWNER_NOT_FOUND, name)
        );
    }

    private void removeDogFromOwner() {
        if (checkDogsIsEmpty() || checkOwnersIsEmpty()) return;

        String name = promptForNonEmptyString(Prompt.ENTER_DOG_NAME);
        if (dogs.getDog(name).isEmpty()) {
            Printer.printMessage(Error.DOG_NOT_FOUND, name);
            return;
        }
        dogs.getDog(name).get().setOwner(null);
        Printer.printMessage(Success.DOG_REMOVED_FROM_OWNER, name, name);
    }

    private String promptForNonEmptyString(Prompt prompt) {
        String inputString;
        do {
            inputString = input.readString(prompt);
        } while (isEmptyString(inputString));
        return inputString.toUpperCase();
    }

    private boolean checkIfDogIsUnique(String name) {
        if (dogs.containsDog(name)) {
            Printer.printMessage(Error.DUPLICATE_DOG, name);
            return false;
        }
        return true;
    }

    private boolean isEmptyString(String name) {
        if (name.trim().isEmpty()) {
            Printer.printMessage(Error.EMPTY_STRING_NOT_ALLOWED);
            return true;
        }
        return false;
    }

    private void handleOwnerRemoval(Owner owner) {
        if (removeDog(owner))
            Printer.printMessage(Success.OWNER_REMOVED, owner.getName());
        else
            Printer.printMessage(Error.OWNER_NOT_FOUND, owner.getName());
    }

    private boolean removeDog(Owner owner) {
        ArrayList<Dog> ownerDogs = owner.getDogs();
        if (!dogs.getDogs().isEmpty()) {
            for (Dog dog : ownerDogs) {
                owner.removeDog(dog);
                dogs.removeDog(dog);
            }
        }
        return owners.removeOwner(owner);
    }

    private boolean checkDogsIsEmpty() {
        if (dogs.getDogs().isEmpty()) {
            Printer.printMessage(Error.NO_DOGS_FOUND);
            return true;
        }
        return false;
    }

    private boolean checkOwnersIsEmpty() {
        if (owners.getOwners().isEmpty()) {
            Printer.printMessage(Error.NO_OWNERS_FOUND);
            return true;
        }
        return false;
    }

    private void printOwner(Owner owner) {
        Printer.printRow(owner.getName(), stringifyDogs(owner.getDogs()));
    }

    private String stringifyDogs(ArrayList<Dog> dogs) {
        if (dogs.isEmpty()) return EMPTY_STRING;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < dogs.size(); i++) {
            builder.append(dogs.get(i).getName());
            if (i < dogs.size() - 1) builder.append(", ");
        }
        return builder.toString();
    }

    private void printDogs(ArrayList<Dog> dogs) {
        Printer.printHeader(DOG_LIST_PRINT_HEADERS);
        for (Dog dog : dogs) {
            printDog(dog);
        }
    }

    private void printDog(Dog dog) {
        String ownerName = dog.getOwner().isPresent() ? dog.getOwner().get().getName() : EMPTY_STRING;
        Printer.printRow(dog.getName(), dog.getBreed(), dog.getAge(), dog.getWeight(), dog.getTailLength(), ownerName);
    }

    private Command readCommand() {
        String cmd = input.readString(Prompt.ENTER_COMMAND);
        return Command.fromString(cmd);
    }

    private void setup() {
        seed();
        greet();
    }

    private void greet() {
        Printer.welcomeMessage();
        Command.printCommands();
    }


    private void seed() {
        ArrayList<Dog> generatedDogs = generateDogs();
        generateOwners(generatedDogs);
    }

    private ArrayList<Dog> generateDogs() {
        Random random = new Random();
        List<String> dogNames = Arrays.asList("Buddy", "Bella", "Max", "Lucy", "Olle", "Charlie", "Daisy", "Rocky", "Molly", "Bailey");
        List<String> breeds = Arrays.asList("Golden Retriever", "Poodle", "Bulldog", "Beagle", "German Shepherd", "Labrador", "Boxer", "Siberian Husky");
        // Generate Dogs
        int numberOfDogs = 10; // Adjust as needed
        List<Dog> generatedDogs = new ArrayList<>();

        for (int i = 0; i < numberOfDogs; i++) {
            String name = dogNames.get(i % dogNames.size());
            String breed = breeds.get(random.nextInt(breeds.size()));
            int age = random.nextInt(15) + 1; // Age between 1 and 15
            int weight = random.nextInt(50) + 5; // Weight between 5 and 54 kg

            Dog dog = new Dog(name, breed, age, weight);
            generatedDogs.add(dog);
            dogs.addDog(dog); // Assuming 'dogs' is your collection for Dog objects
        }
        return new ArrayList<>(generatedDogs);
    }

    private void generateOwners(ArrayList<Dog> generatedDogs) {
        List<String> ownerNames = Arrays.asList("Lisa", "Mary", "Bob", "John", "Alice", "Tom", "Emma", "Jake");
        // Generate Owners
        Random random = new Random();
        int numberOfOwners = 8; // Adjust as needed

        for (int i = 0; i < numberOfOwners; i++) {
            String ownerName = ownerNames.get(i % ownerNames.size()); // Ensures names are reused if numberOfOwners > ownerNames.size()
            Owner owner = new Owner(ownerName);
            // Assign a random number of dogs to each owner (e.g., 0 to 3)
            int dogsToAssign = random.nextInt(4);
            for (int j = 0; j < dogsToAssign && !generatedDogs.isEmpty(); j++) {
                // Select a random dog that is not already assigned
                Dog dog = generatedDogs.remove(random.nextInt(generatedDogs.size()));
                owner.addDog(dog);
            }
            owners.addOwner(owner); // Assuming 'owners' is your collection for Owner objects
        }
    }

    private void run() {
        setup();
        runCommandLoop();
    }

    public static void main(String[] args) {
        new DogRegister().run();
    }
}
