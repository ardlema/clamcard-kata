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

  private def workOutCharge(card: ClamCard): Charge = {
    val originZone = Charger.getZoneFromStation(card.lastOrigin)
    val destinyZone = Charger.getZoneFromStation(card.lastDestiny)
    val overallPrice = workOutOverallPrice(card).charge
    val dayPrice = dailyPrice(originZone, destinyZone)
    val singleJourney = workOutSingleJourney(originZone, destinyZone, card)
    if (overallPrice > dayPrice) {
      Charge(
        card.uid,
        Math.max(findPeriodAndPricePerZone(originZone, "Day").get.price - (overallPrice - singleJourney.charge), 0))
    }
    else {
      singleJourney
    }
  }

  private def workOutSingleJourney(originZone: String, destinyZone: String, card: ClamCard): Charge = {
    if (originZone.equals(destinyZone)) {
      Charge(card.uid, findPeriodAndPricePerZone(originZone, "Single").get.price)
    } else {
      Charge(card.uid, findPeriodAndPricePerZone("B", "Single").get.price)
    }
  }

  private def workOutOverallPrice(card: ClamCard): Charge = {
    val total = card.journeys.foldLeft(0F)((s, journey) =>
      s + workOutSingleJourney(
        Charger.getZoneFromStation(journey.origin),
        Charger.getZoneFromStation(journey.destiny.get),
        card).charge)
    Charge(card.uid, total)
  }

  private def dailyPrice(originZone: String, destinyZone: String): Float = {
    (originZone, destinyZone) match {
      case ("A", "A") => findPeriodAndPricePerZone(originZone, "Day").get.price
      case _ => findPeriodAndPricePerZone("B", "Day").get.price
    }
  }

  private def findPeriodAndPricePerZone(zone: String, period: String): Option[PeriodAndPrice] = {
    Charger.prices(zone).find(p => p.period.equals(period))
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
}
