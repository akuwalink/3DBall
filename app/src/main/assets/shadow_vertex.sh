#version 300 es
uniform mat4 finalMatrix;
uniform mat4 modelMatrix;
in vec3 inPosition;
out vec4 outPosition;
void main(){
    gl_Position=finalMatrix*vec4(inPosition,1);
    outPosition=modelMatrix*vec4(inPosition,1);
}
