package dev.brighten.program;

import dev.brighten.program.controllers.Controller;
import dev.brighten.program.hash.CRC;
import dev.brighten.program.hash.GeneralHash;
import dev.brighten.program.utils.MathUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Run implements Serializable {
    public static Run INSTANCE;

    public void onEnable() {
        INSTANCE = this;
    }

    public String getCheckSum(String hashType) throws IOException {
        long nano = System.nanoTime();
        InputStream is = Files.newInputStream(Paths.get(Controller.INSTANCE.filePath.getText()));
        byte[] bytes = IOUtils.toByteArray(is);

        long inputStream = System.nanoTime() - nano;
        
        String toReturn;

        nano = System.nanoTime();

        switch(hashType) {
            case "SHA-1":
                toReturn = GeneralHash.getSHAHash(bytes, GeneralHash.SHAType.SHA1);
                break;
            case "SHA-256":
                toReturn = GeneralHash.getSHAHash(bytes, GeneralHash.SHAType.SHA256);
                break;
            case "MD5":
                toReturn = GeneralHash.getMD5Hash(bytes);
                break;
            case "CRC":
            default:
                toReturn = String.valueOf(CRC.checkSum(bytes));
                break;
        }



        long hash = System.nanoTime() - nano;
        double millis = hash / 1000000D;

        System.out.println("input=" + inputStream + "ns, hash=" + hash + "ns. Hash used:" + hashType
                + " hash-millis:" + millis);
        Controller.INSTANCE.completeTimeText.setText("Completed hash in " + MathUtils.round(millis, 5) + "ms!");
        Controller.INSTANCE.completeTimeText.setVisible(true);

        return toReturn;
    }
}
