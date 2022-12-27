import input.Input;
import platform.PlatformGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;

public final class Main {

    private Main() {

    }

    /**
     * Main function that starts the execution of every test.
     * Reads the input, creates the output file and writes in it.
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
        File inputFile = new File(args[0]);
        File outputFile = new File("results.out");
        outputFile.createNewFile();

        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(inputFile, Input.class);

        ArrayNode output = objectMapper.createArrayNode();
        PlatformGenerator platform =  PlatformGenerator.getInstance();
        platform.startPage(inputData.getActions(),
                inputData.getUsers(), inputData.getMovies(), output);

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(outputFile, output);
    }
}
