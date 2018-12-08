package org.compiere.tax

import org.idempiere.common.exceptions.AdempiereException
import org.idempiere.common.util.Util

/**
 * Throw when tax not found for given charge
 * @author Teo Sarca, www.arhipac.ro
 *  * FR [ 2758097 ] Implement TaxNotFoundException
 */
class TaxForChangeNotFoundException(
    C_Charge_ID: Int,
    AD_Org_ID: Int,
    M_Warehouse_ID: Int,
    billC_BPartner_Location_ID: Int,
    shipC_BPartner_Location_ID: Int,
    additionalMsg: String
) : AdempiereException(buildMessage(C_Charge_ID, AD_Org_ID, M_Warehouse_ID,
    billC_BPartner_Location_ID, shipC_BPartner_Location_ID,
    additionalMsg)) {
    companion object {
        private val serialVersionUID = 6553174922970467775L

        private val AD_Message = "TaxForChargeNotFound" // TODO : translate

        private fun buildMessage(
            C_Charge_ID: Int,
            AD_Org_ID: Int,
            M_Warehouse_ID: Int,
            billC_BPartner_Location_ID: Int,
            shipC_BPartner_Location_ID: Int,
            additionalMsg: String
        ): String {
            val msg = StringBuffer("@").append(AD_Message).append("@")
            if (!Util.isEmpty(additionalMsg, true)) {
                msg.append(" ").append(additionalMsg).append(" - ")
            }
            msg.append(" @C_Charge_ID@:").append(C_Charge_ID)
            msg.append(", @AD_Org_ID@:").append(AD_Org_ID)
            msg.append(", @M_Warehouse_ID@:").append(M_Warehouse_ID)
            msg.append(", @C_BPartner_Location_ID@:").append(billC_BPartner_Location_ID)
                .append("/").append(shipC_BPartner_Location_ID)
            //
            return msg.toString()
        }
    }
}