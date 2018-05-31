package fr.alex.belote

import akka.actor.{ActorSystem, Props}
import fr.alex.belote.actors.GameMaster

object MainApplication extends App {

  val system = ActorSystem("BeloteSimulator")

  val gameMaster = system.actorOf(Props(classOf[GameMaster]))

  gameMaster ! GameMaster.StartGame
  gameMaster ! GameMaster.FirstTakeStart
}
