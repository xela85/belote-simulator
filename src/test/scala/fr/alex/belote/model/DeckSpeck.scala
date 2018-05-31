package fr.alex.belote.model

import org.specs2.Specification

class DeckSpeck extends Specification {

  def is = s2"""

La sélection aléatoire des cartes de chaque joueur s'effectue par le biais de trois étapes, le but étant pour chaque
joueur de choisir d'être à l'atout ou non.

Lors de la première étape :

  Quatre tas doivent être constitués                                 $firstTakeHeaps
  Chaque tas doit contenir 5 cartes                                  $firstTakeCardsByHeap
  Le reste des cartes doit en contenir 12                            $firstTakeRest
  Toutes les cartes doivent être sur la table                        $firstTakeAll

Lors de la sélection de l'atout, une carte d'atout est choisie :
  Il doit alors rester 11 cartes                                     $atoutTakeRest
  Les 11 cartes et l'atout doivent être les mêmes qu'avant           $atoutTakeAll

Lors de la dernière étape
  Il n'est pas possible que le joueur d'index 4 existe               $lastTakeWrongIndex
  Le joueur d'atout doit avoir 2 cartes, les autres 3                $lastTakeCards
  L'addition des cartes des joueur doivent être les mêmes qu'avant   $lastTakeAll
"""

  private val All = Card.randomized(0)

  private val FirstTake = Deck.firstTake(All)

  def firstTakeHeaps = FirstTake.decks must have size 4

  def firstTakeCardsByHeap = forall(FirstTake.decks) { deck =>
    deck.cards must have size 5
  }

  def firstTakeRest = FirstTake.rest must have size 12

  def firstTakeAll =
    FirstTake.decks.flatMap(_.cards) ++ FirstTake.rest must containTheSameElementsAs(Card.all)


  private val AtoutTake = Deck.atoutTake(FirstTake)

  def atoutTakeRest = AtoutTake.rest must have size 11

  def atoutTakeAll = AtoutTake.atout :: AtoutTake.rest must containTheSameElementsAs(FirstTake.rest)

  private def lastTake(atoutIndex: Int) = Deck.lastTake(AtoutTake, atoutIndex)

  def lastTakeWrongIndex = lastTake(4) must throwA[IllegalArgumentException]

  def lastTakeCards = forall(0 until 4) { atoutIndex =>
    (lastTake(atoutIndex).decks(atoutIndex).cards must have size 2) and
      forall(lastTake(atoutIndex).decks.zipWithIndex.collect { case (deck, i) if i != atoutIndex => deck.cards }) { cards =>
        cards must have size 3
      }
  }

  def lastTakeAll = forall(0 until 4) { atoutIndex =>
    lastTake(atoutIndex).decks.flatMap(_.cards) must containTheSameElementsAs(AtoutTake.rest)
  }

}
