package net.slipp;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class MindMapVerticle extends Verticle {
	@Override
	public void start() {
		container.logger().info("MindMap Verticle deployed");
		
		vertx.eventBus().registerHandler("mindMaps.list", new Handler<Message<JsonObject>>() {
			@Override
			public void handle(Message<JsonObject> message) {
				container.logger().info("message body : " + message.body());
				
				message.reply(new JsonObject());
			}
		});
	}
}
