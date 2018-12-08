package org.compiere.tax;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Tax Postal Model
 *
 * @author Jorg Janke
 * @version $Id: MTaxPostal.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MTaxPostal extends X_C_TaxPostal {
  /** */
  private static final long serialVersionUID = -7048614254051075174L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param C_TaxPostal_ID id
   * @param trxName transaction
   */
  public MTaxPostal(Properties ctx, int C_TaxPostal_ID, String trxName) {
    super(ctx, C_TaxPostal_ID, trxName);
  } //	MTaxPostal

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName transaction
   */
  public MTaxPostal(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MTaxPostal
} //	MTaxPostal
