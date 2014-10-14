package agp

import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._

class GetAggregatedDiagnosisSimulation_robustness extends Simulation {

  val testTimeSecs   = 43200 //12 hrs
  val noOfUsers      = 5 // gives 1 req/sec
  val rampUpTimeSecs = 120
        val minWaitMs      = 2000 milliseconds
  val maxWaitMs      = 5000 milliseconds

  // System under test
  val _baseURL        = "http://33.33.33.33:8088"
  val _contextPath    = "/GetAggregatedMedicationHistory/service/v2"


  val httpConf = httpConfig
    .baseURL(_baseURL)
    .disableResponseChunksDiscarding

  val headers = Map(
    "Accept-Encoding" -> "gzip,deflate",
    "Content-Type" -> "text/xml;charset=UTF-8",
    "SOAPAction" -> "urn:riv:clinicalprocess:activityprescription:actoutcome:GetMedicationHistoryResponder:1:GetMedicationHistory",
    "x-vp-sender-id" -> "sid",
    "x-rivta-original-serviceconsumer-hsaid" -> "TP",
                "Keep-Alive" -> "115")


  val scn = scenario("GetAggregatedMedicationHistory")
    .during(testTimeSecs) {
      feed(csv("patients.csv").random)
      .exec(
        http("GetAggregatedDiagnosis ${patientid} - ${name}")
          .post(_contextPath)
          .headers(headers)
          .fileBody("GetMedicationHistory_request", Map("patientId" -> "${patientid}")).asXML
          .check(status.is(session => session.getTypedAttribute[String]("status").toInt))
          .check(xpath("soap:Envelope", List("soap" -> "http://schemas.xmlsoap.org/soap/envelope/")).exists)
          .check(xpath("//*[local-name()='medicationMedicalRecord']",
                         List("ns2" -> "urn:riv:clinicalprocess:activityprescription:actoutcome:GetMedicationHistoryResponder:2")).count.is(session => session.getTypedAttribute[String]("count").toInt))
      )
      .pause(minWaitMs, maxWaitMs)
    }

  setUp(scn.users(noOfUsers).ramp(rampUpTimeSecs).protocolConfig(httpConf))

}
