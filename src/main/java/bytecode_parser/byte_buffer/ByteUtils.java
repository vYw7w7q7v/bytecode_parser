package bytecode_parser.byte_buffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HexFormat;
import java.util.Locale;

public class ByteUtils {

    private ByteUtils() {}

    public static ByteBuffer readBytesFromFile(File file) {
        try(InputStream fileInputStream = new FileInputStream(file)) {
            return new ByteBuffer(fileInputStream.readAllBytes());
        } catch (IOException ioException) {
            throw new RuntimeException("readBytecodeFromFile ex!!");
        }
    }

    private static final HexFormat emptyDelimiterHexFormat = HexFormat.of().withDelimiter("");

    public static String bytesAsString(byte[] bytes) {
        return emptyDelimiterHexFormat.formatHex(bytes).toUpperCase(Locale.ROOT);
    }

    public static int asNumber(String byteString) {
        return HexFormat.fromHexDigits(byteString);
    }

}
