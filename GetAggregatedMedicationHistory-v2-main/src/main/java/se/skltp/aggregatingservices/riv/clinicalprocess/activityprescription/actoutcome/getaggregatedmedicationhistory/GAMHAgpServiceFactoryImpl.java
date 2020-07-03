package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import riv.clinicalprocess.activityprescription.actoutcome.enums.v2.ResultCodeEnum;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryResponseType;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.ResultType;
import se.skltp.aggregatingservices.AgServiceFactoryBase;

@Log4j2
public class GAMHAgpServiceFactoryImpl extends
    AgServiceFactoryBase<GetMedicationHistoryType, GetMedicationHistoryResponseType> {

  @Override
  public String getPatientId(GetMedicationHistoryType queryObject) {
    return queryObject.getPatientId().getId();
  }

  @Override
  public String getSourceSystemHsaId(GetMedicationHistoryType queryObject) {
    return queryObject.getSourceSystemHSAId();
  }

  @Override
  public GetMedicationHistoryResponseType aggregateResponse(
      List<GetMedicationHistoryResponseType> aggregatedResponseList) {
    GetMedicationHistoryResponseType aggregatedResponse = new GetMedicationHistoryResponseType();
    for(GetMedicationHistoryResponseType response : aggregatedResponseList){
      aggregatedResponse.getMedicationMedicalRecord().addAll(response.getMedicationMedicalRecord());
    }

    aggregatedResponse.setResult(new ResultType());
    aggregatedResponse.getResult().setResultCode(ResultCodeEnum.INFO);
    aggregatedResponse.getResult().setLogId("NA");

    return aggregatedResponse;
  }
}

