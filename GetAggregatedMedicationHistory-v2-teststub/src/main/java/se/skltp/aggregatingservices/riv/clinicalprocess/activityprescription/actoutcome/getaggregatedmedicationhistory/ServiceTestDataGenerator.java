package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory;

import lombok.extern.log4j.Log4j2;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Service;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryResponseType;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.MedicationMedicalRecordBodyType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.MedicationMedicalRecordType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.PatientSummaryHeaderType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.PersonIdType;
import se.skltp.aggregatingservices.data.TestDataGenerator;

@Log4j2
@Service
public class ServiceTestDataGenerator extends TestDataGenerator {

  @Override
  public String getPatientId(MessageContentsList messageContentsList) {
    GetMedicationHistoryType request = (GetMedicationHistoryType) messageContentsList.get(1);
    return request.getPatientId().getId();
  }

  @Override
  public Object createResponse(Object... responseItems) {
    log.info("Creating a response with {} items", responseItems.length);
    GetMedicationHistoryResponseType response = new GetMedicationHistoryResponseType();
    for (int i = 0; i < responseItems.length; i++) {
      response.getMedicationMedicalRecord().add((MedicationMedicalRecordType) responseItems[i]);
    }
    log.info("response.toString:" + response.toString());
    return response;
  }

  @Override
  public Object createResponseItem(String logicalAddress, String registeredResidentId,
      String businessObjectId, String time) {
    log.debug(
        "Created ResponseItem for logical-address {}, registeredResidentId {} and businessObjectId {}",
        new Object[]{logicalAddress, registeredResidentId, businessObjectId});

    MedicationMedicalRecordType resp = new MedicationMedicalRecordType();
    resp.setMedicationMedicalRecordBody(new MedicationMedicalRecordBodyType());
    resp.setMedicationMedicalRecordHeader(new PatientSummaryHeaderType());
    resp.getMedicationMedicalRecordHeader().setPatientId(new PersonIdType());
    resp.getMedicationMedicalRecordHeader().getPatientId().setId(registeredResidentId);
    return resp;
  }

  public Object createRequest(String patientId, String sourceSystemHSAId) {
    GetMedicationHistoryType type = new GetMedicationHistoryType();
    PersonIdType person = new PersonIdType();
    person.setId(patientId);
    type.setPatientId(person);
    type.setSourceSystemHSAId(sourceSystemHSAId);
    return type;
  }
}
