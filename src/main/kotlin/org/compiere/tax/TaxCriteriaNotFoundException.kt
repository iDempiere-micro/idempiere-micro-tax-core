package org.compiere.tax

import org.idempiere.common.exceptions.AdempiereException

/**
 * Throw when a tax criteria was not found
 * @author Teo Sarca, www.arhipac.ro
 *  * FR [ 2758097 ] Implement TaxNotFoundException
 */
class TaxCriteriaNotFoundException(criteriaName: String, criteria_ID: Int) :
    AdempiereException(buildMessage(criteriaName, criteria_ID)) {
    companion object {
        /**
         *
         */
        private val serialVersionUID = -8192276006656371964L

        private val AD_Message = "TaxCriteriaNotFound"

        private fun buildMessage(criteriaName: String, criteria_ID: Int): String {
            val msg = StringBuffer("@").append(AD_Message).append("@")
            msg.append(" @").append(criteriaName).append("@")
            msg.append(" (ID ").append(criteria_ID).append(")")
            return msg.toString()
        }
    }
}