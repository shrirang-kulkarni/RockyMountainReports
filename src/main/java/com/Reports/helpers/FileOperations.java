package com.Reports.helpers;

import java.io.File;
import java.io.IOException;

public class FileOperations {

    public File getFileHandler(String filePath ){
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("File created successfully.");
                } else {
                    System.out.println("File not created.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File already exists.");
        }
        return file;
    }
}
