package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory.integrationtest;

import static se.skltp.agp.test.producer.TestProducerDb.TEST_RR_ID_ONE_HIT;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistory.v2.rivtabp21.GetMedicationHistoryResponderInterface;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryResponseType;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.PersonIdType;
import se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory.GetAggregatedMedicationHistoryMuleServer;
import se.skltp.agp.test.consumer.AbstractTestConsumer;
import se.skltp.agp.test.consumer.SoapHeaderCxfInterceptor;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;

public class GetAggregatedMedicationHistoryTestConsumer extends AbstractTestConsumer<GetMedicationHistoryResponderInterface> {

	private static final Logger log = LoggerFactory.getLogger(GetAggregatedMedicationHistoryTestConsumer.class);
	
	
	public static void main(String[] args) {
		String serviceAddress = GetAggregatedMedicationHistoryMuleServer.getAddress("SERVICE_INBOUND_URL");
		String personnummer = TEST_RR_ID_ONE_HIT;

		GetAggregatedMedicationHistoryTestConsumer consumer = new GetAggregatedMedicationHistoryTestConsumer(serviceAddress, SAMPLE_SENDER_ID, SAMPLE_ORIGINAL_CONSUMER_HSAID);
		Holder<GetMedicationHistoryResponseType> responseHolder = new Holder<GetMedicationHistoryResponseType>();
		Holder<ProcessingStatusType> processingStatusHolder = new Holder<ProcessingStatusType>();

		consumer.callService("logical-adress", personnummer, processingStatusHolder, responseHolder);


		log.info("Returned #medical records = " + responseHolder.value.getMedicationMedicalRecord().size());


	}

	public GetAggregatedMedicationHistoryTestConsumer(String serviceAddress, String senderId, String originalConsumerHsaId) {
	    
		// Setup a web service proxy for communication using HTTPS with Mutual Authentication
		super(GetMedicationHistoryResponderInterface.class, serviceAddress, senderId, originalConsumerHsaId);
	}

	public void callService(String logicalAddress, String registeredResidentId, Holder<ProcessingStatusType> processingStatusHolder, Holder<GetMedicationHistoryResponseType> responseHolder) {

		log.debug("Calling GetAggregatedMedicationHistory-soap-service with Registered Resident Id = {}", registeredResidentId);
		
		GetMedicationHistoryType request = new GetMedicationHistoryType();
		final PersonIdType personIdType = new PersonIdType();
		personIdType.setId(registeredResidentId);
		personIdType.setType("1.2.752.129.2.1.3.1");
		request.setPatientId(personIdType);

		GetMedicationHistoryResponseType response = _service.getMedicationHistory(logicalAddress, request);
		responseHolder.value = response;
		
		processingStatusHolder.value = SoapHeaderCxfInterceptor.getLastFoundProcessingStatus();
	}
}