package net.slipp;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

public class MindMapVerticle extends Verticle {
	@Override
	public void start() {
		container.logger().info("MindMap Verticle deployed");
		
		final EventBus eventBus = vertx.eventBus();
		
		eventBus.registerHandler("mindMaps.list", new Handler<Message<String>>() {
			@Override
			public void handle(Message<String> message) {
				container.logger().info("message body : " + message.body());
				
				eventBus.send("mindMaps", "response");
			}
		});
	}
}
