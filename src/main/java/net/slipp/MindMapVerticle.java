package net.slipp;

import java.util.HashMap;
import java.util.Map;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import com.google.gson.Gson;

public class MindMapVerticle extends Verticle {
	private Map<Double, Node> mindMaps = new HashMap<Double, Node>();
	
	@Override
	public void start() {
		container.logger().info("MindMap Verticle deployed");
		
		vertx.eventBus().registerHandler("mindMaps.list", new Handler<Message<JsonObject>>() {
			@Override
			public void handle(Message<JsonObject> message) {
				container.logger().info("message body : " + message.body());
				
				Gson gson = new Gson();
				message.reply(gson.toJson(mindMaps.values()));
			}
		});

		
		vertx.eventBus().registerHandler("mindMaps.save", new Handler<Message<JsonObject>>() {
			@Override
			public void handle(Message<JsonObject> message) {
				JsonObject body = message.body();
				container.logger().info("message body : " + body);
				
				Number number = body.getNumber("id");
				if (number == null) {
					double id = Math.random();
					mindMaps.put(id, new Node(id, body.getString("name")));
					Gson gson = new Gson();
					message.reply(gson.toJson(mindMaps.get(id)));
				} else {
					double id = number.doubleValue();
					Gson gson = new Gson();
					message.reply(gson.toJson(mindMaps.get(id)));
				}
			}
		});
	}
}
