uniform mat4 uMVPMatrix;
attribute vec4 vPosition;
attribute vec2 a_texCoord;
attribute vec2 trajectory;
varying vec2 v_texCoord;
	            
void main() {
    vec4 blah = vPosition;
    blah.x = blah.x + trajectory.x;
    blah.y = blah.y + trajectory.y;

	gl_Position = uMVPMatrix * blah;
	v_texCoord = a_texCoord;
}