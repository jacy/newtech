package com.nt.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Test extends AbstractActor {

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(String.class, s -> System.out.println(">>>> " + s)).matchAny(any -> System.out.println(">>> unknown msg")).build();
	}

	public static void main(String[] args) {
		final ActorSystem actorSystem = ActorSystem.create("test");
		Props props = Props.create(Test.class);
		ActorRef actor = actorSystem.actorOf(props, "1");
		ActorRef actor2 = actorSystem.actorOf(props, "2");
		actor.tell("hello world", actor2);
		actor.tell(2, actor2);

		new Thread(() -> {
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			actorSystem.actorSelection("akka://test/user/1").tell("send from actor select", null);

		}).start();
		System.out.println(actor.path());

	}

}
