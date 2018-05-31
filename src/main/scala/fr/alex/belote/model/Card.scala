package fr.alex.belote.model

import enumeratum.{Enum, EnumEntry}

import scala.util.Random


sealed trait Color extends EnumEntry

object Color extends Enum[Color] {

  def values = findValues

  case object Coeur extends Color

  case object Pique extends Color

  case object Carreau extends Color

  case object Trèfle extends Color

}


sealed abstract class Value(val atout: Int, val normal: Int) extends EnumEntry

object Value extends Enum[Value] {

  def values = findValues

  case object Roi extends Value(5, 4)

  case object Dame extends Value(3, 3)

  case object Valet extends Value(20, 2)

  case object Dix extends Value(10, 10)

  case object Neuf extends Value(14, 0)

  case object Huit extends Value(0, 0)

  case object Sept extends Value(0, 0)

  case object As extends Value(11, 11)

}

case class Card(color: Color, value: Value)

object Card {

  def all: List[Card] = (for {
    color <- Color.values
    value <- Value.values
  } yield Card(color, value)).toList

  def randomized(seed: Long = System.currentTimeMillis()): List[Card] = new Random(seed).shuffle(all)



}