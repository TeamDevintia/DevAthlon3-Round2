package io.github.teamdevintia.round2.serverwrapper;

import io.github.teamdevintia.round2.serverwrapper.server.ServerWrapper;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

/**
 * @author MiniDigger
 */
@Log
public class FileUtil {

    public static final File BUNGEE = new File(ServerWrapper.getInstance().getJarManager().getRepo(), "bungee.jar");
    public static final File BUKKIT = new File(ServerWrapper.getInstance().getJarManager().getRepo(), "bukkit.jar");
    public static final File SERVER = new File(ServerWrapper.getInstance().getJarManager().getRepo(), "server");

    /**
     * Copies a file to a dir
     *
     * @param jar  the file to copy
     * @param dest the destination directory
     * @return whether or not this action was successful
     */
    public static boolean copyPlugin(File jar, File dest) {
        try {
            Files.copy(jar.toPath(), new File(dest, jar.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            log.log(Level.WARNING, "Could not copy file from " + jar.getAbsolutePath() + " to " + dest.getAbsolutePath(), e);
            return false;
        }
    }

    /**
     * Copies the content of a dir to a dir
     *
     * @param dir  the dir which content should be copied
     * @param dest the destination directory
     * @return whether or not this action was successful
     */
    public static boolean copyDir(File dir, File dest) {
        try {
            for (File f : dir.listFiles()) {
                if (f.isDirectory()) {
                    FileUtils.copyDirectory(f, new File(dest, f.getName()));
                } else {
                    Files.copy(f.toPath(), new File(dest, f.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
            return true;
        } catch (IOException e) {
            log.log(Level.WARNING, "Could not copy dir from " + dir.getAbsolutePath() + " to " + dest.getAbsolutePath(), e);
            return false;
        }
    }

    /**
     * Fully deletes a file or folder
     *
     * @param path the path that should be deleted
     */
    public static void deleteFileOrFolder(final Path path) {
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                        throws IOException {
                    Files.delete(file);
                    return CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(final Path file, final IOException e) {
                    return handleException(e);
                }

                private FileVisitResult handleException(final IOException e) {
                    log.log(Level.WARNING, "Error while deleting path " + path.toString(), e);
                    return TERMINATE;
                }

                @Override
                public FileVisitResult postVisitDirectory(final Path dir, final IOException e)
                        throws IOException {
                    if (e != null) return handleException(e);
                    Files.delete(dir);
                    return CONTINUE;
                }
            });
        } catch (IOException e) {
            log.log(Level.WARNING, "Error while deleting path " + path.toString(), e);
        }
    }
}
