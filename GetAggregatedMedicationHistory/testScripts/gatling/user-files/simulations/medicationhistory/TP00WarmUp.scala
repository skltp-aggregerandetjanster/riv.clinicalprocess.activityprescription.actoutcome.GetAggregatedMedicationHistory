package medicationhistory

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scenarios.GetAggregatedMedicationHistoryScenario

/**
 * Simple requests to warm up service.
 */
class TP00WarmUp extends Simulation {

//val baseURL           = "https://test.esb.ntjp.se/vp/clinicalprocess/activityprescription/actoutcome/GetMedicationHistory/2/rivtabp21"
//val baseURL           = "http://ine-dit-app02.sth.basefarm.net:9013/GetAggregatedMedicationHistory/service/v2"
  val baseURL           = "https://qa.esb.ntjp.se/vp/clinicalprocess/activityprescription/actoutcome/GetMedicationHistory/2/rivtabp21"

  val testDuration      = 1 minute
  val minWaitDuration   = 2 seconds
  val maxWaitDuration   = 5 seconds
  val times:Int         = 1 // 6
  
  val httpProtocol = http.baseURL(baseURL).disableResponseChunksDiscarding

  val warmUp = scenario("warm up")
                 .repeat(times) {
//                   feed(csv("patients.csv").queue)
                   exec(session => {
                     session.set("status","200").set("patientid","121212121212").set("name","Tolvan Tolvansson").set("count","3")
                   })    
                   .exec(GetAggregatedMedicationHistoryScenario.request)
                   .pause(1 second)
                  }
                 
  setUp (warmUp.inject(atOnceUsers(1)).protocols(httpProtocol))
}