import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class ClamCardKataSpec extends FeatureSpec with GivenWhenThen with Matchers {

  feature("The user can use his contactless travel card for the London underground") {

    info("As a user of the London underground")
    info("I want to use my contactless travel card")
    info("So that I can travel around the underground and be charged accordingly")

    /*scenario("Michael travels within ZoneA") {
      Given("Michael has a ClamCard")
      val michaelClamCard = new ClamCard("Michael")
      When("He travels from Asterisk to Aldgate")
      Charger.topIn(michaelClamCard, "Asterisk")
      Charger.topOut(michaelClamCard, "Aldgate")
      Then("He will be charged £2.50 for his journey")
      val firstCharge = michaelClamCard.journeys(0)
      firstCharge.charge should be(2.5)
    }

    scenario("Michael travels from ZoneA to ZoneB and within ZoneB") {
      Given("Michael has a ClamCard")
      val michaelClamCard = new ClamCard("Michael")
      When("He travels from Asterisk to Barbican")
      Charger.topIn(michaelClamCard, "Asterisk")
      Charger.topOut(michaelClamCard, "Barbican")
      Then("He will be charged £3.00 for his first journey")
      val firstCharge = michaelClamCard.journeys(0)
      firstCharge.charge should be(3.0)
      And("He travels from Barbican to Balham")
      Charger.topIn(michaelClamCard, "Barbican")
      Charger.topOut(michaelClamCard, "Balham")
      And("a further £3.00 for his second journey.")
      val secondCharge = michaelClamCard.journeys(0)
      secondCharge.charge should be(3.0)
    }

    scenario("Michael travels from ZoneA to ZoneB, within ZoneB and within ZoneB again") {
      Given("Michael has a ClamCard")
      val michaelClamCard = new ClamCard("Michael")
      When("He travels from Asterisk to Barbican")
      Charger.topIn(michaelClamCard, "Asterisk")
      Charger.topOut(michaelClamCard, "Barbican")
      Then("He will be charged £3.00 for his first journey")
      val firstCharge = michaelClamCard.journeys(0)
      firstCharge.charge should be(3.0)
      And("He travels from Barbican to Balham")
      Charger.topIn(michaelClamCard, "Barbican")
      Charger.topOut(michaelClamCard, "Balham")
      And("a further £3.00 for his second journey")
      val secondCharge = michaelClamCard.journeys(0)
      secondCharge.charge should be(3.0)
      And("He travels from Balham to Bison")
      Charger.topIn(michaelClamCard, "Balham")
      Charger.topOut(michaelClamCard, "Bison")
      And("a further £2.00 for his third journey")
      val thirdCharge = michaelClamCard.journeys(0)
      thirdCharge.charge should be(2.0)
      /*When("He travels from Bison to Bullhead")
      Then("He won't be charged for his last journey")*/
    }*/
  }
}