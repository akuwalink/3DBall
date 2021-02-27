#version 300 es
uniform mat4 matrix;
in vec3 position;
in vec4 acolor;
out vec4 color;

void main(){
    gl_Position=matrix*vec4(position,1);
    color=acolor;
}
