package main;

/**
 * @author Will Fantom
 */
public final class Debug {

    private static final boolean on = true;

    /**
     * Prints a message to console
     *
     * @param message
     * @param type
     */
    public static void print(String message, DebugType type) {
        String toPrint = "";
        switch (type) {
            case ERROR:
                toPrint += "<!> Error: ";
                break;
            case LOADED:
                toPrint += "<+> Loaded: ";
                break;
            case OTHER:
                toPrint += "<#> Debug Message: ";
                break;
        }
        toPrint += message;
        if (on) {
            System.out.println(toPrint);
        }
    }

}
