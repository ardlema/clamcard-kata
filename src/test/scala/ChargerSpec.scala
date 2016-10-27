import org.scalatest.{FeatureSpec, FunSpec, GivenWhenThen, Matchers}
import org.scalatest.Matchers._

class ChargerSpec extends FunSpec {

  it("should get the zone from the station") {
    val map = Map("A" -> List("Asterisk", "Aldgate"), "B" -> List("Barbican", "Balham"))

    Charger.getZoneFromStation("Asterisk") should be("A")
  }

  it("should charge a journey within the A Area") {
     val card = ClamCard("manolo", 1)
    card.travels(Journey("Asterisk", "Aldgate"))

    val charges = Charger.chargeCard(card)
    val firstCharge = charges(0)
    firstCharge.price should be(2.5)
  }

  it("should charge a journey from the B Area to the A Area") {
    val card = ClamCard("manolo", 1)
    card.travels(Journey("Asterisk", "Barbican"))

    val charges = Charger.chargeCard(card)
    val firstCharge = charges(0)
    firstCharge.price should be(3.0)
  }
}