/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.aggregatingservices.riv.clinicalprocess.activityprescription.actoutcome.getaggregatedmedicationhistory.integrationtest;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.util.ThreadSafeSimpleDateFormat;

import riv.clinicalprocess.activityprescription.actoutcome.enums.v2.PrescriptionStatusEnum;
import riv.clinicalprocess.activityprescription.actoutcome.enums.v2.ResultCodeEnum;
import riv.clinicalprocess.activityprescription.actoutcome.enums.v2.TypeOfPrescriptionEnum;
import riv.clinicalprocess.activityprescription.actoutcome.getmedicationhistoryresponder.v2.GetMedicationHistoryResponseType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.CVType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.DosageType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.DrugChoiceType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.HealthcareProfessionalType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.IIType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.LengthOfTreatmentType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.MedicationMedicalRecordBodyType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.MedicationMedicalRecordType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.MedicationPrescriptionType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.OrgUnitType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.PQIntervalType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.PatientSummaryHeaderType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.PersonIdType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.PrescriptionReasonType;
import riv.clinicalprocess.activityprescription.actoutcome.v2.ResultType;
import se.skltp.agp.test.producer.TestProducerDb;

public class GetAggregatedMedicationHistoryTestProducerDb extends TestProducerDb {

    private static final Logger log = LoggerFactory.getLogger(GetAggregatedMedicationHistoryTestProducerDb.class);
    private static final ThreadSafeSimpleDateFormat tf = new ThreadSafeSimpleDateFormat("yyyyMMddhhmmss");

    @Override
    public Object createResponse(Object... responseItems) {
        log.debug("Creates a response with {} items", responseItems);
        GetMedicationHistoryResponseType response = new GetMedicationHistoryResponseType();
        for (Object obj : responseItems) {
            response.getMedicationMedicalRecord().add((MedicationMedicalRecordType) obj);
        }

        final ResultType result = new ResultType();
        result.setResultCode(ResultCodeEnum.OK);
        result.setLogId(UUID.randomUUID().toString());
        result.setMessage("Result message");
        response.setResult(result);

        return response;
    }

    @Override
    public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {

        log.debug("Created one response item for logical-address {}, registeredResidentId {} and businessObjectId {}", 
                new Object[] {logicalAddress, registeredResidentId, businessObjectId });

        final MedicationMedicalRecordType mm = new MedicationMedicalRecordType();

        final PatientSummaryHeaderType header = new PatientSummaryHeaderType();
        header.setDocumentId(UUID.randomUUID().toString());
        header.setSourceSystemHSAId(logicalAddress);
        header.setDocumentTime(tf.format(new Date()));

        final PersonIdType pp = new PersonIdType();
        pp.setId(registeredResidentId);
        pp.setType("1.2.752.129.2.1.3.1");
        header.setPatientId(pp);

        final HealthcareProfessionalType hp = new HealthcareProfessionalType();
        hp.setAuthorTime(tf.format(new Date()));
        hp.setHealthcareProfessionalCareGiverHSAId(logicalAddress);

        final OrgUnitType ou = new OrgUnitType();
        ou.setOrgUnitAddress("Address");
        ou.setOrgUnitEmail("email@email.com");
        ou.setOrgUnitHSAId(logicalAddress);
        ou.setOrgUnitLocation("Location");
        ou.setOrgUnitName("Sjukhuset");
        ou.setOrgUnitTelecom("00-00000000");
        hp.setHealthcareProfessionalOrgUnit(ou);

        header.setAccountableHealthcareProfessional(hp);

        final MedicationMedicalRecordBodyType body = new MedicationMedicalRecordBodyType();
        final MedicationPrescriptionType mpt = new MedicationPrescriptionType();
        mpt.setPrescriptionId(iiType());
        mpt.setTypeOfPrescription(TypeOfPrescriptionEnum.U);
        mpt.setPrescriptionStatus(PrescriptionStatusEnum.ACTIVE);
        mpt.getPrincipalPrescriptionReason().add(generateReasonType());
        mpt.setDrug(new DrugChoiceType());
        mpt.getDrug().setComment("kommentar");
        mpt.getDrug().getDosage().add(new DosageType());
        mpt.getDrug().getDosage().get(0).setLengthOfTreatment(new LengthOfTreatmentType());
        mpt.getDrug().getDosage().get(0).getLengthOfTreatment().setIsMaximumTreatmentTime(false);
        mpt.getDrug().getDosage().get(0).getLengthOfTreatment().setTreatmentInterval(new PQIntervalType());
        mpt.getDrug().getDosage().get(0).getLengthOfTreatment().getTreatmentInterval().setHigh(new Double(2));
        mpt.getDrug().getDosage().get(0).getLengthOfTreatment().getTreatmentInterval().setLow(new Double(1));
        mpt.getDrug().getDosage().get(0).getLengthOfTreatment().getTreatmentInterval().setUnit("hour");
        body.setMedicationPrescription(mpt);
        mm.setMedicationMedicalRecordHeader(header);
        mm.setMedicationMedicalRecordBody(body);
        return mm;
    }

    protected CVType generateCVType() {
        final CVType cvType = new CVType();
        cvType.setCode("Code");
        cvType.setCodeSystem("CodeSystem");
        cvType.setCodeSystemName("CodeSysteName");
        cvType.setCodeSystemVersion("CodeSystemVersion");
        cvType.setDisplayName("DisplayName");
        cvType.setOriginalText("OriginalText");
        return cvType;
    }

    protected IIType iiType() {
        final IIType ii = new IIType();
        ii.setExtension("iiExtension");
        ii.setRoot("iiRoot");
        return ii;
    }

    protected PrescriptionReasonType generateReasonType() {
        final PrescriptionReasonType prt = new PrescriptionReasonType();
        prt.setOtherReason("OtherReason");
        prt.setReason(generateCVType());
        return prt;
    }
}