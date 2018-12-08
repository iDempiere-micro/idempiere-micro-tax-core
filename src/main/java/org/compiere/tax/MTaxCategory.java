package org.compiere.tax;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.orm.Query;
import org.idempiere.common.exceptions.AdempiereException;

/**
 * Tax Category Model
 *
 * @author Jorg Janke
 * @version $Id: MTaxCategory.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MTaxCategory extends X_C_TaxCategory {
  /** */
  private static final long serialVersionUID = -5521670797405300136L;

  /**
   * Standard Constructor
   *
   * @param ctx context
   * @param C_TaxCategory_ID id
   * @param trxName trx
   */
  public MTaxCategory(Properties ctx, int C_TaxCategory_ID, String trxName) {
    super(ctx, C_TaxCategory_ID, trxName);
    if (C_TaxCategory_ID == 0) {
      //	setName (null);
      setIsDefault(false);
    }
  } //	MTaxCategory

  /**
   * Load Constructor
   *
   * @param ctx context
   * @param rs result set
   * @param trxName trx
   */
  public MTaxCategory(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
  } //	MTaxCategory

  /** getDefaultTax Get the default tax id associated with this tax category */
  public MTax getDefaultTax() {
    MTax m_tax = new MTax(getCtx(), 0, get_TrxName());

    final String whereClause =
        I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID
            + "=? AND "
            + I_C_TaxCategory.COLUMNNAME_IsDefault
            + "='Y'";
    List<MTax> list =
        new Query(getCtx(), I_C_Tax.Table_Name, whereClause, get_TrxName())
            .setParameters(getC_TaxCategory_ID())
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
