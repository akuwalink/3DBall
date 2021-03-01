#version 300 es
uniform mat4 finalMatrix;
uniform mat4 modelMatrix;
uniform vec3 lightLocation;
uniform vec3 cameraLocation;
uniform int lightMode;
in vec3 inPosition;
in vec3 inNormal;
in vec2 inTex;
in vec4 inAmbient;
in vec4 inDiffuse;
in vec4 inSpecular;
in float inShininess;


out vec4 outAmbient;
out vec4 outDiffuse;
out vec4 outSpecular;
out vec2 outTex;
out vec4 outPosition;

void directionalLight(
  in vec3 normal,
  inout vec4 ambient,
  inout vec4 diffuse,
  inout vec4 specular,
  in vec3 lightDirection,
  in vec4 lightAmbient,
  in vec4 lightDiffuse,
  in vec4 lightSpecular,
  float shininess
){
  ambient=lightAmbient;
  vec3 normalTarget=inPosition+normal;
  vec3 newNormal=(finalMatrix*vec4(normalTarget,1)).xyz-(finalMatrix*vec4(inPosition,1)).xyz;
  newNormal=normalize(newNormal);
  vec3 eye= normalize(cameraLocation-(finalMatrix*vec4(inPosition,1)).xyz);
  vec3 vp= normalize(lightDirection);
  vec3 halfVector=normalize(vp+eye);
  float nDotViewPosition=max(0.0,dot(newNormal,vp));
  diffuse=lightDiffuse*nDotViewPosition;
  float nDotViewHalfVector=dot(newNormal,halfVector);
  float powerFactor=max(0.0,pow(nDotViewHalfVector,shininess));
  specular=lightSpecular*powerFactor;
}

void pointLight(
  in vec3 normal,
  inout vec4 ambient,
  inout vec4 diffuse,
  inout vec4 specular,
  in vec3 lightDirection,
  in vec4 lightAmbient,
  in vec4 lightDiffuse,
  in vec4 lightSpecular,
  float shininess
){
  ambient=lightAmbient;
  vec3 normalTarget=inPosition+normal;
  vec3 newNormal=(finalMatrix*vec4(normalTarget,1)).xyz-(finalMatrix*vec4(inPosition,1)).xyz;
  newNormal=normalize(newNormal);
  vec3 eye= normalize(cameraLocation-(finalMatrix*vec4(inPosition,1)).xyz);
  vec3 vp= normalize(lightDirection-(finalMatrix*vec4(inPosition,1)).xyz);
  vec3 halfVector=normalize(vp+eye);
  float nDotViewPosition=max(0.0,dot(newNormal,vp));
  diffuse=lightDiffuse*nDotViewPosition;
  float nDotViewHalfVector=dot(newNormal,halfVector);
  float powerFactor=max(0.0,pow(nDotViewHalfVector,shininess));
  specular=lightSpecular*powerFactor;
}
void main(){
   gl_Position = finalMatrix * vec4(inPosition,1);
   vec4 ambientTemp,diffuseTemp,specularTemp;
   if(lightMode==0){
        directionalLight(normalize(inNormal),ambientTemp,diffuseTemp,specularTemp,lightLocation,inAmbient,inDiffuse,inSpecular,inShininess);
   }else{
        pointLight(normalize(inNormal),ambientTemp,diffuseTemp,specularTemp,lightLocation,inAmbient,inDiffuse,inSpecular,inShininess);
   }
   outAmbient=ambientTemp;
   outDiffuse=diffuseTemp;
   outSpecular=specularTemp;
   outTex=inTex;
}

