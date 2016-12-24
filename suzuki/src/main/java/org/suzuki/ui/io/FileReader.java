package org.suzuki.ui.io;

import org.suzuki.ui.io.exception.FileReadException;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {

    public static String read(String path) throws FileReadException {

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(path))) {

            StringBuilder sb = new StringBuilder();

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                sb.append(sCurrentLine);
            }

            return sb.toString();

        } catch (IOException e) {
            throw new FileReadException();
        }
    }

}
