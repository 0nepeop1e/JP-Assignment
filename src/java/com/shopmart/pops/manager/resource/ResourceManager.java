package com.shopmart.pops.manager.resource;

import javafx.scene.image.Image;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * A manager to manage resources.
 */
public class ResourceManager {

    /**
     * Read a text resources file with UTF-8 charset.
     * @param path the path
     * @return the content
     * @throws IOException when failed to read file
     */
    public static String readResource(String path) throws IOException {
        return readResource(path, Charset.forName("UTF-8"));
    }
    /**
     * Read a text resources file with specific charset.
     * @param path the path
     * @param cs the charset
     * @return the content
     * @throws IOException when failed to read file
     */
    public static String readResource(String path, Charset cs) throws IOException {
        URL url = getURL(path);
        List<String> lines = Files.readAllLines(
                Paths.get(url.toExternalForm()), cs);
        return String.join(System.lineSeparator(), lines);
    }

    /**
     * Load an image from resource
     * @param path the path
     * @return the image
     */
    public static Image getImage(String path){
        URL url = getURL(path);
        return new Image(url.toExternalForm(), 64, 64, true, true);
    }

    /**
     * Get the URL of a resource.
     * @param path the path
     * @return the URL
     */
    public static URL getURL(String path){
        return ResourceManager.class.getResource(path);
    }
}
