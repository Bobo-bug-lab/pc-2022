package lamp_thing.consumer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class VanillaLightSensorDeviceThingConsumerAgent extends AbstractVerticle {

	
	private LightSensorThingAPI thing;
	private int nEventsReceived;
	
	public VanillaLightSensorDeviceThingConsumerAgent(LightSensorThingAPI thing) {
		this.thing = thing; 
		nEventsReceived = 0;
	}
	
	/**
	 * Main agent body.
	 */
	public void start(Promise<Void> startPromise) throws Exception {
		log("Lamp consumer agent started.");		
		
		log("Getting the status...");		
		Future<String> getStateRes = thing.getState();

		Future<Void> switchOnRes = getStateRes.compose(res -> {
			log("Status: " + res);			
			log("Switching on");
			return thing.on();
		}).onFailure(err -> {
			log("Failure " + err);
		});
		
		Future<Void> subscribeRes = switchOnRes.compose(res2 -> {
			log("Action done. "); 				
			log("Subscribing...");
			return thing.subscribe(this::onNewEvent);
		});

		subscribeRes.onComplete(res3 -> {
			log("Subscribed!");
		});		
	}
	
	
	/**
	 * Handler to process observed events  
	 */
	protected void onNewEvent(JsonObject ev) {
		nEventsReceived++;
		log("New event: \n " + ev.toString() + "\nNum events received: " + nEventsReceived);
	}
	
	protected void log(String msg) {
		System.out.println("[LampThingConsumerAgent]["+System.currentTimeMillis()+"] " + msg);
	}
	
}
