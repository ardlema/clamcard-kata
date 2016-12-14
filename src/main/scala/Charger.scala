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

    /*def chargeCard(clamCard: ClamCard): List[Charge] = {
      val journey = clamCard.journeys(0)
      val originZone = getZoneFromStation(journey.origin)
      val destinyZone = getZoneFromStation(journey.destiny)
      if (originZone.equals(destinyZone)) {
        val pricesPerZone = prices(originZone)
        return List(Charge(journey, pricesPerZone.find(p => p.period.equals("Single")).get.price))
      } else {
        return List(Charge(journey, prices("B").find(_.period.equals("Single")).get.price))
      }
    }*/

  def topIn(card: ClamCard, origin: String): Unit = card.addOrigin(origin)

  def topOut(card: ClamCard, destiny: String): Unit = {
    card.addDestiny(destiny)
    card.charge()
  }

}

case class Journey(origin: String, destiny: Option[String] = None, charge: Float = 0)

case class ClamCard(name: String) {
  var journeys: ListBuffer[Journey] = ListBuffer()

  def addOrigin(origin: String): Unit = journeys.prepend(Journey(origin))

  def addDestiny(destiny: String): Unit = journeys.update(0, Journey(journeys.head.origin, Some(destiny)))

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