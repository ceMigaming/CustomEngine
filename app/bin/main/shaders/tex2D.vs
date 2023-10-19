#version 330 core

in vec3 position;
in vec2 texCoordsIn;
out vec2 texCoords;

void main() {
    texCoords = texCoordsIn;
    gl_Position = vec4(position, 1.0);
}