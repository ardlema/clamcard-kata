import scala.collection.immutable.Iterable
import scala.collection.mutable.ListBuffer

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

  def topIn(card: ClamCard, origin: String): Unit = card.addOrigin(origin)

  def topOut(card: ClamCard, destiny: String): Unit = {
    card.addDestiny(destiny)
    card.charge()
  }

  def workOutCharge(card: ClamCard): Float = {
    val originZone = Charger.getZoneFromStation(card.lastOrigin)
    val destinyZone = Charger.getZoneFromStation(card.lastDestiny)
    if (originZone.equals(destinyZone)) {
      Charger.prices(originZone).find(p => p.period.equals("Single")).get.price
    } else {
      Charger.prices("B").find(p => p.period.equals("Single")).get.price
    }
  }
}

case class Journey(origin: String, destiny: Option[String] = None, charge: Float = 0)

case class ClamCard(uid: String, journeys: ListBuffer[Journey] = ListBuffer()) { self =>

  def addOrigin(origin: String): ClamCard = self.copy(journeys = Journey(origin) +=: journeys)

  def addDestiny(destiny: String): Unit = journeys.update(0, Journey(journeys.head.origin, Some(destiny)))

  def lastOrigin(): String = journeys(0).origin

  def lastDestiny(): String = journeys(0).destiny.getOrElse("")

  def charge(): Unit = {
    val journey = journeys(0)
    val originZone = Charger.getZoneFromStation(journey.origin)
    val destinyZone = Charger.getZoneFromStation(journey.destiny.get)
    if (originZone.equals(destinyZone)) {
      journeys.update(0, journeys(0).copy(charge = Charger.prices(originZone).find(p => p.period.equals("Single")).get.price))
    } else {
      journeys.update(0, journeys(0).copy(charge = Charger.prices("B").find(p => p.period.equals("Single")).get.price))
    }
  }
}

case class PeriodAndPrice(period: String, price: Float)