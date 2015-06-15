package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory

trait CommonParameters {
  val serviceName:String     = "MedicationHistory"
  val urn:String             = "urn:riv:clinicalprocess:activityprescription:actoutcome:GetMedicationHistoryResponder:2"
  val responseElement:String = "GetMedicationHistoryResponse"
  val responseItem:String    = "medicationMedicalRecord"
  var baseUrl:String         = if (System.getProperty("baseUrl") != null && !System.getProperty("baseUrl").isEmpty()) {
                                   System.getProperty("baseUrl")
                               } else {
                                   "http://33.33.33.33:8081/GetAggregatedMedicationHistory/service/v2"
                               }
}
