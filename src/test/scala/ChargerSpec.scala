import org.scalatest.FunSpec
import org.scalatest.Matchers._

class ChargerSpec extends FunSpec {

  it("should get the zone from the station") {
    val map = Map("A" -> List("Asterisk", "Aldgate"), "B" -> List("Barbican", "Balham"))

    Charger.getZoneFromStation("Asterisk") should be("A")
  }

  //TODO: Test for non-existing station

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
    val (cardWithDestiny, charge) = Charger.topOut(card, "Aldgate")
    cardWithDestiny.journeys.size should be(1)
    cardWithDestiny.journeys(0).origin should be("Asterisk")
    cardWithDestiny.journeys(0).destiny.get should be("Aldgate")
    charge.cardId should be("manolo")
    charge.charge should be(2.5)
  }

  it("should charge a journey from the A Area to the B Area") {
    val card = ClamCard("manolo")
    Charger.topIn(card, "Asterisk")
    val (cardWithDestiny, charge) = Charger.topOut(card, "Barbican")
    cardWithDestiny.journeys.size should be(1)
    cardWithDestiny.journeys(0).origin should be("Asterisk")
    cardWithDestiny.journeys(0).destiny.get should be("Barbican")
    charge.cardId should be("manolo")
    charge.charge should be(3.0)
  }

  it("should charge the Single Fare for Zone A when travelling within Zone A and does not exceed the Day Fare") {
    val card = ClamCard("manolo")
    Charger.topIn(card, "Asterisk")
    Charger.topOut(card, "Aldgate")
    val charge = Charger.workOutCharge(card)
    charge.charge should be(2.5)
    charge.cardId should be("manolo")
  }

  it("should charge the Single Fare for Zone B when travelling within Zone B and does not exceed the Day Fare") {
    val card = ClamCard("manolo")
    Charger.topIn(card, "Barbican")
    Charger.topOut(card, "Balham")
    val charge = Charger.workOutCharge(card)
    charge.charge should be(3.0)
    charge.cardId should be("manolo")
  }

  it("should charge the Single Fare for Zone B when travelling between zones and does not exceed the Day Fare") {
    val card = ClamCard("manolo")
    Charger.topIn(card, "Asterisk")
    Charger.topOut(card, "Barbican")
    val charge = Charger.workOutCharge(card)
    charge.charge should be(3.0)
    charge.cardId should be("manolo")
  }

  it("should charge the difference til Day Fare for Zone A when travelling within zone A and exceeds the Day Fare") {
    val card = ClamCard("manolo")
    Charger.topIn(card, "Asterisk")
    Charger.topOut(card, "Aldgate")
    Charger.topIn(card, "Aldgate")
    Charger.topOut(card, "Asterisk")
    Charger.topIn(card, "Asterisk")
    Charger.topOut(card, "Aldgate")
    val charge = Charger.workOutCharge(card)
    charge.charge should be(2.0)
    charge.cardId should be("manolo")
  }

  it("should charge the difference til Day Fare for Zone B when travelling within zone B and exceeds the Day Fare") {
    val card = ClamCard("manolo")
    Charger.topIn(card, "Barbican")
    Charger.topOut(card, "Balham")
    Charger.topIn(card, "Balham")
    Charger.topOut(card, "Barbican")
    Charger.topIn(card, "Barbican")
    val (cardCharged, charge) = Charger.topOut(card, "Balham")
    charge.charge should be(2.0)
    charge.cardId should be("manolo")
  }
}