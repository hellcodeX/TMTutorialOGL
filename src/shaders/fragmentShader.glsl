#version 400 core

in vec2 pass_textureCoords;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main() {

    // texture() - is a GLSL function
    out_Color = texture(textureSampler, pass_textureCoords);
}