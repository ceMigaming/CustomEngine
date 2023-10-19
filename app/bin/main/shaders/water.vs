#version 330

layout(location = 0)in vec4 vert;

uniform mat4 model = mat4(1.0f);
uniform mat4 view = mat4(1.0f);
uniform mat4 projection = mat4(1.0f);

out vec4 pos;

void main()
{
    pos = gl_Position = projection * view * model * vert;
}