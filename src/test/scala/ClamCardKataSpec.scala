import org.scalatest.{FeatureSpec, GivenWhenThen}
import org.scalatest.Matchers._

class ClamCardKataSpec extends FeatureSpec with GivenWhenThen {

  feature("The user can use his contactless travel card for the London underground") {

    info("As a user of the London underground")
    info("I want to use my contactless travel card")
    info("So that I can travel around the underground and be charged accordingly")

    scenario("Michael travels within ZoneA") {
      Given("Michael has a ClamCard")
      When("He travels from Asterisk to Aldgate")
      Then("He will be charged £2.50 for his journey")
      pending
    }

    scenario("Michael travels from ZoneA to ZoneB and within ZoneB") {
      Given("Michael has a ClamCard")
      When("He travels from Asterisk to Barbican")
      And("He travels from Barbican to Balham")
      Then("He will be charged £3.00 for his first journey")
      And("a further £3.00 for his second journey.")
      pending
    }

    scenario("Michael travels from ZoneA to ZoneB, within ZoneB and within ZoneB again") {
      Given("Michael has a ClamCard")
      When("He travels from Asterisk to Barbican")
      And("He travels from Barbican to Balham")
      And("He travels from Balham to Bison")
      Then("He will be charged £3.00 for his first journey")
      And("a further £3.00 for his second journey")
      And("a further £2.00 for his third journey")
      When("He travels from Bison to Bullhead")
      Then("He won't be charged for his last journey")
      pending
    }
  }
}
