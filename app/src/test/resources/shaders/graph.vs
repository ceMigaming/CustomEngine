#version 330 core
#define Pi 3.1415926
layout(location = 0) in vec3 aPos;

uniform mat4 mvp;
uniform vec3 wavePos;
uniform float rotation, amplitude, phase, time, freq;
uniform int mode;
out vec4 color;
uniform vec4 baseColor;

void main() {
    float omega = 2.0 * Pi * freq;
    float k = 2.0 * Pi / freq;
    if(mode == 0) { // none
        color = baseColor.x == 0 ? vec4(0.5, 0.5, 0.5, 1.0) : baseColor;
        gl_Position = mvp * vec4(aPos, 1.0);
    } else if(mode == 1) { // spherical wave
        float r = length(aPos - wavePos / 10) * 10;
        float u1 = 0.1 * amplitude / r * cos(omega * time * 0.1 - k * r + phase);
        float u2 = 0.1 * amplitude / r * sin(omega * time * 0.1 - k * r + phase);
        float u = max(min(atan(u2, u1), 1), -1);
        color = baseColor.x == 0 ? vec4(0.5 + 0.5 * u, 0.5 - 0.5 * u, 0.5, 1.0) : baseColor;
        gl_Position = mvp * vec4(aPos.xy, u, 1.0);
    } else if(mode == 2) { // plane wave
        color = baseColor.x == 0 ? vec4(aPos.xyz + .5 + sin(time) / 2, 0) : baseColor;
        gl_Position = mvp * vec4(aPos.xy, amplitude * 0.1 * cos(omega * time * 0.1 + 10 * k * cos(rotation) * aPos.x + 10 * k * sin(rotation) * aPos.y - freq + phase), 1.0);
    } else if(mode == 3) { // plane and spherical wave
        float r = length(aPos - wavePos / 10) * 10;
        float u1 = 0.1 * amplitude / r * cos(omega * time * 0.1 - k * r + phase);
        float u2 = 0.1 * amplitude / r * sin(omega * time * 0.1 - k * r + phase);
        float planeZ = amplitude * 0.1 * cos(omega * time * 0.1 + 10 * k * cos(0) * aPos.x + 10 * k * sin(0) * aPos.y - freq + phase);
        // spherical and plane sum
        float u = max(min(atan(u2, u1) + planeZ, 1), -1);
        // spherical part
        color = baseColor.x == 0 ? vec4(0.5 + 0.5 * u, 0.5 - 0.5 * u, 0.5, 1.0) : baseColor;
        gl_Position = mvp * vec4(aPos.xy, u, 1.0);
        // plane part
        color += baseColor.x == 0 ? vec4(aPos.xyz - 0.25 + sin(time) / 2, 0) : baseColor;
        //gl_Position += mvp * vec4(aPos.xy, , 1.0);
    } else if(mode == 6) { // two plane waves
        color = baseColor.x == 0 ? vec4(aPos.xyz + .5 + sin(time) / 2, 0) : baseColor;
        float re1 = amplitude * 0.1 * cos(omega * time * 0.1 + 10 * k * cos(0) * aPos.x + 10 * k * sin(0) * aPos.y - freq + phase);
        float re2 = amplitude * 0.1 * cos(omega * time * 0.1 + 10 * k * cos(rotation) * aPos.x + 10 * k * sin(rotation) * aPos.y - freq + phase);
        float im1 = amplitude * 0.1 * sin(omega * time * 0.1 + 10 * k * cos(0) * aPos.x + 10 * k * sin(0) * aPos.y - freq + phase);
        float im2 = amplitude * 0.1 * sin(omega * time * 0.1 + 10 * k * cos(rotation) * aPos.x + 10 * k * sin(rotation) * aPos.y - freq + phase);

        float y1 = re1 + re2;
        float y2 = im1 + im2;
        float y = sqrt(y1 * y1 + y2 * y2);
        y = y * y;
        gl_Position = mvp * vec4(aPos.xy, y, 1.0);
    } else if(mode == 7) { // all waves
        float r = length(aPos - wavePos / 10) * 10;
        float u1 = 0.1 * amplitude / r * cos(omega * time * 0.1 - k * r + phase);
        float u2 = 0.1 * amplitude / r * sin(omega * time * 0.1 - k * r + phase);
        float y1 = amplitude * 0.1 * cos(omega * time * 0.1 + 10 * k * cos(0) * aPos.x + 10 * k * sin(0) * aPos.y - freq + phase);
        float y2 = amplitude * 0.1 * cos(omega * time * 0.1 + 10 * k * cos(rotation) * aPos.x + 10 * k * sin(rotation) * aPos.y - freq + phase);

        // spherical and plane sum
        float u = max(min(atan(u2, u1) + y1 + y2, 1), -1);
        // spherical part
        color = baseColor.x == 0 ? vec4(0.5 + 0.5 * u, 0.5 - 0.5 * u, 0.5, 1.0) : baseColor;
        gl_Position = mvp * vec4(aPos.xy, u, 1.0);
        // plane part
        color += baseColor.x == 0 ? vec4(aPos.xyz - 0.25 + sin(time) / 2, 0) : baseColor;
        //gl_Position += mvp * vec4(aPos.xy, , 1.0);
    }

}