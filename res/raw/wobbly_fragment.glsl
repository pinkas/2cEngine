precision mediump float;
uniform float time;
uniform sampler2D u_texture;
varying vec2 v_texCoord;
void main() {
	    	            
	float height = 1.0 - v_texCoord.y;
	float offset = pow(height, 2.5);
	offset *= (sin(time * 3.0) * 0.2);
//	if ( (v_texCoord.x + offset) < 0.0 ){
//		offset = 0.0;
//	 }
  	vec3 normalColor = texture2D(u_texture, vec2(v_texCoord.x + offset, v_texCoord.y)).rgb;
  	gl_FragColor =  vec4(normalColor,1);
}