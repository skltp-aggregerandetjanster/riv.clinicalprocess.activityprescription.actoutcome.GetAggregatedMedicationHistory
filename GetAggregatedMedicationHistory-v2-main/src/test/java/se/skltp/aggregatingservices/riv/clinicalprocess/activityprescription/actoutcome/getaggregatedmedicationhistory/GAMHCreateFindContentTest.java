package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory;

import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateFindContentTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class GAMHCreateFindContentTest extends CreateFindContentTest {

  private static GAMHAgpServiceConfiguration configuration = new GAMHAgpServiceConfiguration();
  private static AgpServiceFactory<GetMedicationHistoryResponseType> agpServiceFactory = new GAMHAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GAMHCreateFindContentTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }

}
