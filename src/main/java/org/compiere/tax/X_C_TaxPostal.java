package org.compiere.tax;

import org.compiere.model.I_C_TaxPostal;
import org.compiere.orm.PO;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_TaxPostal
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_TaxPostal extends PO implements I_C_TaxPostal, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_TaxPostal(Properties ctx, int C_TaxPostal_ID) {
    super(ctx, C_TaxPostal_ID);
    /** if (C_TaxPostal_ID == 0) { setC_Tax_ID (0); setC_TaxPostal_ID (0); setPostal (null); } */
  }

  /** Load Constructor */
  public X_C_TaxPostal(Properties ctx, ResultSet rs) {
    super(ctx, rs);
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
   * Get Tax.
   *
   * @return Tax identifier
   */
  public int getC_Tax_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_Tax_ID);
    if (ii == null) return 0;
    return ii;
  }

    /**
   * Get ZIP.
   *
   * @return Postal code
   */
  public String getPostal() {
    return (String) get_Value(COLUMNNAME_Postal);
  }

    /**
   * Get ZIP To.
   *
   * @return Postal code to
   */
  public String getPostal_To() {
    return (String) get_Value(COLUMNNAME_Postal_To);
  }

  @Override
  public int getTableId() {
    return I_C_TaxPostal.Table_ID;
  }
}
