package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory;

import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateAggregatedResponseTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GAMHCreateAggregatedResponseTest extends CreateAggregatedResponseTest {

  private static GAMHAgpServiceConfiguration configuration = new GAMHAgpServiceConfiguration();
  private static AgpServiceFactory<GetMedicationHistoryResponseType> agpServiceFactory = new GAMHAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GAMHCreateAggregatedResponseTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }

  @Override
  public int getResponseSize(Object response) {
    GetMedicationHistoryResponseType responseType = (GetMedicationHistoryResponseType) response;
    return responseType.getMedicationMedicalRecord().size();
  }
}