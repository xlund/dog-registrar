public class Printer {
     public static void printHeader(String... headers) {
        for (String header : headers) {
            printColumn(header);
        }
        System.out.println();
        printRowBreak(headers.length);
    }

    public static void printMessage(MessageProvider msg, Object... objects) {
        System.out.print(msg.getMessage(objects));
    }

    public static void welcomeMessage() {
         System.out.print("""
                ========================
                `Dog register`
                ========================
                A tool to keep track
                of dog owners and their
                dogs. You can even track
                the dogs age!
                
                We hope you find this
                tool useful and that
                you share it with your
                dog friends and fam.
                
                -- Erik.
                ========================
                
                """);
    }

    public static void printRow(Object... values) {
        for (Object value : values) {
            printColumn(value);
        }
        System.out.println(); // Move to the next line after printing the row
    }


    private static void printColumn(Object value) {
        ColumnFormat format = ColumnFormat.fromObject(value);
        System.out.printf(format.getFormat(), value);
    }

    private static void printRowBreak(int colCount) {
        System.out.println(ColumnFormat.getSeparator().repeat(colCount * ColumnFormat.getWidth()));
    }

}


enum ColumnFormat {
    STRING("s"),
    INT("d"),
    FLOAT(".1f");

    private static final int COL_WIDTH = 24;
    private static final String SEPARATOR = "•";
    private final String formatType;

    ColumnFormat(String formatType) {
        this.formatType = formatType;
    }

    public static int getWidth() {
        return COL_WIDTH;
    }

    public static String getSeparator() {
        return SEPARATOR;
    }

    public static ColumnFormat fromObject(Object value) {
        switch (value) {
            case Integer _ -> {
                return ColumnFormat.INT;
            }
            case Float _, Double _ -> {
                return ColumnFormat.FLOAT;
            }
            default -> {
                return ColumnFormat.STRING;
            }
        }
    }


    public String getFormat() {
        return "%-" + COL_WIDTH + formatType;
    }
}