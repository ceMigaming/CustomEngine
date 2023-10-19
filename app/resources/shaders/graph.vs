#version 330 core
#define Pi 3.1415926
layout(location = 0) in vec3 aPos;

uniform mat4 mvp;
uniform vec3 wavePos;
uniform float amplitude, phase, time, freq;
uniform int mode;
out vec4 color;
uniform vec4 baseColor;

void main() {
    float omega = 2.0 * Pi * freq;
    if(mode == 0) { // none
        color = baseColor.x == 0 ? vec4(0.5, 0.5, 0.5, 1.0) : baseColor;
        gl_Position = mvp * vec4(aPos, 1.0);
    } else if(mode == 1) { // spherical wave
        float r = length(aPos - wavePos / 10) * 10;
        float k = 2.0 * Pi / freq;
        float u = 0.1 * amplitude / r * cos(omega * time * 0.1 - k * r + phase);
        color = baseColor.x == 0 ? vec4(0.5 + 0.5 * u, 0.5 - 0.5 * u, 0.5, 1.0) : baseColor;
        gl_Position = mvp * vec4(aPos.xy, u, 1.0);
    } else if(mode == 2) { // plane wave
        color = baseColor.x == 0 ? vec4(aPos.xyz + .5 + sin(time) / 2, 0) : baseColor;
        gl_Position = mvp * vec4(aPos.xy, amplitude * 0.1 * sin(omega * time * 0.1 + 10 * aPos.x + 10 * aPos.y + phase), 1.0);
    } else {
        float r = length(aPos - wavePos / 10) * 10;
        float k = 2.0 * Pi / freq;
        float u = 0.1 * amplitude / r * cos(omega * time * 0.1 - k * r + phase);
        // spherical part
        color = baseColor.x == 0 ? vec4(0.5 + 0.5 * u, 0.5 - 0.5 * u, 0.5, 1.0) : baseColor;
        gl_Position = mvp * vec4(aPos.xy, u, 1.0);
        // plane part
        color += baseColor.x == 0 ? vec4(aPos.xyz - 0.25 + sin(time) / 2, 0) : baseColor;
        gl_Position += mvp * vec4(aPos.xy, amplitude * 0.1 * sin(omega * time * 0.1 + 10 * aPos.x + 10 * aPos.y + phase), 1.0);
    }
}