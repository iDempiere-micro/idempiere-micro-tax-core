package org.compiere.tax;

import org.compiere.model.I_C_TaxPostal;
import org.compiere.orm.MTable;
import org.compiere.orm.PO;
import org.idempiere.common.util.KeyNamePair;
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
  public X_C_TaxPostal(Properties ctx, int C_TaxPostal_ID, String trxName) {
    super(ctx, C_TaxPostal_ID, trxName);
    /** if (C_TaxPostal_ID == 0) { setC_Tax_ID (0); setC_TaxPostal_ID (0); setPostal (null); } */
  }

  /** Load Constructor */
  public X_C_TaxPostal(Properties ctx, ResultSet rs, String trxName) {
    super(ctx, rs, trxName);
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

  public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException {
    return (org.compiere.model.I_C_Tax)
        MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_Name)
            .getPO(getC_Tax_ID(), get_TrxName());
  }

  /**
   * Set Tax.
   *
   * @param C_Tax_ID Tax identifier
   */
  public void setC_Tax_ID(int C_Tax_ID) {
    if (C_Tax_ID < 1) set_ValueNoCheck(COLUMNNAME_C_Tax_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
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
   * Set Tax ZIP.
   *
   * @param C_TaxPostal_ID Tax Postal/ZIP
   */
  public void setC_TaxPostal_ID(int C_TaxPostal_ID) {
    if (C_TaxPostal_ID < 1) set_ValueNoCheck(COLUMNNAME_C_TaxPostal_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_TaxPostal_ID, Integer.valueOf(C_TaxPostal_ID));
  }

  /**
   * Get Tax ZIP.
   *
   * @return Tax Postal/ZIP
   */
  public int getC_TaxPostal_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_TaxPostal_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_TaxPostal_UU.
   *
   * @param C_TaxPostal_UU C_TaxPostal_UU
   */
  public void setC_TaxPostal_UU(String C_TaxPostal_UU) {
    set_Value(COLUMNNAME_C_TaxPostal_UU, C_TaxPostal_UU);
  }

  /**
   * Get C_TaxPostal_UU.
   *
   * @return C_TaxPostal_UU
   */
  public String getC_TaxPostal_UU() {
    return (String) get_Value(COLUMNNAME_C_TaxPostal_UU);
  }

  /**
   * Set ZIP.
   *
   * @param Postal Postal code
   */
  public void setPostal(String Postal) {
    set_Value(COLUMNNAME_Postal, Postal);
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
   * Get Record ID/ColumnName
   *
   * @return ID/ColumnName pair
   */
  public KeyNamePair getKeyNamePair() {
    return new KeyNamePair(getId(), getPostal());
  }

  /**
   * Set ZIP To.
   *
   * @param Postal_To Postal code to
   */
  public void setPostal_To(String Postal_To) {
    set_Value(COLUMNNAME_Postal_To, Postal_To);
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
