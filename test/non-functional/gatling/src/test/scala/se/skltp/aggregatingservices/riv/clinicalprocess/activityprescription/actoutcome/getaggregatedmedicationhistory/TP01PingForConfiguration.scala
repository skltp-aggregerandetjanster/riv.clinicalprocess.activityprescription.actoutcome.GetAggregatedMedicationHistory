package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory.scenarios.GetAggregatedMedicationHistoryPingForConfigurationScenario

/**
 * Ping for configuration run against remote service - returns ok.
 */
class TP01PingForConfiguration extends Simulation {

  val baseURL = if (System.getProperty("baseUrl") != null && !System.getProperty("baseUrl").isEmpty()) { 
                  System.getProperty("baseUrl") 
                } else {
                  "http://33.33.33.33:8084/agp/getaggregatedmedicationhistory/itintegration/monitoring/PingForConfiguration/1/rivtabp21"
                }
  val httpProtocol = http.baseURL(baseURL).disableResponseChunksDiscarding  
  
  val pingForConfiguration = scenario("ping for configuration")
                 .repeat(2) {
                    exec(GetAggregatedMedicationHistoryPingForConfigurationScenario.request)
                  }

  setUp (pingForConfiguration.inject(atOnceUsers(1)).protocols(httpProtocol))
}
