#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 out_Color;

// TODO: rename textureSampler to modelTexture;
uniform sampler2D textureSampler;
uniform vec3 lightColour;

void main() {

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);

    float nDot1 = dot(unitNormal, unitLightVector);
    float bightness = max(nDot1, 0.0);
    vec3 diffuse = bightness * lightColour;

    // texture() - is a GLSL function
    out_Color = vec4(diffuse, 1.0) * texture(textureSampler, pass_textureCoords);
}