package com.cemi.engine.render.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cemi.engine.render.Mesh;
import com.cemi.engine.system.IOUtils;

public class OBJLoader {
    private final static Pattern vertexPattern = Pattern.compile("(v (-?[.\\d]+ -?[.\\d]+ -?[.\\d]+))",
            Pattern.CASE_INSENSITIVE);
    private final static Pattern normalPattern = Pattern.compile("(vn (-?[.\\d]+ -?[.\\d]+ -?[.\\d]+))",
            Pattern.CASE_INSENSITIVE);
    private final static Pattern texturePattern = Pattern.compile("(vt (-?[.\\d]+ -?[.\\d]+))",
            Pattern.CASE_INSENSITIVE);
    private final static Pattern facePattern = Pattern
            .compile("(f (\\d+\\/\\d+\\/\\d+ \\d+\\/\\d+\\/\\d+ \\d+\\/\\d+\\/\\d+))", Pattern.CASE_INSENSITIVE);

    public static Mesh load(String path) {
        String fileContent;
        try {
            fileContent = IOUtils.loadFileAsString(path);
        } catch (IOException e) {
            System.out.println("Failed to load file: " + path);
            e.printStackTrace();
            return null;
        }

        Matcher vertexMatcher = vertexPattern.matcher(fileContent);
        Matcher normalMatcher = normalPattern.matcher(fileContent);
        Matcher textureMatcher = texturePattern.matcher(fileContent);
        Matcher faceMatcher = facePattern.matcher(fileContent);

        if (!vertexMatcher.find() || !normalMatcher.find() || !textureMatcher.find() || !faceMatcher.find()) {
            System.out.println("Failed to find vertex, normal, texture or face data in file: " + path);
            return null;
        }

        String[] vertices = vertexMatcher.group(2).split(" ");
        String[] normals = normalMatcher.group(2).split(" ");
        String[] textures = textureMatcher.group(2).split(" ");
        String[] faces = faceMatcher.group(2).split(" ");

        Mesh.Builder builder = new Mesh.Builder();
        
        return null;
    }
}
