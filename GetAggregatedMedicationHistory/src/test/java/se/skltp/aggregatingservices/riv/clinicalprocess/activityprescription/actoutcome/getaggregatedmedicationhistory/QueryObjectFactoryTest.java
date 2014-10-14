package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory;

import static org.junit.Assert.*;

import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryType;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.ObjectFactory;
import riv.clinicalprocess.activityprescription.actoutcome.v2.DatePeriodType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.PersonIdType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;


public class QueryObjectFactoryTest {

	private static final QueryObjectFactoryImpl testObject = new QueryObjectFactoryImpl();
	private static final ObjectFactory objFactory = new ObjectFactory();
	
	
	private static final String TEST_DATA_1 = UUID.randomUUID().toString();
	private static final String TEST_DATA_2 = UUID.randomUUID().toString();
	private static final String TEST_DATA_3 = UUID.randomUUID().toString();
	
	@BeforeClass
	public static void init() {
		testObject.setEiCategorization(TEST_DATA_1);
		testObject.setEiServiceDomain(TEST_DATA_2);
	}
	
	@Test
	public void testQueryObjectFactory() throws Exception {
		final GetMedicationHistoryType type = new GetMedicationHistoryType();
		
		final PersonIdType person = new PersonIdType();
		person.setId(TEST_DATA_3);
		type.setPatientId(person);
		
		final Node node = createNode(type);
		final FindContentType findContent = testObject.createQueryObject(node).getFindContent();
		assertEquals(TEST_DATA_1, findContent.getCategorization());
		assertEquals(TEST_DATA_2, findContent.getServiceDomain());
		assertEquals(TEST_DATA_3, findContent.getRegisteredResidentIdentification());
		
		assertNull(findContent.getLogicalAddress());
		assertNull(findContent.getSourceSystem());
		assertNull(findContent.getBusinessObjectInstanceIdentifier());
		assertNull(findContent.getClinicalProcessInterestId());
		assertNull(findContent.getDataController());
		assertNull(findContent.getMostRecentContent());
		assertNull(findContent.getOwner());
	}
	
	private Node createNode(final GetMedicationHistoryType request) throws Exception {
		JAXBElement<GetMedicationHistoryType> jaxb = objFactory.createGetMedicationHistory(request);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document node = db.newDocument();
		
		JAXBContext jc = JAXBContext.newInstance(GetMedicationHistoryType.class);
		Marshaller marshaller = jc.createMarshaller();
		marshaller.marshal(jaxb, node);
		return node;
	}
}
