package com.cemi.engine.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL30;

import com.cemi.engine.Engine;

public class FileUtils {

    public static byte[] loadBytes(String path) throws IOException {
        File byteFile = new File(path);
        FileInputStream fileInputStream = new FileInputStream(byteFile);
        byte[] data = new byte[(int) byteFile.length()];

        fileInputStream.read(data);
        fileInputStream.close();

        return data;
    }

    public static String loadFileAsString(String path) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(Engine.class.getResourceAsStream(path)));
        String content = br.lines().collect(Collectors.joining("\n"));
        br.close();
        return content;
    }

    public static List<String> getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<>();
        try (InputStream in = getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;

            while ((resource = br.readLine()) != null) {
                filenames.add(resource);
            }
        }

        return filenames;
    }

    public static InputStream getResourceAsStream(String resource) {
        final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);

        return in == null ? Engine.class.getResourceAsStream(resource) : in;
    }

    public static int createShader(String shaderName) {
        int program = GL30.glCreateProgram();
        int vertexShader = createShader(shaderName + ".vs", GL30.GL_VERTEX_SHADER);
        int fragmentShader = createShader(shaderName + ".fs", GL30.GL_FRAGMENT_SHADER);
        GL30.glAttachShader(program, vertexShader);
        GL30.glAttachShader(program, fragmentShader);
        GL30.glLinkProgram(program);
        GL30.glValidateProgram(program);
        GL30.glDeleteShader(vertexShader);
        GL30.glDeleteShader(fragmentShader);
        return program;
    }

    private static int createShader(String shaderName, int type) {
        String shaderSrc = "";
        try {
            shaderSrc = loadFileAsString("/shaders/" + shaderName);
        } catch (IOException e) {
            System.err.println("Error reading shader file: " + shaderName);
            e.printStackTrace();
            System.exit(1);
        }
        int shader = GL30.glCreateShader(type);
        GL30.glShaderSource(shader, shaderSrc);
        GL30.glCompileShader(shader);

        if (GL30.glGetShaderi(shader, GL30.GL_COMPILE_STATUS) != GL30.GL_TRUE) {
            throw new RuntimeException(GL30.glGetShaderInfoLog(shader));
        }
        return shader;
    }
}
