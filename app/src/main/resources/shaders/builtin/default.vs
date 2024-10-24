#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 texCoord;

out vec3 fragPosition;
out vec3 fragNormal;
out vec2 fragTexCoord;

uniform mat4 model = mat4(1.0);
uniform mat4 view = mat4(1.0);
uniform mat4 projection = mat4(1.0);

void main() {
    mat3 normalMatrix = mat3(model);
    normalMatrix = inverse(normalMatrix);
    normalMatrix = transpose(normalMatrix);
    fragPosition = vec3(model * vec4(position, 1.0));
    fragNormal = normalize(normalMatrix * normal);
    fragTexCoord = texCoord;
    gl_Position = projection * view * model * vec4(position, 1.0);
}