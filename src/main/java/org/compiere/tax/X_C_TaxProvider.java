package org.compiere.tax;

import kotliquery.Row;
import org.compiere.model.I_C_TaxProvider;
import org.compiere.model.I_C_TaxProviderCfg;
import org.compiere.orm.PO;
import software.hsharp.core.orm.MBaseTableKt;

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
    public X_C_TaxProvider(int C_TaxProvider_ID) {
        super(C_TaxProvider_ID);
    }

    /**
     * Load Constructor
     */
    public X_C_TaxProvider(Row row) {
        super(row);
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
        return "X_C_TaxProvider[" + getId() + "]";
    }

    public I_C_TaxProviderCfg getTaxProviderConfiguration() throws RuntimeException {
        return (I_C_TaxProviderCfg)
                MBaseTableKt.getTable(I_C_TaxProviderCfg.Table_Name)
                        .getPO(getTaxProviderConfigurationId());
    }

    /**
     * Get Tax Provider Configuration.
     *
     * @return Tax Provider Configuration
     */
    public int getTaxProviderConfigurationId() {
        Integer ii = getValue(COLUMNNAME_C_TaxProviderCfg_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Tax Provider.
     *
     * @return Tax Provider
     */
    public int getTaxProviderId() {
        Integer ii = getValue(COLUMNNAME_C_TaxProvider_ID);
        if (ii == null) return 0;
        return ii;
    }

    @Override
    public int getTableId() {
        return I_C_TaxProvider.Table_ID;
    }
}
