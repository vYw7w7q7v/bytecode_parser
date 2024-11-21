package byte_code_parser.bytes;

import java.util.*;

public class ByteBuffer {
    private final byte[] bytes;
    private int currIndex;

    public ByteBuffer(byte[] bytes) {
        this.bytes = bytes;
        currIndex = 0;
    }

    public void reset() {
        currIndex = 0;
    }

    public byte[] readBytes(int bytesCount) {
        if (bytesCount <= 0 || bytesCount >= bytes.length) throw new IllegalArgumentException("invalid bytesCount");
        currIndex += bytesCount;
        return Arrays.copyOfRange(bytes, currIndex - bytesCount, currIndex);
    }

}
