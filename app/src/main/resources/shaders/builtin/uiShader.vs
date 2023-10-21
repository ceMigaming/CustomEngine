#version 330 core

layout(location = 0)in vec4 vert;

in vec2 texCoordsIn;
out vec2 texCoords;

uniform mat4 model = mat4(1.0f);
uniform mat4 view = mat4(1.0f);
uniform mat4 projection = mat4(1.0f);

void main() {
    texCoords = texCoordsIn;
    gl_Position = projection * view * model * vert;
}