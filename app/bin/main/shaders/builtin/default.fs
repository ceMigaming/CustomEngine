#version 330

out vec4 FragColor;

in vec2 fragTexCoord;
in vec3 fragNormal;
in vec3 fragPosition;

uniform vec3 color = vec3(1.0, 1.0, 1.0);

uniform float ambientIntensity = 0.1;
uniform vec3 ambientColor = vec3(1.0, 1.0, 1.0);

uniform vec3 lightPosition = vec3(0.0, 0.0, 0.0);
uniform vec3 lightColor = vec3(1.0, 1.0, 1.0);

uniform vec3 cameraPosition;
uniform float shininess = 32;
uniform float specularIntensity = 0.5;

uniform sampler2D texture1;

void main() {
    // texture
    vec4 texColor = texture(texture1, fragTexCoord);

    // ambient
    vec3 ambientResult = ambientIntensity * ambientColor;

    // diffuse
    vec3 n = normalize(fragNormal);
    vec3 dir = normalize(lightPosition - fragPosition);
    float diffuse = max(dot(n, dir), 0.0);
    vec3 diffuseResult = diffuse * lightColor;

    // specular
    vec3 viewDirection = normalize(cameraPosition - fragPosition);
    vec3 reflectDirection = reflect(-dir, n);
    float specular = pow(max(dot(viewDirection, reflectDirection), 0.0), shininess);
    vec3 specularResult = specular * specularIntensity * lightColor;

    vec3 result = (ambientResult + diffuseResult + specularResult) * color * texColor.rgb;
    FragColor = vec4(result, 1.0);

}