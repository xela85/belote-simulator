package fr.alex.belote.model

import fr.alex.belote.model.Value._
import org.specs2.{ScalaCheck, Specification}

class CardSpec extends Specification with ScalaCheck {

  def is = s2"""

La belote est un jeu se jouant à 32 cartes, toutes composées d'une des quatre couleurs (Coeur, Carreau, Pique, Trèfle)
et des 8 valeurs. Chaque valeur apporte ses points spécifiques, que ce soit à l'atout et ou non.

  Au départ, la pioche doit comporter 32 cartes $totalCards
  La carte la plus forte à l'atout est le valet, les moins fortes le 8 et le 7    $atoutExtremums
  La carte la plus forte non atout est l'as, les moins fortes le 8 et le 7        $normalExtremums
  Chaque pioche doit contenir l'intégralité des cartes du jeu                     $randomized
    """


  def totalCards = Card.all must have size 32

  def atoutExtremums = (Value.values.maxBy(_.atout) must_== Valet) and
    (Value.values.groupBy(_.atout).minBy(_._1)._2 must containTheSameElementsAs(List(Sept, Huit)))

  def normalExtremums = (Value.values.maxBy(_.normal) must_== As) and
    (Value.values.groupBy(_.normal).minBy(_._1)._2 must containTheSameElementsAs(List(Sept, Huit, Neuf)))

  def randomized =
    prop((seed: Long) => Card.randomized(seed) must containTheSameElementsAs(Card.all))

}
