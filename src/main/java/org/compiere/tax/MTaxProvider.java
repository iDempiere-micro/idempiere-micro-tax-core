package org.compiere.tax;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.orm.PO;
import org.idempiere.common.base.Service;

/**
 * Tax provider model
 *
 * @author Elaine
 */
public class MTaxProvider extends X_C_TaxProvider {
  /** */
  private static final long serialVersionUID = 6621828279540899973L;

  public MTaxProvider(Properties ctx, int C_TaxProvider_ID, String trxName) {
    super(ctx, C_TaxProvider_ID, trxName);
  }

  public MTaxProvider(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  }

  public String getTaxProviderClass() {
    return getC_TaxProviderCfg().getTaxProviderClass();
  }

  public String getURL() {
    return getC_TaxProviderCfg().getURL();
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

      List<ITaxProviderFactory> factoryList =
          Service.Companion.locator().list(ITaxProviderFactory.class).getServices();
      if (factoryList == null) return null;
      for (ITaxProviderFactory factory : factoryList) {
        calculator = factory.newTaxProviderInstance(className);
        if (calculator != null) return calculator;
      }
    }

    return null;
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

      List<ITaxProviderFactory> factoryList =
          Service.Companion.locator().list(ITaxProviderFactory.class).getServices();
      if (factoryList == null) return null;
      for (ITaxProviderFactory factory : factoryList) {
        calculator = PO.as(IInvoiceTaxProvider.class, factory.newTaxProviderInstance(className));
        if (calculator != null) return calculator;
      }
    }

    return null;
  }
}
