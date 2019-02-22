package org.compiere.tax;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;


/**
 * Tax provider model
 *
 * @author Elaine
 */
public class MTaxProvider extends X_C_TaxProvider {
    /**
     *
     */
    private static final long serialVersionUID = 6621828279540899973L;

    public MTaxProvider(Properties ctx, int C_TaxProvider_ID) {
        super(ctx, C_TaxProvider_ID);
    }

    public MTaxProvider(Properties ctx, ResultSet rs) {
        super(ctx, rs);
    }

    /**
     * Get tax provider instance
     *
     * @param provider
     * @return tax provider instance or null if not found
     */
    public static ITaxProvider getTaxProvider(
            MTaxProvider provider, ITaxProvider standardTaxProvider) {
        ITaxProvider calculator = null;
        if (provider != null) {
            if (provider.getC_TaxProvider_ID() == 0) return standardTaxProvider;

            if (!provider.isActive()) {
                s_log.log(Level.SEVERE, "Tax provider is inactive: " + provider);
                return null;
            }

            String className = provider.getTaxProviderClass();
            if (className == null || className.length() == 0) {
                s_log.log(Level.SEVERE, "Tax provider class not defined: " + provider);
                return null;
            }

        }

        return standardTaxProvider;
    }

    /**
     * Get tax provider instance
     *
     * @param provider
     * @return tax provider instance or null if not found
     */
    public static IInvoiceTaxProvider getTaxProvider(
            MTaxProvider provider, IInvoiceTaxProvider standardTaxProvider) {
        IInvoiceTaxProvider calculator = null;
        if (provider != null) {
            if (provider.getC_TaxProvider_ID() == 0) return standardTaxProvider;

            if (!provider.isActive()) {
                s_log.log(Level.SEVERE, "Tax provider is inactive: " + provider);
                return null;
            }

            String className = provider.getTaxProviderClass();
            if (className == null || className.length() == 0) {
                s_log.log(Level.SEVERE, "Tax provider class not defined: " + provider);
                return null;
            }

        }

        return standardTaxProvider;
    }

    public String getTaxProviderClass() {
        return getC_TaxProviderCfg().getTaxProviderClass();
    }
}
