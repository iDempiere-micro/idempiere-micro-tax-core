package org.compiere.tax;

import org.compiere.crm.MCountryGroup;
import org.compiere.crm.MLocation;
import org.idempiere.common.exceptions.DBException;
import org.idempiere.common.util.CLogMgt;
import org.idempiere.common.util.CLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;

import static software.hsharp.core.util.DBKt.getSQLValueEx;
import static software.hsharp.core.util.DBKt.prepareStatement;

/**
 * Tax Handling
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <li>FR [ 2758097 ] Implement TaxNotFoundException
 * @version $Id: Tax.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class Tax {
    /**
     * Logger
     */
    private static CLogger log = CLogger.getCLogger(Tax.class);

    /**
     * @param M_Product_ID
     * @param C_Charge_ID
     * @param billDate
     * @param shipDate
     * @param AD_Org_ID
     * @param M_Warehouse_ID
     * @param billC_BPartner_Location_ID
     * @param shipC_BPartner_Location_ID
     * @param IsSOTrx
     * @return
     * @deprecated
     */
    public static int get(

            int M_Product_ID,
            int C_Charge_ID,
            Timestamp billDate,
            Timestamp shipDate,
            int AD_Org_ID,
            int M_Warehouse_ID,
            int billC_BPartner_Location_ID,
            int shipC_BPartner_Location_ID,
            boolean IsSOTrx) {
        return get(

                M_Product_ID,
                C_Charge_ID,
                billDate,
                shipDate,
                AD_Org_ID,
                M_Warehouse_ID,
                billC_BPartner_Location_ID,
                shipC_BPartner_Location_ID,
                IsSOTrx,
                null);
    }

    /**
     * ************************************************************************ Get Tax ID - converts
     * parameters to call Get Tax.
     *
     * <pre>
     * 	M_Product_ID/C_Charge_ID	->	C_TaxCategory_ID
     * 	billDate, shipDate			->	billDate, shipDate
     * 	AD_Org_ID					->	billFromC_Location_ID
     * 	M_Warehouse_ID				->	shipFromC_Location_ID
     * 	billC_BPartner_Location_ID  ->	billToC_Location_ID
     * 	shipC_BPartner_Location_ID 	->	shipToC_Location_ID
     *
     *  if IsSOTrx is false, bill and ship are reversed
     *  </pre>
     *
     * @param M_Product_ID               product
     * @param C_Charge_ID                product
     * @param billDate                   invoice date
     * @param shipDate                   ship date (ignored)
     * @param AD_Org_ID                  org
     * @param M_Warehouse_ID             warehouse (ignored)
     * @param billC_BPartner_Location_ID invoice location
     * @param shipC_BPartner_Location_ID ship location (ignored)
     * @param IsSOTrx                    is a sales trx
     * @return C_Tax_ID
     * @throws TaxCriteriaNotFoundException if a criteria was not found
     */
    public static int get(

            int M_Product_ID,
            int C_Charge_ID,
            Timestamp billDate,
            Timestamp shipDate,
            int AD_Org_ID,
            int M_Warehouse_ID,
            int billC_BPartner_Location_ID,
            int shipC_BPartner_Location_ID,
            boolean IsSOTrx,
            String trxName) {
        if (M_Product_ID != 0)
            return getProduct(

                    M_Product_ID,
                    billDate,
                    shipDate,
                    AD_Org_ID,
                    M_Warehouse_ID,
                    billC_BPartner_Location_ID,
                    shipC_BPartner_Location_ID,
                    IsSOTrx,
                    trxName);
        else if (C_Charge_ID != 0)
            return getCharge(

                    C_Charge_ID,
                    billDate,
                    shipDate,
                    AD_Org_ID,
                    M_Warehouse_ID,
                    billC_BPartner_Location_ID,
                    shipC_BPartner_Location_ID,
                    IsSOTrx
            );
        else return getExemptTax(AD_Org_ID);
    } //	get

    /**
     * Get Tax ID - converts parameters to call Get Tax.
     *
     * <pre>
     * 	C_Charge_ID					->	C_TaxCategory_ID
     * 	billDate					->	billDate
     * 	shipDate					->	shipDate (ignored)
     * 	AD_Org_ID					->	billFromC_Location_ID
     * 	M_Warehouse_ID				->	shipFromC_Location_ID (ignored)
     * 	billC_BPartner_Location_ID  ->	billToC_Location_ID
     * 	shipC_BPartner_Location_ID 	->	shipToC_Location_ID (ignored)
     *
     *  if IsSOTrx is false, bill and ship are reversed
     *  </pre>
     *
     * @param C_Charge_ID                product
     * @param billDate                   invoice date
     * @param shipDate                   ship date (ignored)
     * @param AD_Org_ID                  org
     * @param M_Warehouse_ID             warehouse (ignored)
     * @param billC_BPartner_Location_ID invoice location
     * @param shipC_BPartner_Location_ID ship location (ignored)
     * @param IsSOTrx                    is a sales trx
     * @return C_Tax_ID
     * @throws TaxForChangeNotFoundException if criteria not found for given change
     * @throws TaxCriteriaNotFoundException  if a criteria was not found
     */
    public static int getCharge(

            int C_Charge_ID,
            Timestamp billDate,
            Timestamp shipDate,
            int AD_Org_ID,
            int M_Warehouse_ID,
            int billC_BPartner_Location_ID,
            int shipC_BPartner_Location_ID,
            boolean IsSOTrx) {
        int C_TaxCategory_ID = 0;
        int shipFromC_Location_ID = 0;
        int shipToC_Location_ID = 0;
        int billFromC_Location_ID = 0;
        int billToC_Location_ID = 0;
        String IsTaxExempt = null;
        String IsSOTaxExempt;
        String IsPOTaxExempt;

        //	Get all at once
        String sql =
                "SELECT c.C_TaxCategory_ID, o.C_Location_ID, il.C_Location_ID, b.IsTaxExempt, b.IsPOTaxExempt,"
                        + " w.C_Location_ID, sl.C_Location_ID "
                        + "FROM C_Charge c, AD_OrgInfo o,"
                        + " C_BPartner_Location il INNER JOIN C_BPartner b ON (il.C_BPartner_ID=b.C_BPartner_ID) "
                        + " LEFT OUTER JOIN M_Warehouse w ON (w.M_Warehouse_ID=?), C_BPartner_Location sl "
                        + "WHERE c.C_Charge_ID=?"
                        + " AND o.AD_Org_ID=?"
                        + " AND il.C_BPartner_Location_ID=?"
                        + " AND sl.C_BPartner_Location_ID=?";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, M_Warehouse_ID);
            pstmt.setInt(2, C_Charge_ID);
            pstmt.setInt(3, AD_Org_ID);
            pstmt.setInt(4, billC_BPartner_Location_ID);
            pstmt.setInt(5, shipC_BPartner_Location_ID);
            rs = pstmt.executeQuery();
            boolean found = false;
            if (rs.next()) {
                C_TaxCategory_ID = rs.getInt(1);
                billFromC_Location_ID = rs.getInt(2);
                billToC_Location_ID = rs.getInt(3);
                IsSOTaxExempt = rs.getString(4);
                IsPOTaxExempt = rs.getString(5);
                IsTaxExempt = IsSOTrx ? IsSOTaxExempt : IsPOTaxExempt;
                shipFromC_Location_ID = rs.getInt(6);
                shipToC_Location_ID = rs.getInt(7);
                found = true;
            }
            //
            if (!found) {
                throw new TaxForChangeNotFoundException(
                        C_Charge_ID,
                        AD_Org_ID,
                        M_Warehouse_ID,
                        billC_BPartner_Location_ID,
                        shipC_BPartner_Location_ID,
                        null);
            } else if ("Y".equals(IsTaxExempt)) {
                return getExemptTax(AD_Org_ID);
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }

        //	Reverese for PO
        if (!IsSOTrx) {
            int temp = billFromC_Location_ID;
            billFromC_Location_ID = billToC_Location_ID;
            billToC_Location_ID = temp;
            temp = shipFromC_Location_ID;
            shipFromC_Location_ID = shipToC_Location_ID;
            shipToC_Location_ID = temp;
        }
        //
        if (log.isLoggable(Level.FINE))
            log.fine(
                    "getCharge - C_TaxCategory_ID="
                            + C_TaxCategory_ID
                            + ", billFromC_Location_ID="
                            + billFromC_Location_ID
                            + ", billToC_Location_ID="
                            + billToC_Location_ID
                            + ", shipFromC_Location_ID="
                            + shipFromC_Location_ID
                            + ", shipToC_Location_ID="
                            + shipToC_Location_ID);
        return get(

                C_TaxCategory_ID,
                IsSOTrx,
                shipDate,
                shipFromC_Location_ID,
                shipToC_Location_ID,
                billDate,
                billFromC_Location_ID,
                billToC_Location_ID
        );
    } //	getCharge

    /**
     * @param M_Product_ID
     * @param billDate
     * @param shipDate
     * @param AD_Org_ID
     * @param M_Warehouse_ID
     * @param billC_BPartner_Location_ID
     * @param shipC_BPartner_Location_ID
     * @param IsSOTrx
     * @return
     * @deprecated
     */
    public static int getProduct(

            int M_Product_ID,
            Timestamp billDate,
            Timestamp shipDate,
            int AD_Org_ID,
            int M_Warehouse_ID,
            int billC_BPartner_Location_ID,
            int shipC_BPartner_Location_ID,
            boolean IsSOTrx) {
        return getProduct(

                M_Product_ID,
                billDate,
                shipDate,
                AD_Org_ID,
                M_Warehouse_ID,
                billC_BPartner_Location_ID,
                shipC_BPartner_Location_ID,
                IsSOTrx,
                null);
    }

    /**
     * Get Tax ID - converts parameters to call Get Tax.
     *
     * <pre>
     * 	M_Product_ID				->	C_TaxCategory_ID
     * 	billDate					->	billDate
     * 	shipDate					->	shipDate (ignored)
     * 	AD_Org_ID					->	billFromC_Location_ID
     * 	M_Warehouse_ID				->	shipFromC_Location_ID (ignored)
     * 	billC_BPartner_Location_ID  ->	billToC_Location_ID
     * 	shipC_BPartner_Location_ID 	->	shipToC_Location_ID (ignored)
     *
     *  if IsSOTrx is false, bill and ship are reversed
     *  </pre>
     *
     * @param M_Product_ID               product
     * @param billDate                   invoice date
     * @param shipDate                   ship date (ignored)
     * @param AD_Org_ID                  org
     * @param M_Warehouse_ID             warehouse (ignored)
     * @param billC_BPartner_Location_ID invoice location
     * @param shipC_BPartner_Location_ID ship location (ignored)
     * @param IsSOTrx                    is a sales trx
     * @return C_Tax_ID If error it returns 0 and sets error log (TaxCriteriaNotFound)
     */
    public static int getProduct(

            int M_Product_ID,
            Timestamp billDate,
            Timestamp shipDate,
            int AD_Org_ID,
            int M_Warehouse_ID,
            int billC_BPartner_Location_ID,
            int shipC_BPartner_Location_ID,
            boolean IsSOTrx,
            String trxName) {
        String variable = "";
        int C_TaxCategory_ID = 0;
        int shipFromC_Location_ID = 0;
        int shipToC_Location_ID = 0;
        int billFromC_Location_ID = 0;
        int billToC_Location_ID = 0;
        String IsTaxExempt = null;
        String IsSOTaxExempt = null;
        String IsPOTaxExempt = null;

        String sql = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //	Get all at once
            sql =
                    "SELECT p.C_TaxCategory_ID, o.C_Location_ID, il.C_Location_ID, b.IsTaxExempt, b.IsPOTaxExempt, "
                            + " w.C_Location_ID, sl.C_Location_ID "
                            + "FROM M_Product p, AD_OrgInfo o,"
                            + " C_BPartner_Location il INNER JOIN C_BPartner b ON (il.C_BPartner_ID=b.C_BPartner_ID) "
                            + " LEFT OUTER JOIN M_Warehouse w ON (w.M_Warehouse_ID=?), C_BPartner_Location sl "
                            + "WHERE p.M_Product_ID=?"
                            + " AND o.AD_Org_ID=?"
                            + " AND il.C_BPartner_Location_ID=?"
                            + " AND sl.C_BPartner_Location_ID=?";
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, M_Warehouse_ID);
            pstmt.setInt(2, M_Product_ID);
            pstmt.setInt(3, AD_Org_ID);
            pstmt.setInt(4, billC_BPartner_Location_ID);
            pstmt.setInt(5, shipC_BPartner_Location_ID);
            rs = pstmt.executeQuery();
            boolean found = false;
            if (rs.next()) {
                C_TaxCategory_ID = rs.getInt(1);
                billFromC_Location_ID = rs.getInt(2);
                billToC_Location_ID = rs.getInt(3);
                IsSOTaxExempt = rs.getString(4);
                IsPOTaxExempt = rs.getString(5);
                IsTaxExempt = IsSOTrx ? IsSOTaxExempt : IsPOTaxExempt;
                shipFromC_Location_ID = rs.getInt(6);
                shipToC_Location_ID = rs.getInt(7);
                found = true;
            }
            //
            if (found && "Y".equals(IsTaxExempt)) {
                if (log.isLoggable(Level.FINE)) log.fine("getProduct - Business Partner is Tax exempt");
                return getExemptTax(AD_Org_ID);
            } else if (found) {
                if (!IsSOTrx) {
                    int temp = billFromC_Location_ID;
                    billFromC_Location_ID = billToC_Location_ID;
                    billToC_Location_ID = temp;
                    temp = shipFromC_Location_ID;
                    shipFromC_Location_ID = shipToC_Location_ID;
                    shipToC_Location_ID = temp;
                }
                if (log.isLoggable(Level.FINE))
                    log.fine(
                            "getProduct - C_TaxCategory_ID="
                                    + C_TaxCategory_ID
                                    + ", billFromC_Location_ID="
                                    + billFromC_Location_ID
                                    + ", billToC_Location_ID="
                                    + billToC_Location_ID
                                    + ", shipFromC_Location_ID="
                                    + shipFromC_Location_ID
                                    + ", shipToC_Location_ID="
                                    + shipToC_Location_ID);
                return get(

                        C_TaxCategory_ID,
                        IsSOTrx,
                        shipDate,
                        shipFromC_Location_ID,
                        shipToC_Location_ID,
                        billDate,
                        billFromC_Location_ID,
                        billToC_Location_ID
                );
            }

            // ----------------------------------------------------------------

            //	Detail for error isolation

            //	M_Product_ID				->	C_TaxCategory_ID
            variable = "M_Product_ID";
            sql = "SELECT C_TaxCategory_ID FROM M_Product WHERE M_Product_ID=?";
            C_TaxCategory_ID = getSQLValueEx(trxName, sql, M_Product_ID);
            found = C_TaxCategory_ID != -1;
            if (C_TaxCategory_ID <= 0) {
                throw new TaxCriteriaNotFoundException(variable, M_Product_ID);
            }
            if (log.isLoggable(Level.FINE)) log.fine("getProduct - C_TaxCategory_ID=" + C_TaxCategory_ID);

            //	AD_Org_ID					->	billFromC_Location_ID
            variable = "AD_Org_ID";
            sql = "SELECT C_Location_ID FROM AD_OrgInfo WHERE AD_Org_ID=?";
            billFromC_Location_ID = getSQLValueEx(trxName, sql, AD_Org_ID);
            found = billFromC_Location_ID != -1;
            if (billFromC_Location_ID <= 0) {
                throw new TaxCriteriaNotFoundException(variable, AD_Org_ID);
            }

            //	billC_BPartner_Location_ID  ->	billToC_Location_ID
            variable = "BillTo_ID";
            sql =
                    "SELECT l.C_Location_ID, b.IsTaxExempt, b.IsPOTaxExempt "
                            + " FROM C_BPartner_Location l"
                            + " INNER JOIN C_BPartner b ON (l.C_BPartner_ID=b.C_BPartner_ID) "
                            + " WHERE C_BPartner_Location_ID=?";
            pstmt = prepareStatement(sql);
            pstmt.setInt(1, billC_BPartner_Location_ID);
            rs = pstmt.executeQuery();
            found = false;
            if (rs.next()) {
                billToC_Location_ID = rs.getInt(1);
                IsSOTaxExempt = rs.getString(2);
                IsPOTaxExempt = rs.getString(3);
                IsTaxExempt = IsSOTrx ? IsSOTaxExempt : IsPOTaxExempt;
                found = true;
            }
            if (billToC_Location_ID <= 0) {
                throw new TaxCriteriaNotFoundException(variable, billC_BPartner_Location_ID);
            }
            if ("Y".equals(IsTaxExempt)) return getExemptTax(AD_Org_ID);

            //  Reverse for PO
            if (!IsSOTrx) {
                int temp = billFromC_Location_ID;
                billFromC_Location_ID = billToC_Location_ID;
                billToC_Location_ID = temp;
            }
            if (log.isLoggable(Level.FINE)) {
                log.fine("getProduct - billFromC_Location_ID = " + billFromC_Location_ID);
                log.fine("getProduct - billToC_Location_ID = " + billToC_Location_ID);
            }

            // -----------------------------------------------------------------

            //	M_Warehouse_ID				->	shipFromC_Location_ID
            variable = "M_Warehouse_ID";
            sql = "SELECT C_Location_ID FROM M_Warehouse WHERE M_Warehouse_ID=?";
            shipFromC_Location_ID = getSQLValueEx(trxName, sql, M_Warehouse_ID);
            found = shipFromC_Location_ID != -1;
            if (shipFromC_Location_ID <= 0) {
                throw new TaxCriteriaNotFoundException(variable, M_Warehouse_ID);
            }

            //	shipC_BPartner_Location_ID 	->	shipToC_Location_ID
            variable = "C_BPartner_Location_ID";
            sql = "SELECT C_Location_ID FROM C_BPartner_Location WHERE C_BPartner_Location_ID=?";
            shipToC_Location_ID = getSQLValueEx(trxName, sql, shipC_BPartner_Location_ID);
            found = shipToC_Location_ID != -1;
            if (shipToC_Location_ID <= 0) {
                throw new TaxCriteriaNotFoundException(variable, shipC_BPartner_Location_ID);
            }

            //  Reverse for PO
            if (!IsSOTrx) {
                int temp = shipFromC_Location_ID;
                shipFromC_Location_ID = shipToC_Location_ID;
                shipToC_Location_ID = temp;
            }
            if (log.isLoggable(Level.FINE))
                log.fine("getProduct - shipFromC_Location_ID = " + shipFromC_Location_ID);
            if (log.isLoggable(Level.FINE))
                log.fine("getProduct - shipToC_Location_ID = " + shipToC_Location_ID);
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            rs = null;
            pstmt = null;
        }

        return get(

                C_TaxCategory_ID,
                IsSOTrx,
                shipDate,
                shipFromC_Location_ID,
                shipToC_Location_ID,
                billDate,
                billFromC_Location_ID,
                billToC_Location_ID
        );
    } //	getProduct

    /**
     * Get Exempt Tax Code
     *
     * @param AD_Org_ID org to find client
     * @return C_Tax_ID
     * @throws TaxNoExemptFoundException if no tax exempt found
     */
    public static int getExemptTax(int AD_Org_ID) {
        final String sql =
                "SELECT t.C_Tax_ID "
                        + "FROM C_Tax t"
                        + " INNER JOIN AD_Org o ON (t.AD_Client_ID=o.AD_Client_ID) "
                        + "WHERE t.IsTaxExempt='Y' AND o.AD_Org_ID=? AND t.IsActive='Y' "
                        + "ORDER BY t.Rate DESC";
        int C_Tax_ID = getSQLValueEx(sql, AD_Org_ID);
        if (log.isLoggable(Level.FINE)) log.fine("getExemptTax - TaxExempt=Y - C_Tax_ID=" + C_Tax_ID);
        if (C_Tax_ID <= 0) {
            throw new TaxNoExemptFoundException(AD_Org_ID);
        } else {
            return C_Tax_ID;
        }
    } //	getExemptTax

    /**
     * ************************************************************************ Get Tax ID (Detail).
     *
     * @param C_TaxCategory_ID      tax category
     * @param IsSOTrx               Sales Order Trx
     * @param shipDate              ship date (ignored)
     * @param shipFromC_Location_ID ship from (ignored)
     * @param shipToC_Location_ID   ship to (ignored)
     * @param billDate              invoice date
     * @param billFromC_Location_ID invoice from
     * @param billToC_Location_ID   invoice to
     * @return C_Tax_ID
     * @throws TaxNotFoundException if no tax found for given criteria
     */
    public static int get(

            int C_TaxCategory_ID,
            boolean IsSOTrx,
            Timestamp shipDate,
            int shipFromC_Location_ID,
            int shipToC_Location_ID,
            Timestamp billDate,
            int billFromC_Location_ID,
            int billToC_Location_ID) {
        //	C_TaxCategory contains CommodityCode

        //	API to Tax Vendor comes here

        if (CLogMgt.isLevelFine()) {
            if (log.isLoggable(Level.INFO))
                log.info("get(Detail) - Category=" + C_TaxCategory_ID + ", SOTrx=" + IsSOTrx);
            if (log.isLoggable(Level.CONFIG))
                log.config(
                        "get(Detail) - BillFrom="
                                + billFromC_Location_ID
                                + ", BillTo="
                                + billToC_Location_ID
                                + ", BillDate="
                                + billDate);
        }

        MTax[] taxes = MTax.getAll();
        MLocation lFrom = new MLocation(billFromC_Location_ID);
        MLocation lTo = new MLocation(billToC_Location_ID);
        if (log.isLoggable(Level.FINER)) {
            log.finer("From=" + lFrom);
            log.finer("To=" + lTo);
        }

        for (MTax tax : taxes) {
            if (log.isLoggable(Level.FINEST)) log.finest(tax.toString());
            //
            if (tax.getTaxCategoryId() != C_TaxCategory_ID
                    || !tax.isActive()
                    || tax.getParent_TaxId() != 0) // 	user parent tax
                continue;
            if (IsSOTrx && MTax.SOPOTYPE_PurchaseTax.equals(tax.getSOPOType())) continue;
            if (!IsSOTrx && MTax.SOPOTYPE_SalesTax.equals(tax.getSOPOType())) continue;

            if (log.isLoggable(Level.FINEST))
                log.finest(
                        "From Country Group - "
                                + (MCountryGroup.countryGroupContains(
                                tax.getCountryGroupFromId(), lFrom.getCountryId())
                                || tax.getCountryGroupFromId() == 0));
            if (log.isLoggable(Level.FINEST))
                log.finest(
                        "From Country - "
                                + (tax.getCountryId() == lFrom.getCountryId() || tax.getCountryId() == 0));
            if (log.isLoggable(Level.FINEST))
                log.finest(
                        "From Region - "
                                + (tax.getRegionId() == lFrom.getRegionId() || tax.getRegionId() == 0));
            if (log.isLoggable(Level.FINEST))
                log.finest(
                        "To Country Group - "
                                + (MCountryGroup.countryGroupContains(
                                tax.getCountryGroupToId(), lTo.getCountryId())
                                || tax.getCountryGroupToId() == 0));
            if (log.isLoggable(Level.FINEST))
                log.finest(
                        "To Country - "
                                + (tax.getTo_CountryId() == lTo.getCountryId() || tax.getTo_CountryId() == 0));
            if (log.isLoggable(Level.FINEST))
                log.finest(
                        "To Region - "
                                + (tax.getTo_RegionId() == lTo.getRegionId() || tax.getTo_RegionId() == 0));
            if (log.isLoggable(Level.FINEST))
                log.finest("Date valid - " + (!tax.getValidFrom().after(billDate)));

            //	From Country Group
            if ((tax.getCountryGroupFromId() == 0
                    || MCountryGroup.countryGroupContains(
                    tax.getCountryGroupFromId(), lFrom.getCountryId()))
                    //	From Country
                    && (tax.getCountryId() == lFrom.getCountryId() || tax.getCountryId() == 0)
                    //	From Region
                    && (tax.getRegionId() == lFrom.getRegionId() || tax.getRegionId() == 0)
                    //	To Country Group
                    && (tax.getCountryGroupToId() == 0
                    || MCountryGroup.countryGroupContains(
                    tax.getCountryGroupToId(), lTo.getCountryId()))
                    //	To Country
                    && (tax.getTo_CountryId() == lTo.getCountryId() || tax.getTo_CountryId() == 0)
                    //	To Region
                    && (tax.getTo_RegionId() == lTo.getRegionId() || tax.getTo_RegionId() == 0)
                    //	Date
                    && !tax.getValidFrom().after(billDate)) {
                if (!tax.isPostal()) return tax.getTaxId();
                //
                MTaxPostal[] postals = tax.getPostals(false);
                for (int j = 0; j < postals.length; j++) {
                    MTaxPostal postal = postals[j];
                    if (postal.isActive()
                            //	Postal From is mandatory
                            && postal.getPostal().startsWith(lFrom.getPostal())
                            //	Postal To is optional
                            && (postal.getPostalTo() == null
                            || postal.getPostalTo().startsWith(lTo.getPostal()))) return tax.getTaxId();
                } //	for all postals
            }
        } //	for all taxes

        //	Default Tax
        for (MTax tax : taxes) {
            if (!tax.isDefault() || !tax.isActive() || tax.getParent_TaxId() != 0) // 	user parent tax
                continue;
            if (IsSOTrx && MTax.SOPOTYPE_PurchaseTax.equals(tax.getSOPOType())) continue;
            if (!IsSOTrx && MTax.SOPOTYPE_SalesTax.equals(tax.getSOPOType())) continue;
            if (log.isLoggable(Level.FINE)) log.fine("get (default) - " + tax);
            return tax.getTaxId();
        } //	for all taxes

        throw new TaxNotFoundException(
                C_TaxCategory_ID,
                IsSOTrx,
                shipDate,
                shipFromC_Location_ID,
                shipToC_Location_ID,
                billDate,
                billFromC_Location_ID,
                billToC_Location_ID);
    } //	get
} //	Tax
