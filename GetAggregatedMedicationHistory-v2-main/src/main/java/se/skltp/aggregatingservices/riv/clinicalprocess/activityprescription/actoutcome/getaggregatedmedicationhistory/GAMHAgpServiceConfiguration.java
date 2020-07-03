package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistory.v2.rivtabp21.GetMedicationHistoryResponderInterface;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistory.v2.rivtabp21.GetMedicationHistoryResponderService;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "getaggregatedmedicationhistory.v2")
public class GAMHAgpServiceConfiguration extends se.skltp.aggregatingservices.configuration.AgpServiceConfiguration {

public static final String SCHEMA_PATH = "/schemas/clinicalprocess.activityprescription.actoutcome/interactions/GetMedicationHistoryInteraction/GetMedicationHistoryInteraction_2.0_RIVTABP21.wsdl";

  public GAMHAgpServiceConfiguration() {

    setServiceName("GetAggregatedMedicationHistory-v2");
    setTargetNamespace("urn:riv:clinicalprocess:activityprescription:actoutcome:GetMedicationHistory:2:rivtabp21");

    // Set inbound defaults
    setInboundServiceURL("http://localhost:9003/GetAggregatedMedicationHistory/service/v2");
    setInboundServiceWsdl(SCHEMA_PATH);
    setInboundServiceClass(GetMedicationHistoryResponderInterface.class.getName());
    setInboundPortName(GetMedicationHistoryResponderService.GetMedicationHistoryResponderPort.toString());

    // Set outbound defaults
    setOutboundServiceWsdl(SCHEMA_PATH);
    setOutboundServiceClass(GetMedicationHistoryResponderInterface.class.getName());
    setOutboundPortName(GetMedicationHistoryResponderService.GetMedicationHistoryResponderPort.toString());

    // FindContent
    setEiServiceDomain("riv:clinicalprocess:activityprescription:actoutcome");
    setEiCategorization("caa-gmh");

    // TAK
    setTakContract("urn:riv:clinicalprocess:activityprescription:actoutcome:GetMedicationHistoryResponder:2");

    // Set service factory
    setServiceFactoryClass(GAMHAgpServiceFactoryImpl.class.getName());
    }


}
