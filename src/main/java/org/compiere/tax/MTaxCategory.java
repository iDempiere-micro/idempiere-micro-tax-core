package org.compiere.tax;

import kotliquery.Row;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.orm.Query;
import org.idempiere.common.exceptions.AdempiereException;

import java.util.List;

/**
 * Tax Category Model
 *
 * @author Jorg Janke
 * @version $Id: MTaxCategory.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MTaxCategory extends X_C_TaxCategory {
    /**
     *
     */
    private static final long serialVersionUID = -5521670797405300136L;

    /**
     * Standard Constructor
     *
     * @param ctx              context
     * @param C_TaxCategory_ID id
     */
    public MTaxCategory(int C_TaxCategory_ID) {
        super(C_TaxCategory_ID);
        if (C_TaxCategory_ID == 0) {
            //	setName (null);
            setIsDefault(false);
        }
    } //	MTaxCategory

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MTaxCategory(Row row) {
        super(row);
    } //	MTaxCategory

    /**
     * getDefaultTax Get the default tax id associated with this tax category
     */
    public MTax getDefaultTax() {
        MTax m_tax;

        final String whereClause =
                I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID
                        + "=? AND "
                        + I_C_TaxCategory.COLUMNNAME_IsDefault
                        + "='Y'";
        List<MTax> list =
                new Query(I_C_Tax.Table_Name, whereClause)
                        .setParameters(getTaxCategoryId())
                        .setOnlyActiveRecords(true)
                        .list();
        if (list.size() == 0) {
            throw new AdempiereException("NoDefaultTaxRate"); // Error - should be at least one default
        } else if (list.size() == 1) {
            m_tax = list.get(0);
        } else {
            throw new AdempiereException("TooManyDefaults"); // Error - should only be one default
        }

        return m_tax;
    } // getDefaultTax

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "["
                + getId()
                + ", Name="
                + getName()
                + ", IsDefault="
                + isDefault()
                + ", IsActive="
                + isActive()
                + "]";
    }
} //	MTaxCategory
