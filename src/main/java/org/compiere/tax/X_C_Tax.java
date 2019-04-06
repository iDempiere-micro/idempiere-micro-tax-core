package org.compiere.tax;

import kotliquery.Row;
import org.compiere.model.HasName;
import org.compiere.model.I_C_Tax;
import org.compiere.orm.PO;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Generated Model for C_Tax
 *
 * @author iDempiere (generated)
 * @version Release 5.1 - $Id$
 */
public class X_C_Tax extends PO {

    /**
     * Both = B
     */
    public static final String SOPOTYPE_Both = "B";
    /**
     * Sales Tax = S
     */
    public static final String SOPOTYPE_SalesTax = "S";
    /**
     * Purchase Tax = P
     */
    public static final String SOPOTYPE_PurchaseTax = "P";
    /**
     *
     */
    private static final long serialVersionUID = 20171031L;

    /**
     * Standard Constructor
     */
    public X_C_Tax(int C_Tax_ID) {
        super(C_Tax_ID);
        /**
         * if (C_Tax_ID == 0) { setTaxCategoryId (0); setTaxId (0); setIsDefault (false);
         * setIsDocumentLevel (false); setIsSalesTax (false); // N setIsSummary (false); setIsTaxExempt
         * (false); setName (null); setRate (Env.ZERO); setRequiresTaxCertificate (false); setSOPOType
         * (null); // B setValidFrom (new Timestamp( System.currentTimeMillis() )); }
         */
    }

    /**
     * Load Constructor
     */
    public X_C_Tax(Row row) {
        super(row);
    }

    /**
     * AccessLevel
     *
     * @return 2 - Client
     */
    protected int getAccessLevel() {
        return I_C_Tax.accessLevel.intValue();
    }

    public String toString() {
        return "X_C_Tax[" + getId() + "]";
    }

    /**
     * Get Country Group From.
     *
     * @return Country Group From
     */
    public int getCountryGroupFromId() {
        Integer ii = (Integer) getValue(I_C_Tax.COLUMNNAME_C_CountryGroupFrom_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Country Group To.
     *
     * @return Country Group To
     */
    public int getCountryGroupToId() {
        Integer ii = (Integer) getValue(I_C_Tax.COLUMNNAME_C_CountryGroupTo_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Country.
     *
     * @return Country
     */
    public int getCountryId() {
        Integer ii = (Integer) getValue(I_C_Tax.COLUMNNAME_C_Country_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Country.
     *
     * @param C_Country_ID Country
     */
    public void setCountryId(int C_Country_ID) {
        if (C_Country_ID < 1) setValue(I_C_Tax.COLUMNNAME_C_Country_ID, null);
        else setValue(I_C_Tax.COLUMNNAME_C_Country_ID, C_Country_ID);
    }

    /**
     * Get Region.
     *
     * @return Identifies a geographical Region
     */
    public int getRegionId() {
        Integer ii = (Integer) getValue(I_C_Tax.COLUMNNAME_C_Region_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Tax Category.
     *
     * @return Tax Category
     */
    public int getTaxCategoryId() {
        Integer ii = (Integer) getValue(I_C_Tax.COLUMNNAME_C_TaxCategory_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Tax Category.
     *
     * @param C_TaxCategory_ID Tax Category
     */
    public void setTaxCategoryId(int C_TaxCategory_ID) {
        if (C_TaxCategory_ID < 1) setValue(I_C_Tax.COLUMNNAME_C_TaxCategory_ID, null);
        else setValue(I_C_Tax.COLUMNNAME_C_TaxCategory_ID, C_TaxCategory_ID);
    }

    /**
     * Get Tax.
     *
     * @return Tax identifier
     */
    public int getTaxId() {
        Integer ii = (Integer) getValue(I_C_Tax.COLUMNNAME_C_Tax_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Tax Provider.
     *
     * @return Tax Provider
     */
    public int getTaxProviderId() {
        Integer ii = (Integer) getValue(I_C_Tax.COLUMNNAME_C_TaxProvider_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set Default.
     *
     * @param IsDefault Default value
     */
    public void setIsDefault(boolean IsDefault) {
        setValue(I_C_Tax.COLUMNNAME_IsDefault, IsDefault);
    }

    /**
     * Get Default.
     *
     * @return Default value
     */
    public boolean isDefault() {
        Object oo = getValue(I_C_Tax.COLUMNNAME_IsDefault);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo);
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Document Level.
     *
     * @param IsDocumentLevel Tax is calculated on document level (rather than line by line)
     */
    public void setIsDocumentLevel(boolean IsDocumentLevel) {
        setValue(I_C_Tax.COLUMNNAME_IsDocumentLevel, IsDocumentLevel);
    }

    /**
     * Get Document Level.
     *
     * @return Tax is calculated on document level (rather than line by line)
     */
    public boolean isDocumentLevel() {
        Object oo = getValue(I_C_Tax.COLUMNNAME_IsDocumentLevel);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo);
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set Sales Tax.
     *
     * @param IsSalesTax This is a sales tax (i.e. not a value added tax)
     */
    public void setIsSalesTax(boolean IsSalesTax) {
        setValue(I_C_Tax.COLUMNNAME_IsSalesTax, IsSalesTax);
    }

    /**
     * Set Summary Level.
     *
     * @param IsSummary This is a summary entity
     */
    public void setIsSummary(boolean IsSummary) {
        setValue(I_C_Tax.COLUMNNAME_IsSummary, IsSummary);
    }

    /**
     * Get Summary Level.
     *
     * @return This is a summary entity
     */
    public boolean isSummary() {
        Object oo = getValue(I_C_Tax.COLUMNNAME_IsSummary);
        if (oo != null) {
            if (oo instanceof Boolean) return ((Boolean) oo);
            return "Y".equals(oo);
        }
        return false;
    }

    /**
     * Set SO Tax exempt.
     *
     * @param IsTaxExempt Business partner is exempt from tax on sales
     */
    public void setIsTaxExempt(boolean IsTaxExempt) {
        setValue(I_C_Tax.COLUMNNAME_IsTaxExempt, IsTaxExempt);
    }

    /**
     * Get Name.
     *
     * @return Alphanumeric identifier of the entity
     */
    public String getName() {
        return (String) getValue(HasName.COLUMNNAME_Name);
    }

    /**
     * Set Name.
     *
     * @param Name Alphanumeric identifier of the entity
     */
    public void setName(String Name) {
        setValue(HasName.COLUMNNAME_Name, Name);
    }

    /**
     * Get Parent Tax.
     *
     * @return Parent Tax indicates a tax that is made up of multiple taxes
     */
    public int getParent_TaxId() {
        Integer ii = (Integer) getValue(I_C_Tax.COLUMNNAME_Parent_Tax_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Rate.
     *
     * @return Rate or Tax or Exchange
     */
    public BigDecimal getRate() {
        BigDecimal bd = (BigDecimal) getValue(I_C_Tax.COLUMNNAME_Rate);
        if (bd == null) return Env.ZERO;
        return bd;
    }

    /**
     * Set Rate.
     *
     * @param Rate Rate or Tax or Exchange
     */
    public void setRate(BigDecimal Rate) {
        setValue(I_C_Tax.COLUMNNAME_Rate, Rate);
    }

    /**
     * Set Requires Tax Certificate.
     *
     * @param RequiresTaxCertificate This tax rate requires the Business Partner to be tax exempt
     */
    public void setRequiresTaxCertificate(boolean RequiresTaxCertificate) {
        setValue(I_C_Tax.COLUMNNAME_RequiresTaxCertificate, RequiresTaxCertificate);
    }

    /**
     * Get SO/PO Type.
     *
     * @return Sales Tax applies to sales situations, Purchase Tax to purchase situations
     */
    public String getSOPOType() {
        return (String) getValue(I_C_Tax.COLUMNNAME_SOPOType);
    }

    /**
     * Set SO/PO Type.
     *
     * @param SOPOType Sales Tax applies to sales situations, Purchase Tax to purchase situations
     */
    public void setSOPOType(String SOPOType) {

        setValue(I_C_Tax.COLUMNNAME_SOPOType, SOPOType);
    }

    /**
     * Get To.
     *
     * @return Receiving Country
     */
    public int getTo_CountryId() {
        Integer ii = (Integer) getValue(I_C_Tax.COLUMNNAME_To_Country_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Set To.
     *
     * @param To_Country_ID Receiving Country
     */
    public void setTo_CountryId(int To_Country_ID) {
        if (To_Country_ID < 1) setValue(I_C_Tax.COLUMNNAME_To_Country_ID, null);
        else setValue(I_C_Tax.COLUMNNAME_To_Country_ID, To_Country_ID);
    }

    /**
     * Get To.
     *
     * @return Receiving Region
     */
    public int getTo_RegionId() {
        Integer ii = (Integer) getValue(I_C_Tax.COLUMNNAME_To_Region_ID);
        if (ii == null) return 0;
        return ii;
    }

    /**
     * Get Valid from.
     *
     * @return Valid from including this date (first day)
     */
    public Timestamp getValidFrom() {
        return (Timestamp) getValue(I_C_Tax.COLUMNNAME_ValidFrom);
    }

    /**
     * Set Valid from.
     *
     * @param ValidFrom Valid from including this date (first day)
     */
    public void setValidFrom(Timestamp ValidFrom) {
        setValue(I_C_Tax.COLUMNNAME_ValidFrom, ValidFrom);
    }

    @Override
    public int getTableId() {
        return I_C_Tax.Table_ID;
    }
}
