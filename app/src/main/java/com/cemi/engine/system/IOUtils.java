package com.cemi.engine.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import com.cemi.engine.Engine;
import com.cemi.game.IOUtil;

public class IOUtils {

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

    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = resource.startsWith("http") ? null : Paths.get(resource);
        if (path != null && Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    ;
                }
            }
        } else {
            try (
                    InputStream source = resource.startsWith("http")
                            ? new URL(resource).openStream()
                            : IOUtil.class.getClassLoader().getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)) {
                buffer = BufferUtils.createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
                    }
                }
            }
        }

        buffer.flip();
        return MemoryUtil.memSlice(buffer);
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }
}
