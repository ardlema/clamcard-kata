import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}
import org.scalatest.Matchers._

class ClamCardKataSpec extends FeatureSpec with GivenWhenThen with Matchers {

  feature("The user can use his contactless travel card for the London underground") {

    info("As a user of the London underground")
    info("I want to use my contactless travel card")
    info("So that I can travel around the underground and be charged accordingly")

    scenario("Michael travels within ZoneA") {
      Given("Michael has a ClamCard")
      val michaelClamCard = new ClamCard("Michael", 1)
      When("He travels from Asterisk to Aldgate")
      michaelClamCard.travels(Journey("Asterisk", "Aldgate"))
      Then("He will be charged £2.50 for his journey")
      val charge = Charger.chargeCard(michaelClamCard)
      charge should be(2.5)
    }

    scenario("Michael travels from ZoneA to ZoneB and within ZoneB") {
      Given("Michael has a ClamCard")
      val michaelClamCard = new ClamCard("Michael", 1)
      When("He travels from Asterisk to Barbican")
      michaelClamCard.travels(Journey("Asterisk", "Barbican"))
      Then("He will be charged £3.00 for his first journey")
      val charge1 = Charger.chargeCard(michaelClamCard)
      charge1 should be(3.0)
      And("He travels from Barbican to Balham")
      michaelClamCard.travels(Journey("Barbican", "Balham"))
      And("a further £3.00 for his second journey.")
      val charge2 = Charger.chargeCard(michaelClamCard)
      charge2 should be(3.0)

    }

    scenario("Michael travels from ZoneA to ZoneB, within ZoneB and within ZoneB again") {
      Given("Michael has a ClamCard")
      val michaelClamCard = new ClamCard("Michael", 1)
      When("He travels from Asterisk to Barbican")
      michaelClamCard.travels(Journey("Asterisk", "Barbican"))
      Then("He will be charged £3.00 for his first journey")
      val charge1 = Charger.chargeCard(michaelClamCard)
      charge1 should be(3.0)
      And("He travels from Barbican to Balham")
      michaelClamCard.travels(Journey("Barbican", "Balham"))
      And("a further £3.00 for his second journey")
      val charge2 = Charger.chargeCard(michaelClamCard)
      charge2 should be(3.0)
      And("He travels from Balham to Bison")
      michaelClamCard.travels(Journey("Balham", "Bison"))
      And("a further £2.00 for his third journey")
      val charge3 = Charger.chargeCard(michaelClamCard)
      charge3 should be(2.0)
      When("He travels from Bison to Bullhead")
      Then("He won't be charged for his last journey")
    }
  }
}