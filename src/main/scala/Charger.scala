import scala.collection.immutable.Iterable
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
      //if (originZone.equals(destinyZone)) {
      val pricesPerZone: List[PeriodAndPrice] = prices(originZone)
      pricesPerZone.find(p => p.period.equals("Single")).get.price
    }

}

case class Journey(origin: String, destiny: String)

case class ClamCard(name: String, id: Long, journeys: List[Journey] = List()) {

  def travels(journey: Journey) = {
    journeys :+ journey
  }
}

case class PeriodAndPrice(period: String, price: Float)