package org.compiere.tax;

import kotliquery.Row;
import org.compiere.model.I_C_TaxPostal;
import org.compiere.orm.PO;

import java.util.Properties;

/**
 * Generated Model for C_TaxPostal
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_TaxPostal extends PO implements I_C_TaxPostal {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_TaxPostal(Properties ctx, int C_TaxPostal_ID) {
        super(ctx, C_TaxPostal_ID);
        /** if (C_TaxPostal_ID == 0) { setC_Tax_ID (0); setC_TaxPostal_ID (0); setPostal (null); } */
    }

    /**
     * Load Constructor
     */
    public X_C_TaxPostal(Properties ctx, Row row) {
        super(ctx, row);
    }

    /**
     * AccessLevel
     *
     * @return 2 - Client
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_C_TaxPostal[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get ZIP.
     *
     * @return Postal code
     */
    public String getPostal() {
        return (String) getValue(COLUMNNAME_Postal);
    }

    /**
     * Get ZIP To.
     *
     * @return Postal code to
     */
    public String getPostal_To() {
        return (String) getValue(COLUMNNAME_Postal_To);
    }

    @Override
    public int getTableId() {
        return I_C_TaxPostal.Table_ID;
    }
}
