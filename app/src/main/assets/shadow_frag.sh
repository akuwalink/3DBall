#version 300 es
precision highp float;
uniform highp vec3 lightLocation;
in vec4 outPosition;
out float fragColor;
void main(){
    float dis=distance(outPosition.xyz,lightLocation);
    fragColor=dis;
}

