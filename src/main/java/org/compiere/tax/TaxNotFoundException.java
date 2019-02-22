package org.compiere.tax;

import org.compiere.crm.MLocation;
import org.compiere.util.DisplayType;
import org.idempiere.common.exceptions.AdempiereException;
import org.idempiere.common.util.Env;

import java.sql.Timestamp;
import java.text.DateFormat;

/**
 * Throw by Tax Engine where no tax found for given criteria
 *
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 2758097 ] Implement TaxNotFoundException
 */
public class TaxNotFoundException extends AdempiereException {
    /**
     *
     */
    private static final long serialVersionUID = -5471615720092096644L;

    /**
     * AD_Message code
     */
    private static final String AD_Message = "TaxNotFound";

    public TaxNotFoundException(
            int C_TaxCategory_ID,
            boolean IsSOTrx,
            Timestamp shipDate,
            int shipFromC_Location_ID,
            int shipToC_Location_ID,
            Timestamp billDate,
            int billFromC_Location_ID,
            int billToC_Location_ID) {
        super(
                buildMessage(
                        C_TaxCategory_ID,
                        IsSOTrx,
                        shipDate,
                        shipFromC_Location_ID,
                        shipToC_Location_ID,
                        billDate,
                        billFromC_Location_ID,
                        billToC_Location_ID));
    }

    private static final String buildMessage(
            int C_TaxCategory_ID,
            boolean IsSOTrx,
            Timestamp shipDate,
            int shipFromC_Location_ID,
            int shipToC_Location_ID,
            Timestamp billDate,
            int billFromC_Location_ID,
            int billToC_Location_ID) {
        final DateFormat df = DisplayType.getDateFormat();
        StringBuffer msg = new StringBuffer("@").append(AD_Message).append("@");
        //
        msg.append(" - @C_TaxCategory_ID@:").append(getTaxCategoryString(C_TaxCategory_ID));
        msg.append(", @IsSOTrx@:@").append(IsSOTrx ? "Y" : "N").append("@");
        //
        msg.append(", @Shipment@ (")
                .append(df.format(shipDate))
                .append(", ")
                .append(getLocationString(shipFromC_Location_ID))
                .append(" -> ")
                .append(getLocationString(shipToC_Location_ID))
                .append(")");
        //
        msg.append(", @Invoice@ (")
                .append(df.format(billDate))
                .append(", ")
                .append(getLocationString(billFromC_Location_ID))
                .append(" -> ")
                .append(getLocationString(billToC_Location_ID))
                .append(")");
        //
        //
        return msg.toString();
    }

    private static final String getTaxCategoryString(int C_TaxCategory_ID) {
        if (C_TaxCategory_ID <= 0) {
            return "?";
        }
        MTaxCategory cat = new MTaxCategory(Env.getCtx(), C_TaxCategory_ID);
        if (cat.getId() != C_TaxCategory_ID) {
            return "?";
        }
        return cat.getName();
    }

    private static final String getLocationString(int C_Location_ID) {
        if (C_Location_ID <= 0) {
            return "?";
        }
        MLocation loc = MLocation.get(Env.getCtx(), C_Location_ID);
        if (loc == null || loc.getId() != C_Location_ID) {
            return "?";
        }
        return loc.toString();
    }
}
