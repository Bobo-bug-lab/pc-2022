/**
 * Simulator/mock for a light device
 * 
 */
package lamp_thing.impl;

public class LampDeviceSimulator implements LightSensorDevice {

	private LightSensorDeviceSimFrame frame;
	private String lightID;
	
	public LampDeviceSimulator(String lightID){
		this.lightID = lightID;
	}
	
	public void init() {
		frame = new LightSensorDeviceSimFrame(lightID);
		frame.display();
	}
	
	@Override
	public void on() {
		frame.setOn(true);	
	}

	@Override
	public void off() {
		frame.setOn(false);	
	}
}
