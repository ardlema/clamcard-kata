import scala.collection.immutable.Iterable
import scala.collection.mutable.ListBuffer

case class PeriodAndPrice(period: String, price: Float)

case class Charge(cardId: String, charge: Float)

object Charger {
  val zones = Map("A" -> List("Asterisk", "Aldgate"), "B" -> List("Barbican", "Balham"))
  val prices = Map("A" ->
    List(
      PeriodAndPrice("Single", 2.5F),
      PeriodAndPrice("Day", 7.0F)),
    "B" ->
      List(
        PeriodAndPrice("Single", 3.0F),
        PeriodAndPrice("Day", 8.0F))
  )


  def getZoneFromStation(origin: String): String = {
    val zonesFounded: Iterable[String] = zones.filter(_._2.contains(origin)).map(e => e._1)
    zonesFounded.head
  }

  def topIn(card: ClamCard, origin: String): ClamCard = card.addOrigin(origin)

  def topOut(card: ClamCard, destiny: String): (ClamCard, Charge) = {
    val cardWithDestiny = card.addDestiny(destiny)
    (cardWithDestiny, workOutCharge(cardWithDestiny))
  }

  def workOutCharge(card: ClamCard): Charge = {
    val originZone = Charger.getZoneFromStation(card.lastOrigin)
    val destinyZone = Charger.getZoneFromStation(card.lastDestiny)
    val overallPrice = workOutOverallPrice(card).charge
    if (overallPrice > dailyPrice(card.lastOrigin(), card.lastDestiny())) {
      Charge(card.uid, overallPrice - Charger.prices(originZone).find(p => p.period.equals("Day")).get.price)
    }
    else {
      workOutSingleJourney(originZone, destinyZone, card)
    }
  }

  private def workOutSingleJourney(originZone: String, destinyZone: String, card: ClamCard): Charge = {
    if (originZone.equals(destinyZone)) {
      Charge(card.uid, Charger.prices(originZone).find(p => p.period.equals("Single")).get.price)
    } else {
      Charge(card.uid, Charger.prices("B").find(p => p.period.equals("Single")).get.price)
    }
  }

  private def workOutOverallPrice(card: ClamCard): Charge = {
    val total = card.journeys.foldLeft(0F)((s, journey) =>
      s + workOutSingleJourney(journey.origin, journey.destiny.get, card).charge)
    Charge(card.uid, total)
  }

  private def dailyPrice(originZone: String, destinyZone: String): Float = {
    (originZone, destinyZone) match {
      case ("A", "A") => Charger.prices(originZone).find(p => p.period.equals("Day")).get.price
      case _ => Charger.prices("B").find(p => p.period.equals("Day")).get.price
    }
  }
}

case class Journey(origin: String, destiny: Option[String] = None)

case class ClamCard(uid: String, journeys: ListBuffer[Journey] = ListBuffer()) { self =>

  def addOrigin(origin: String): ClamCard = self.copy(journeys = Journey(origin) +=: journeys)

  def addDestiny(destiny: String): ClamCard = {
    val newJourney = Journey(journeys.head.origin, Some(destiny))
    self.journeys.update(0, newJourney)
    self
  }

  def lastOrigin(): String = journeys(0).origin

  def lastDestiny(): String = journeys(0).destiny.getOrElse("")

/*  def charge(): Unit = {
    val journey = journeys(0)
    val originZone = Charger.getZoneFromStation(journey.origin)
    val destinyZone = Charger.getZoneFromStation(journey.destiny.get)
    if (originZone.equals(destinyZone)) {
      journeys.update(0, journeys(0).copy(charge = Charger.prices(originZone).find(p => p.period.equals("Single")).get.price))
    } else {
      journeys.update(0, journeys(0).copy(charge = Charger.prices("B").find(p => p.period.equals("Single")).get.price))
    }
  }*/
}
