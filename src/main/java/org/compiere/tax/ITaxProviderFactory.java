package org.compiere.tax;

/**
 * Tax provider factory interface
 *
 * @author Elaine
 */
public interface ITaxProviderFactory {

    /**
     * Create new tax provider instance
     *
     * @param className
     * @return tax provider instance
     */
    public ITaxProvider newTaxProviderInstance(String className);
}
