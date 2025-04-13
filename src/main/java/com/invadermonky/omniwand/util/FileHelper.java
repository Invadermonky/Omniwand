package com.invadermonky.omniwand.util;

import com.invadermonky.omniwand.Omniwand;
import cpw.mods.fml.common.Loader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;

public class FileHelper {
    public static void copyConfigFromJar(String source, final Path target) throws URISyntaxException, IOException {
        Path configPath = Paths.get(Omniwand.class.getResource("/data/omniwand.json").toURI());
        ClassLoader loader = Omniwand.class.getClassLoader();
        FileSystem fileSystem = FileSystems.newFileSystem(configPath, Omniwand.class.getClassLoader());

        final Path jarPath = fileSystem.getPath(source);

        Files.walkFileTree(jarPath, new SimpleFileVisitor<Path>() {
            private Path currentTarget;

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                currentTarget = target.resolve(jarPath.relativize(dir).toString());
                Files.createDirectories(currentTarget);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, target.resolve(jarPath.relativize(file).toString()), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });

        fileSystem.close();
    }

    /**
     * Walks through the specified toolFolder config folder and reads all .json file contents.
     *
     * @param toolFolder the config tool folder
     * @return map containing all json file names and file contents
     */
    public static HashMap<String, String> getToolConfigs(String toolFolder) {
        final HashMap<String, String> fileContents = new HashMap<>();
        final Path configPath = Paths.get(Loader.instance().getConfigDir().getAbsolutePath(), Omniwand.MOD_ID, toolFolder);
        try {
            Files.walkFileTree(configPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, @NotNull BasicFileAttributes attrs) {
                    if (dir.toFile().isDirectory() && !dir.equals(configPath))
                        return FileVisitResult.SKIP_SUBTREE;
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (Files.probeContentType(file) != null && Files.probeContentType(file).contains("json")) {
                        fileContents.put(file.getFileName().toString(), getFileContents(file));
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileContents;
    }

    /**
     * Reads a passed file and returns the contents as a string.
     *
     * @param path the path of the file to read
     * @return string containing the file contents
     */
    public static String getFileContents(Path path) throws IOException {
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
