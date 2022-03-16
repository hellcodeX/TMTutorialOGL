#version 400 core

in vec3 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform mat4 transormationMatrix;

void main(void) {

    gl_Position = transormationMatrix * vec4(position.xyz, 1.0);
    pass_textureCoords = textureCoords;
}