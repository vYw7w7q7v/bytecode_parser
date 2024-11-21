package bytecode_parser;

import bytecode_parser.parser.BytecodeParser;

import java.nio.file.Path;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        BytecodeParser.parseJavaBytecode(Path.of("src/main/java/Main.class").toFile());
    }
}
