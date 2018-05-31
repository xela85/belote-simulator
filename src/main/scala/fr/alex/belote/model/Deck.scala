package fr.alex.belote.model

case class Deck(cards: List[Card]) {
  def +(other: Deck) = Deck(cards ++ other.cards)
  def +(card: Card) = Deck(card :: cards)
}

case class FirstTake(decks: List[Deck], rest: List[Card])
case class AtoutTake(atout: Card, rest: List[Card])
case class LastTake(decks: List[Deck])

object Deck {


  def firstTake(cards: List[Card]): FirstTake = {
    require(cards.length == Card.all.length, "La pioche ne doit comporter que 32 cartes")
    val (first, firstRest) = cards.splitAt(Belote.Players * 3)
    val (second, rest) = firstRest.splitAt(Belote.Players * 2)
    val decks = first.grouped(3).toList.zip(second.grouped(2).toList).map { case (a, b) => a ++ b }.map(Deck.apply)
    FirstTake(decks, rest)
  }

  def atoutTake(cards: FirstTake): AtoutTake = {
    require(cards.rest.length == 12)
    AtoutTake(cards.rest.head, cards.rest.tail)
  }

  def lastTake(cards: AtoutTake, atoutChoserIndex: Int): LastTake = {
    require(atoutChoserIndex < Belote.Players)

    val toTake = (0 until Belote.Players) map { i =>
      if (atoutChoserIndex == i) 2
      else 3
    }

    def takeFromHeap(cards: List[Card], toTake: List[Int]): List[Deck] = toTake match {
      case number :: rest => Deck(cards.take(number)) :: takeFromHeap(cards.drop(number), rest)
      case Nil => Nil
    }

    LastTake(decks = takeFromHeap(cards.rest, toTake.toList))
  }

}