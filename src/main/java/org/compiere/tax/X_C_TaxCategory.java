package org.compiere.tax;

import org.compiere.model.I_C_TaxCategory;
import org.compiere.orm.BasePOName;
import org.idempiere.orm.I_Persistent;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_TaxCategory
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_TaxCategory extends BasePOName implements I_C_TaxCategory, I_Persistent {

  /** */
  private static final long serialVersionUID = 20171031L;

  /** Standard Constructor */
  public X_C_TaxCategory(Properties ctx, int C_TaxCategory_ID, String trxName) {
    super(ctx, C_TaxCategory_ID, trxName);
    /**
     * if (C_TaxCategory_ID == 0) { setC_TaxCategory_ID (0); setIsDefault (false); setName (null); }
     */
  }

  /** Load Constructor */
  public X_C_TaxCategory(Properties ctx, ResultSet rs, String trxName) {
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
    StringBuffer sb = new StringBuffer("X_C_TaxCategory[").append(getId()).append("]");
    return sb.toString();
  }

  /**
   * Set Commodity Code.
   *
   * @param CommodityCode Commodity code used for tax calculation
   */
  public void setCommodityCode(String CommodityCode) {
    set_Value(COLUMNNAME_CommodityCode, CommodityCode);
  }

  /**
   * Get Commodity Code.
   *
   * @return Commodity code used for tax calculation
   */
  public String getCommodityCode() {
    return (String) get_Value(COLUMNNAME_CommodityCode);
  }

  /**
   * Set Tax Category.
   *
   * @param C_TaxCategory_ID Tax Category
   */
  public void setC_TaxCategory_ID(int C_TaxCategory_ID) {
    if (C_TaxCategory_ID < 1) set_ValueNoCheck(COLUMNNAME_C_TaxCategory_ID, null);
    else set_ValueNoCheck(COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
  }

  /**
   * Get Tax Category.
   *
   * @return Tax Category
   */
  public int getC_TaxCategory_ID() {
    Integer ii = (Integer) get_Value(COLUMNNAME_C_TaxCategory_ID);
    if (ii == null) return 0;
    return ii;
  }

  /**
   * Set C_TaxCategory_UU.
   *
   * @param C_TaxCategory_UU C_TaxCategory_UU
   */
  public void setC_TaxCategory_UU(String C_TaxCategory_UU) {
    set_Value(COLUMNNAME_C_TaxCategory_UU, C_TaxCategory_UU);
  }

  /**
   * Get C_TaxCategory_UU.
   *
   * @return C_TaxCategory_UU
   */
  public String getC_TaxCategory_UU() {
    return (String) get_Value(COLUMNNAME_C_TaxCategory_UU);
  }

  /**
   * Set Description.
   *
   * @param Description Optional short description of the record
   */
  public void setDescription(String Description) {
    set_Value(COLUMNNAME_Description, Description);
  }

  /**
   * Get Description.
   *
   * @return Optional short description of the record
   */
  public String getDescription() {
    return (String) get_Value(COLUMNNAME_Description);
  }

  /**
   * Set Default.
   *
   * @param IsDefault Default value
   */
  public void setIsDefault(boolean IsDefault) {
    set_Value(COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
  }

  /**
   * Get Default.
   *
   * @return Default value
   */
  public boolean isDefault() {
    Object oo = get_Value(COLUMNNAME_IsDefault);
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
