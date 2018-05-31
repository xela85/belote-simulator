package fr.alex.belote.actors

import akka.actor.{Actor, ActorLogging}
import fr.alex.belote.model.Deck

class Player extends Actor with ActorLogging {
  override def receive: Receive = {
    case deck: Deck =>
      log.info("{} received {}", context.self, deck)
  }
}
