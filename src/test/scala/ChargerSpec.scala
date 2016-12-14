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

  it("should charge a journey within the A area when the user top-out") {
    val card = ClamCard("manolo")
    Charger.topIn(card, "Asterisk")
    Charger.topOut(card, "Aldgate")
    card.journeys.size should be(1)
    card.journeys(0).origin should be("Asterisk")
    card.journeys(0).destiny.get should be("Aldgate")
    card.journeys(0).charge should be(2.5)
  }

  it("should charge a journey from the A Area to the B Area") {
    val card = ClamCard("manolo")
    Charger.topIn(card, "Asterisk")
    Charger.topOut(card, "Barbican")
    card.journeys.size should be(1)
    card.journeys(0).origin should be("Asterisk")
    card.journeys(0).destiny.get should be("Barbican")
    card.journeys(0).charge should be(3.0)
  }

  it("should charge the Single Fare for Zone A when travelling within Zone A and does not exceed the Day Fare") {
    val card = ClamCard("manolo")
    Charger.topIn(card, "Asterisk")
    Charger.topOut(card, "Aldgate")
    val charge = Charger.workOutCharge(card)
    charge should be(2.5)
  }

  it("should charge the Single Fare for Zone B when travelling within Zone B and does not exceed the Day Fare") {
    val card = ClamCard("manolo")
    Charger.topIn(card, "Barbican")
    Charger.topOut(card, "Balham")
    val charge = Charger.workOutCharge(card)
    charge should be(3.0)
  }

  it("should charge the Single Fare for Zone B when travelling between zones and does not exceed the Day Fare") {
    val card = ClamCard("manolo")
    Charger.topIn(card, "Asterisk")
    Charger.topOut(card, "Barbican")
    val charge = Charger.workOutCharge(card)
    charge should be(3.0)
  }
}