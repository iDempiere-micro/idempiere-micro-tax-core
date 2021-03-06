package org.compiere.tax;

import kotliquery.Row;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.orm.BasePOName;

/**
 * Generated Model for C_TaxCategory
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_TaxCategory extends BasePOName implements I_C_TaxCategory {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_TaxCategory(int C_TaxCategory_ID) {
        super(C_TaxCategory_ID);
        /**
         * if (C_TaxCategory_ID == 0) { setTaxCategoryId (0); setIsDefault (false); setName (null); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_TaxCategory(Row row) {
        super(row);
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
        StringBuffer sb = new StringBuffer("X_C_TaxCategory[").append(getId()).append("]");
        return sb.toString();
    }

    /**
     * Get Tax Category.
     *
     * @return Tax Category
     */
    public int getTaxCategoryId() {
        Integer ii = getValue(COLUMNNAME_C_TaxCategory_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Default.
     *
     * @param IsDefault Default value
     */
    public void setIsDefault(boolean IsDefault) {
        setValue(COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
    }

    /**
     * Get Default.
     *
     * @return Default value
     */
    public boolean isDefault() {
        Object oo = getValue(COLUMNNAME_IsDefault);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo).booleanValue();
            return "Y".equals(oo);
        }
        return false;
    }

    @Override
    public int getTableId() {
        return I_C_TaxCategory.Table_ID;
    }
}
