#version 300 es
precision mediump float;
in vec2 outTex;
in vec4 outAmbient;
in vec4 outDiffuse;
in vec4 outSpecular;
in vec4 outPosition;
uniform sampler2D sTexture;
out vec4 fragColor;
void main(){
    vec4 color=texture(sTexture,outTex);
    fragColor=color*(outAmbient+outDiffuse+outSpecular);

}
