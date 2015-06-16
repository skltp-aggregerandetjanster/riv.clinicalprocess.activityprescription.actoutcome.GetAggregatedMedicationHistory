package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;

import riv.clinicalprocess.activityprescription.actoutcome.enums.v2.ResultCodeEnum;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryResponseType;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.ObjectFactory;
import riv.clinicalprocess.activityprescription.actoutcome.v2.ResultType;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.ResponseListFactory;

public class ResponseListFactoryImpl implements ResponseListFactory {

    private static final Logger log = LoggerFactory.getLogger(ResponseListFactoryImpl.class);
    private static final JaxbUtil jaxbUtil = new JaxbUtil(GetMedicationHistoryResponseType.class, ProcessingStatusType.class);
    private static final ObjectFactory OF = new ObjectFactory();

    @Override
    public String getXmlFromAggregatedResponse(QueryObject queryObject, List<Object> aggregatedResponseList) {
        final GetMedicationHistoryResponseType aggregatedResponse = new GetMedicationHistoryResponseType();
        for (Object obj : aggregatedResponseList) {
            final GetMedicationHistoryResponseType response = (GetMedicationHistoryResponseType) obj;
            aggregatedResponse.getMedicationMedicalRecord().addAll(response.getMedicationMedicalRecord());
        }
        aggregatedResponse.setResult(new ResultType());
        aggregatedResponse.getResult().setResultCode(ResultCodeEnum.INFO);

        log.info("Returning {} aggregated medication history for subject of care id {}", 
                aggregatedResponse.getMedicationMedicalRecord().size(),
                queryObject.getFindContent().getRegisteredResidentIdentification());
        return jaxbUtil.marshal(OF.createGetMedicationHistoryResponse(aggregatedResponse));
    }
}