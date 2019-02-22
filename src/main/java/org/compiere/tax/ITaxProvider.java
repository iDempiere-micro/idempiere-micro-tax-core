package org.compiere.tax;

import org.compiere.model.*;

/**
 * Tax provider interface
 *
 * @author Elaine
 * @contributor Murilo H. Torquato <muriloht@devcoffee.com.br>
 */
public interface ITaxProvider {

    boolean calculateOrderTaxTotal(I_C_TaxProvider provider, I_C_Order order);

    boolean updateOrderTax(I_C_TaxProvider provider, I_C_OrderLine line);

    boolean recalculateTax(I_C_TaxProvider provider, I_C_OrderLine line, boolean newRecord);

    boolean updateHeaderTax(I_C_TaxProvider provider, I_C_OrderLine line);

    boolean calculateRMATaxTotal(I_C_TaxProvider provider, I_M_RMA rma);

    boolean updateRMATax(I_C_TaxProvider provider, I_M_RMALine line);

    boolean recalculateTax(I_C_TaxProvider provider, I_M_RMALine line, boolean newRecord);

    boolean updateHeaderTax(I_C_TaxProvider provider, I_M_RMALine line);

}
