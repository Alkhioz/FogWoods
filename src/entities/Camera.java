package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float distanceFromPlayer = 50;
	private float angleArroundPlayer = 0;
	
	
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch = 20;
	private float yaw;
	private float roll;
	
	private Player player;
	
	public Camera(Player player){
		this.player = player;
	}
	
	public void move(){
		calculateZoom();
		calculatePitch(); 
		calculateAngleArroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		if (verticalDistance <0 && horizontalDistance <0 ) {
			verticalDistance = 1;
			horizontalDistance = 1;
		}
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleArroundPlayer);
	}

	public void invertPitch(){
		this.pitch = -pitch;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = player.getRotY() + angleArroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance + 5;//el cinco es la altura del player
		
	}
	
	private float calculateHorizontalDistance() {
		return  (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	
	}
	
	private float calculateVerticalDistance() {
		return  (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
		
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange; 
			if(pitch > 90) {
				pitch = 90;
			}else if(pitch < 0) {
				pitch = 0;
			}
			
		}
	}
	
	private void calculateAngleArroundPlayer() {
		if(Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleArroundPlayer -= angleChange;
		}
	}

}
