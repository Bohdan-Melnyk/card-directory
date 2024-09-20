package com.example.pryvatbank.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    public static byte[] createTestZipFile(Path path) throws IOException {
        File tempZip = File.createTempFile("test", ".zip", path.toFile());
        try (ZipOutputStream zipOutputStream = new java.util.zip.ZipOutputStream(Files.newOutputStream(tempZip.toPath()))) {
            ZipEntry zipEntry = new java.util.zip.ZipEntry("test.json");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(CardCommonUtil.convertCardTempListToJson().getBytes());
            zipOutputStream.closeEntry();
        }
        return Files.readAllBytes(tempZip.toPath());
    }

    public static byte[] createTestZipFileWithInvalidJson(Path path) throws IOException {
        File tempZip = File.createTempFile("invalid", ".zip", path.toFile());
        try (ZipOutputStream zipOutputStream = new java.util.zip.ZipOutputStream(Files.newOutputStream(tempZip.toPath()))) {
            ZipEntry zipEntry = new java.util.zip.ZipEntry("test.json");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write("INVALID_JSON".getBytes());
            zipOutputStream.closeEntry();
        }
        return Files.readAllBytes(tempZip.toPath());
    }
}
