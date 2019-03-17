package org.compiere.tax;

import kotliquery.Row;
import org.compiere.model.I_C_TaxProvider;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;

import java.util.Properties;

/**
 * Generated Model for C_TaxProvider
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_TaxProvider extends PO implements I_C_TaxProvider {

    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_TaxProvider(Properties ctx, int C_TaxProvider_ID) {
        super(ctx, C_TaxProvider_ID);
        /**
         * if (C_TaxProvider_ID == 0) { setTaxProviderConfigurationId (0); setTaxProviderId (0); setName
         * (null); setSeqNo (0); // 0 }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_TaxProvider(Properties ctx, Row row) {
        super(ctx, row);
    }

    /**
     * AccessLevel
     *
     * @return 3 - Client - Org
     */
    protected int getAccessLevel() {
        return accessLevel.intValue();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("X_C_TaxProvider[").append(getId()).append("]");
        return sb.toString();
    }

    public org.compiere.model.I_C_TaxProviderCfg getTaxProviderConfiguration() throws RuntimeException {
        return (org.compiere.model.I_C_TaxProviderCfg)
                MTable.get(getCtx(), org.compiere.model.I_C_TaxProviderCfg.Table_Name)
                        .getPO(getTaxProviderConfigurationId());
    }

    /**
     * Get Tax Provider Configuration.
     *
     * @return Tax Provider Configuration
     */
    public int getTaxProviderConfigurationId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_TaxProviderCfg_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Tax Provider.
     *
     * @return Tax Provider
     */
    public int getTaxProviderId() {
        Integer ii = (Integer) getValue(COLUMNNAME_C_TaxProvider_ID);
        if (ii == null) return 0;
        return ii;
    }

    @Override
    public int getTableId() {
        return I_C_TaxProvider.Table_ID;
    }
}
