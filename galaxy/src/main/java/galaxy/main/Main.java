package galaxy.main;

import java.io.IOException;

/**
 * Starting point of the program.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.out.println("Please pass the fully qualified path of the instruction set as program argument.");
            System.exit(0);
        }
        String filePath = args[0];

        GalaxyExecutor executor = new GalaxyExecutor(filePath);
        executor.execute();
    }
}
