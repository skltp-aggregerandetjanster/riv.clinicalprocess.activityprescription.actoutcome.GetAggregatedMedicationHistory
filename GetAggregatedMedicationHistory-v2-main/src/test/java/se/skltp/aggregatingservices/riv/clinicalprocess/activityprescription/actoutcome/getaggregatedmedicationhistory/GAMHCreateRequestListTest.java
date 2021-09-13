package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory;

import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateRequestListTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GAMHCreateRequestListTest extends CreateRequestListTest {

  private static GAMHAgpServiceConfiguration configuration = new GAMHAgpServiceConfiguration();
  private static AgpServiceFactory<GetMedicationHistoryResponseType> agpServiceFactory = new GAMHAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GAMHCreateRequestListTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }
}