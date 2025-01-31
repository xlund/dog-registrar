// Erik Näslund erns6604
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class InputReader implements AutoCloseable {
    private static final ArrayList<InputStream> INPUT_STREAMS = new ArrayList<>();
    private final Scanner scanner;
    public InputReader() {
      this(System.in);
    }

    public InputReader(InputStream inputStream) {
        if (inputStreamIsInUse(inputStream))
            throw new IllegalStateException(Error.INPUT_ALREADY_IN_USE.getMessage());
        INPUT_STREAMS.add(inputStream);
        scanner = new Scanner(inputStream);
    }

    private boolean inputStreamIsInUse(InputStream inputStream) {
        return INPUT_STREAMS.contains(inputStream);
    }


    public double readDouble(Prompt prompt) {
        Printer.printMessage(prompt);

        double d = scanner.nextDouble();
        scanner.nextLine();
        return d;
    }
    public int readInt(Prompt prompt) {
        Printer.printMessage(prompt);
        int i = scanner.nextInt();
        scanner.nextLine();
        return i;
    }
    public String readString(Prompt prompt) {
        Printer.printMessage(prompt);
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
