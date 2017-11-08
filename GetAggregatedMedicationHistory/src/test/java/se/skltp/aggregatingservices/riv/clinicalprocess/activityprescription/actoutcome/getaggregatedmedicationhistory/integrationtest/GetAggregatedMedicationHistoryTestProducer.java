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