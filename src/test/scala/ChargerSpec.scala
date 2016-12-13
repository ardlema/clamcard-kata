import org.scalatest.FunSpec
import org.scalatest.Matchers._

class ChargerSpec extends FunSpec {

  it("should get the zone from the station") {
    val map = Map("A" -> List("Asterisk", "Aldgate"), "B" -> List("Barbican", "Balham"))

    Charger.getZoneFromStation("Asterisk") should be("A")
  }

  it("should add the origin station when the user top-in") {
    val card = ClamCard("manolo")
    Charger.topIn(card, "Asterisk")
    card.journeys.size should be(1)
    card.journeys(0).origin should be("Asterisk")
    card.journeys(0).destiny.isDefined should be(false)
  }

  it("should add the origin station at the first position") {
    val card = ClamCard("manolo")
    Charger.topIn(card, "Asterisk")
    Charger.topIn(card, "Aldgate")
    card.journeys.size should be(2)
    card.journeys(0).origin should be("Aldgate")
  }

  /*it("should charge a journey within the A Area") {
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
  }*/
}