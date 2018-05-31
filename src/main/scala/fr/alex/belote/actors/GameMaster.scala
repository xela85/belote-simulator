package fr.alex.belote.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import fr.alex.belote.actors.GameMaster.{FirstTakeStart, StartGame}
import fr.alex.belote.model.{Card, Deck}

class GameMaster extends Actor with ActorLogging {

  def receive: Receive = waitingForStart

  def waitingForStart: Receive = {
    case StartGame =>
      val players = (0 until 4) map (_ => context.actorOf(Props(classOf[Player])))
      context.become(waitingForFirstTake(players.toList))
  }

  def waitingForFirstTake(players: List[ActorRef]): Receive = {
    case FirstTakeStart =>
      val take = Deck.firstTake(Card.randomized())
      players.zip(take.decks).foreach { case (actor, deck) => actor ! deck }

  }



}

object GameMaster {

  case object StartGame
  case object FirstTakeStart

}
