package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;
import org.w3c.dom.Node;

import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.QueryObjectFactory;

public class QueryObjectFactoryImpl implements QueryObjectFactory {

	private static final Logger log = LoggerFactory.getLogger(QueryObjectFactoryImpl.class);
	private static final JaxbUtil ju = new JaxbUtil(GetMedicationHistoryType.class);

	private String eiServiceDomain;
	public void setEiServiceDomain(String eiServiceDomain) {
		this.eiServiceDomain = eiServiceDomain;
	}

	private String eiCategorization;
	public void setEiCategorization(String eiCategorization) {
		this.eiCategorization = eiCategorization;
	}

	/**
	 * Transformerar GetAggregatedMedicationHistory request till EI FindContent request enligt:
	 * 
	 * 1. subjectOfCareId --> registeredResidentIdentification
	 * 2. "riv:clinicalprocess:activityprescription:actoutcome" --> serviceDomain
	 * 3. "caa-gmh" --> categorization
	 */
	public QueryObject createQueryObject(Node node) {
		
		final GetMedicationHistoryType request = (GetMedicationHistoryType)ju.unmarshal(node);
		
		if(log.isDebugEnabled() && request.getPatientId() != null) {
			log.debug("Transformed payload for pid: {}", request.getPatientId().getId());
		}
		
		final FindContentType fc = new FindContentType();
		if(request.getPatientId() != null) {
			fc.setRegisteredResidentIdentification(request.getPatientId().getId());
		}
		fc.setServiceDomain(eiServiceDomain);
		fc.setCategorization(eiCategorization);
		
		return new QueryObject(fc, request);
	}
}
