package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory.integrationtest;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistory.v2.rivtabp21.GetMedicationHistoryResponderInterface;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryResponseType;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryType;
import se.skltp.agp.test.producer.TestProducerDb;

@WebService(serviceName = "GetMedicationHistoryResponderService", portName = "GetMedicationHistoryResponderPort", targetNamespace = "urn:riv:clinicalprocess:activityprescription:actoutcome:GetMedicationHistory:1:rivtabp21", name = "GetMedicationHistoryInteraction")
public class GetAggregatedMedicationHistoryTestProducer implements GetMedicationHistoryResponderInterface {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedMedicationHistoryTestProducer.class);

	private TestProducerDb testDb;
	public void setTestDb(TestProducerDb testDb) {
		this.testDb = testDb;
	}

	public GetMedicationHistoryResponseType getMedicationHistory(String logicalAddress, GetMedicationHistoryType request) {
		GetMedicationHistoryResponseType response = null;


        // TODO: CHANGE GENERATED SAMPLE CODE - START
        if (1==1) throw new UnsupportedOperationException("Not yet implemented");
        /*

		log.info("### Virtual service for GetAggregatedMedicationHistory call the source system with logical address: {} and patientId: {}", logicalAddress, request.getSubjectOfCareId());

		response = (GetAggregatedMedicationHistoryResponseType)testDb.processRequest(logicalAddress, request.getSubjectOfCareId());
        if (response == null) {
        	// Return an empty response object instead of null if nothing is found
        	response = new GetAggregatedMedicationHistoryResponseType();
        }

		log.info("### Virtual service got {} booknings in the reply from the source system with logical address: {} and patientId: {}", new Object[] {response.getRequestActivity().size(), logicalAddress, request.getSubjectOfCareId()});

        */
        // TODO: CHANGE GENERATED SAMPLE CODE - END


		// We are done
        return response;
	}
}