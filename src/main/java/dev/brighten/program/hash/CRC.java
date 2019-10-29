package dev.brighten.program.hash;

import java.util.zip.CRC32;

public class CRC {
    private static CRC32 crc32 = new CRC32();

    public static long checkSum(byte[] bytes) {
        crc32.update(bytes);

        return crc32.getValue();
    }
}
