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

    def chargeCard(clamCard: ClamCard): Float = {
      val journey = clamCard.journeys(0)
      val originZone = getZoneFromStation(journey.origin)
      val destinyZone = getZoneFromStation(journey.destiny)
      if (originZone.equals(destinyZone)) {
        val pricesPerZone = prices(originZone)
        return pricesPerZone.find(p => p.period.equals("Single")).get.price
      } else {
        return prices("B").find(_.period.equals("Single")).get.price
      }
    }

}

case class Journey(origin: String, destiny: String)

case class ClamCard(name: String, id: Long) {
  var journeys: ListBuffer[Journey] = ListBuffer()

  def travels(journey: Journey) = {
    journeys.append(journey)
  }
}

case class PeriodAndPrice(period: String, price: Float)