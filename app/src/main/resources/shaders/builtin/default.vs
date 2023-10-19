#version 330

layout(location = 0)in vec4 vert;

uniform vec4 color;
out vec4 fragColor;

uniform mat4 model = mat4(1.0f);
uniform mat4 view = mat4(1.0f);
uniform mat4 projection = mat4(1.0f);

void main()
{
    fragColor = color;
    gl_Position = projection * view * model * vert;
}