package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory.integrationtest;

import javax.jws.WebService;

import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistory.v2.rivtabp21.GetMedicationHistoryResponderInterface;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryResponseType;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryType;
import se.skltp.agp.test.producer.TestProducerDb;

@WebService(serviceName = "GetMedicationHistoryResponderService", 
               portName = "GetMedicationHistoryResponderPort", 
        targetNamespace = "urn:riv:clinicalprocess:activityprescription:actoutcome:GetMedicationHistory:2:rivtabp21", 
                   name = "GetMedicationHistoryInteraction")
public class GetAggregatedMedicationHistoryTestProducer implements GetMedicationHistoryResponderInterface {

    private TestProducerDb testDb;

    public void setTestDb(TestProducerDb testDb) {
        this.testDb = testDb;
    }

    public GetMedicationHistoryResponseType getMedicationHistory(String logicalAddress, GetMedicationHistoryType request) {
        final GetMedicationHistoryResponseType response 
           = (GetMedicationHistoryResponseType) testDb.processRequest(logicalAddress, request.getPatientId().getId());
        if (response == null) {
            return new GetMedicationHistoryResponseType();
        }
        return response;
    }
}