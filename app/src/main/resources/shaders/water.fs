#version 330

uniform vec2 uResolution;
uniform float uTime;
uniform vec4 color;

out vec4 outColor;

// Redefine below to see the tiling...
// #define SHOW_TILING

#define TAU 6.28318530718
#define MAX_ITER 3

in vec4 pos;

void main() 
{
	vec2 fragCoord = vec2(gl_FragCoord);
	float time = uTime * .1+23.0;
    // uv should be the 0-1 uv of texture...
	vec2 uv = fragCoord.xy / uResolution.xy;
    vec2 p = mod(uv*TAU, TAU)-250.0;
	vec2 i = vec2(p);
	float c = 1.0;
	float inten = .005;

	for (int n = 0; n < MAX_ITER; n++) 
	{
		float t = time * (1.0 - (3.5 / float(n+1)));
		i = p + vec2(cos(t - i.x) + sin(t + i.y), sin(t - i.y) + cos(t + i.x));
		c += 1.0/length(vec2(p.x / (sin(i.x+t)/inten),p.y / (cos(i.y+t)/inten)));
	}
	c /= float(MAX_ITER);
	c = 1.17-pow(c, 1.4);
	vec3 finalColor = vec3(pow(abs(c), 8.0));
    finalColor = clamp(finalColor + vec3(color), 0.0, 1.0);
    
	outColor = vec4(finalColor, color.a);
}