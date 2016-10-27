import org.scalatest.{FeatureSpec, FunSpec, GivenWhenThen, Matchers}
import org.scalatest.Matchers._

class ChargerSpec extends FunSpec {

  it("should get the zone from the station") {
    val map = Map("A" -> List("Asterisk", "Aldgate"), "B" -> List("Barbican", "Balham"))

    Charger.getZoneFromStation("Asterisk") should be("A")
  }

/*  it("should charge a journey within the A Area") {
     val card = ClamCard("manolo", 1, List(Journey("Asterisk", "Aldgate")))

    Charger.chargeCard(card) should be(2.5)
  }*/
}