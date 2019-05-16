package org.compiere.tax;

import kotliquery.Row;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxPostal;
import org.compiere.orm.Query;
import org.compiere.orm.TimeUtil;
import org.compiere.util.MsgKt;
import org.idempiere.common.util.CCache;
import org.idempiere.common.util.Env;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;

/**
 * Tax Model
 *
 * @author Jorg Janke
 * @version $Id: MTax.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $ red1 - FR: [ 2214883 ] Remove SQL
 * code and Replace for Query trifonnt - BF [2913276] - Allow only one Default Tax Rate per Tax
 * Category mjmckay - BF [2948632] - Allow edits to the Default Tax Rate
 */
public class MTax extends X_C_Tax implements I_C_Tax {
    /**
     *
     */
    private static final long serialVersionUID = 5871827364071851846L;

    /**
     * Cache
     */
    private static CCache<Integer, MTax> s_cache = new CCache<>(I_C_Tax.Table_Name, 5);
    /**
     * Cache of Client
     */
    private static CCache<Integer, MTax[]> s_cacheAll =
            new CCache<>(I_C_Tax.Table_Name, I_C_Tax.Table_Name + "_Of_Client", 5);

    /**
     * Child Taxes
     */
    private MTax[] m_childTaxes = null;
    /**
     * Postal Codes
     */
    private MTaxPostal[] m_postals = null;

    /**
     * ************************************************************************ Standard Constructor
     *
     * @param C_Tax_ID id
     */
    public MTax(int C_Tax_ID) {
        super(C_Tax_ID);
        if (C_Tax_ID == 0) {
            setIsDefault(false);
            setIsDocumentLevel(true);
            setIsSummary(false);
            setIsTaxExempt(false);
            setRate(Env.ZERO);
            setRequiresTaxCertificate(false);
            setSOPOType(X_C_Tax.SOPOTYPE_Both);
            setValidFrom(TimeUtil.getDay(1990, 1, 1));
            setIsSalesTax(false);
        }
    } //	MTax

    /**
     * Load Constructor
     *
     * @param ctx context
     */
    public MTax(Row row) {
        super(row);
    } //	MTax

    /**
     * New Constructor
     *
     * @param Name
     * @param Rate
     * @param C_TaxCategory_ID
     */
    public MTax(String Name, BigDecimal Rate, int C_TaxCategory_ID) {
        this(0);
        setName(Name);
        setRate(Rate == null ? Env.ZERO : Rate);
        setTaxCategoryId(C_TaxCategory_ID); // 	FK
    } //	MTax

    /**
     * Get All Tax codes (for AD_Client)
     *
     * @return MTax
     */
    public static MTax[] getAll() {
        int AD_Client_ID = Env.getClientId();
        MTax[] retValue = s_cacheAll.get(AD_Client_ID);
        if (retValue != null) return retValue;

        //	Create it
        // FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
        List<MTax> list =
                new Query(I_C_Tax.Table_Name, null)
                        .setClientId()
                        .setOrderBy(
                                "C_CountryGroupFrom_ID, C_Country_ID, C_Region_ID, C_CountryGroupTo_ID, To_Country_ID, To_Region_ID, ValidFrom DESC")
                        .setOnlyActiveRecords(true)
                        .list();
        for (MTax tax : list) {
            s_cache.put(tax.getId(), tax);
        }
        retValue = list.toArray(new MTax[0]);
        s_cacheAll.put(AD_Client_ID, retValue);
        return retValue;
    } //	getAll

    /**
     * Get Tax from Cache
     *
     * @param C_Tax_ID id
     * @return MTax
     */
    public static MTax get(int C_Tax_ID) {
        Integer key = C_Tax_ID;
        MTax retValue = s_cache.get(key);
        if (retValue != null) return retValue;
        retValue = new MTax(C_Tax_ID);
        if (retValue.getId() != 0) s_cache.put(key, retValue);
        return retValue;
    } //	get

    /**
     * Get Child Taxes
     *
     * @param requery reload
     * @return array of taxes or null
     */
    public MTax[] getChildTaxes(boolean requery) {
        if (!isSummary()) return null;
        if (m_childTaxes != null && !requery) return m_childTaxes;
        //
        // FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
        final String whereClause = I_C_Tax.COLUMNNAME_Parent_Tax_ID + "=?";
        List<MTax> list =
                new Query(I_C_Tax.Table_Name, whereClause)
                        .setParameters(getTaxId())
                        .setOnlyActiveRecords(true)
                        .list();
        // red1 - end -

        m_childTaxes = new MTax[list.size()];
        list.toArray(m_childTaxes);
        return m_childTaxes;
    } //	getChildTaxes

    /**
     * Get Postal Qualifiers
     *
     * @param requery requery
     * @return array of postal codes
     */
    public MTaxPostal[] getPostals(boolean requery) {
        if (m_postals != null && !requery) return m_postals;

        // FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
        final String whereClause = MTaxPostal.COLUMNNAME_C_Tax_ID + "=?";
        List<MTaxPostal> list =
                new Query(I_C_TaxPostal.Table_Name, whereClause)
                        .setParameters(getTaxId())
                        .setOnlyActiveRecords(true)
                        .setOrderBy(I_C_TaxPostal.COLUMNNAME_Postal + ", " + I_C_TaxPostal.COLUMNNAME_Postal_To)
                        .list();
        // red1 - end -

        if (list.size() > 0) {
            m_postals = new MTaxPostal[list.size()];
            list.toArray(m_postals);
        }
        return m_postals;
    } //	getPostals

    /**
     * Do we have Postal Codes
     *
     * @return true if postal codes exist
     */
    public boolean isPostal() {
        if (getPostals(false) == null) return false;

        return getPostals(false).length > 0;
    } //	isPostal

    /**
     * Is Zero Tax
     *
     * @return true if tax rate is 0
     */
    public boolean isZeroTax() {
        return getRate().signum() == 0;
    } //	isZeroTax

    public String toString() {
        return "MTax[" +
                getId() +
                ", Name = " +
                getName() +
                ", SO/PO=" +
                getSOPOType() +
                ", Rate=" +
                getRate() +
                ", C_TaxCategory_ID=" +
                getTaxCategoryId() +
                ", Summary=" +
                isSummary() +
                ", Parent=" +
                getParent_TaxId() +
                ", Country=" +
                getCountryId() +
                "|" +
                getTo_CountryId() +
                ", Region=" +
                getRegionId() +
                "|" +
                getTo_RegionId() +
                "]";
    } //	toString

    /**
     * Calculate Tax - no rounding
     *
     * @param amount      amount
     * @param taxIncluded if true tax is calculated from gross otherwise from net
     * @param scale       scale
     * @return tax amount
     */
    public BigDecimal calculateTax(BigDecimal amount, boolean taxIncluded, int scale) {
        //	Null Tax
        if (isZeroTax()) return Env.ZERO;

        MTax[] taxarray;
        if (isSummary()) taxarray = getChildTaxes(false);
        else taxarray = new MTax[]{this};

        BigDecimal tax = Env.ZERO;
        for (MTax taxc : taxarray) {
            BigDecimal multiplier = taxc.getRate().divide(Env.ONEHUNDRED, 12, BigDecimal.ROUND_HALF_UP);
            if (!taxIncluded) //	$100 * 6 / 100 == $6 == $100 * 0.06
            {
                BigDecimal itax = amount.multiply(multiplier).setScale(scale, BigDecimal.ROUND_HALF_UP);
                tax = tax.add(itax);
            } else //	$106 - ($106 / (100+6)/100) == $6 == $106 - ($106/1.06)
            {
                multiplier = multiplier.add(Env.ONE);
                BigDecimal base = amount.divide(multiplier, 12, BigDecimal.ROUND_HALF_UP);
                BigDecimal itax = amount.subtract(base).setScale(scale, BigDecimal.ROUND_HALF_UP);
                tax = tax.add(itax);
            }
        }
        if (log.isLoggable(Level.FINE))
            log.fine(
                    "calculateTax "
                            + amount
                            + " (incl="
                            + taxIncluded
                            + ",scale="
                            + scale
                            + ") = "
                            + tax
                            + " ["
                            + tax
                            + "]");
        return tax;
    } //	calculateTax

    @Override
    protected boolean beforeSave(boolean newRecord) {
        if (isDefault()) {
            // @Trifon - Ensure that only one tax rate is set as Default!
            // @Mckay - Allow edits to the Default tax rate
            String whereClause =
                    I_C_Tax.COLUMNNAME_C_TaxCategory_ID
                            + "=? AND "
                            + I_C_Tax.COLUMNNAME_C_Tax_ID
                            + "<>? AND "
                            + "IsDefault='Y'";
            List<MTax> list =
                    new Query(I_C_Tax.Table_Name, whereClause)
                            .setParameters(getTaxCategoryId(), getTaxId())
                            .setOnlyActiveRecords(true)
                            .list();
            if (list.size() >= 1) {
                log.saveError(
                        "Error",
                        MsgKt.parseTranslation(
                                MsgKt.getMsg("OnlyOneTaxPerCategoryMarkedDefault")));
                return false;
            }
        }
        if (getCountryId() > 0 && getCountryGroupFromId() > 0) {
            setCountryId(0);
        }
        if (getTo_CountryId() > 0 && getCountryGroupToId() > 0) {
            setTo_CountryId(0);
        }

        return super.beforeSave(newRecord);
    }

    /**
     * After Save
     *
     * @param newRecord new
     * @param success   success
     * @return success
     */
    protected boolean afterSave(boolean newRecord, boolean success) {
        if (newRecord && success) insertAccounting("C_Tax_Acct", "C_AcctSchema_Default", null);

        return success;
    } //	afterSave
} //	MTax
