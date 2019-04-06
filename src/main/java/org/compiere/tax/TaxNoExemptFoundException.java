package org.compiere.tax;

import org.compiere.orm.MOrg;
import org.compiere.orm.MOrgKt;
import org.idempiere.common.exceptions.AdempiereException;

/**
 * Throw by Tax Engine where no tax found for given criteria
 *
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 2758097 ] Implement TaxNotFoundException
 */
public class TaxNoExemptFoundException extends AdempiereException {
    /**
     *
     */
    private static final long serialVersionUID = -5489066603806460132L;

    private static final String AD_Message = "TaxNoExemptFound";

    public TaxNoExemptFoundException(int AD_Org_ID) {
        super(buildMessage(AD_Org_ID));
    }

    private static final String buildMessage(int AD_Org_ID) {
        StringBuffer msg = new StringBuffer("@").append(AD_Message).append("@");
        msg.append("@AD_Org_ID@:").append(getOrgString(AD_Org_ID));
        //
        return msg.toString();
    }

    private static final String getOrgString(int AD_Org_ID) {
        if (AD_Org_ID <= 0) {
            return "*";
        }
        MOrg org = MOrgKt.getOrg(AD_Org_ID);
        if (org.getId() != AD_Org_ID) {
            return "?";
        }
        return org.getName();
    }
}
