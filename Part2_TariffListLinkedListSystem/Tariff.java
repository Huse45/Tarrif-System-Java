package Part2_TariffListLinkedListSystem;

// Assignment 3
// Question: Tariff Class for Part 2
// Written by: Huseyin Pilavci, 40312242

/**
 * Represents a tariff rule applied between two countries for a specific product category.
 * Stores origin and destination countries, product category, and minimum tariff percentage.
 */
public class Tariff {
    private String destinationCountry;
    private String originCountry;
    private String productCategory;
    private double minimumTariff;

    /**
     * Constructs a Tariff object with all fields initialized.
     *
     * @param destinationCountry the country receiving the goods
     * @param originCountry      the country sending the goods
     * @param productCategory    the type/category of the product
     * @param minimumTariff      the minimum tariff percentage
     */
    public Tariff(String destinationCountry, String originCountry, String productCategory, double minimumTariff) {
        this.destinationCountry = destinationCountry;
        this.originCountry = originCountry;
        this.productCategory = productCategory;
        this.minimumTariff = minimumTariff;
    }

    /**
     * Copy constructor to create a deep copy of another Tariff object.
     *
     * @param other the Tariff object to copy
     */
    public Tariff(Tariff other) {
        this.destinationCountry = other.destinationCountry;
        this.originCountry = other.originCountry;
        this.productCategory = other.productCategory;
        this.minimumTariff = other.minimumTariff;
    }

    /**
     * Creates and returns a deep copy of this Tariff object.
     *
     * @return a new Tariff object identical to this one
     */
    public Tariff clone() {
        return new Tariff(this);
    }

    /**
     * Gets the destination country.
     *
     * @return destination country
     */
    public String getDestinationCountry() { return destinationCountry; }

    /**
     * Sets the destination country.
     *
     * @param destinationCountry the new destination country
     */
    public void setDestinationCountry(String destinationCountry) { this.destinationCountry = destinationCountry; }

    /**
     * Gets the origin country.
     *
     * @return origin country
     */
    public String getOriginCountry() { return originCountry; }

    /**
     * Sets the origin country.
     *
     * @param originCountry the new origin country
     */
    public void setOriginCountry(String originCountry) { this.originCountry = originCountry; }

    /**
     * Gets the product category.
     *
     * @return product category
     */
    public String getProductCategory() { return productCategory; }

    /**
     * Sets the product category.
     *
     * @param productCategory the new product category
     */
    public void setProductCategory(String productCategory) { this.productCategory = productCategory; }

    /**
     * Gets the minimum tariff percentage.
     *
     * @return minimum tariff
     */
    public double getMinimumTariff() { return minimumTariff; }

    /**
     * Sets the minimum tariff percentage.
     *
     * @param minimumTariff the new minimum tariff
     */
    public void setMinimumTariff(double minimumTariff) { this.minimumTariff = minimumTariff; }

    /**
     * Checks if this Tariff object is equal to another object.
     * Equality is based on all fields being exactly the same.
     *
     * @param obj the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Tariff)) return false;

        Tariff other = (Tariff) obj;
        return destinationCountry.equals(other.destinationCountry) &&
                originCountry.equals(other.originCountry) &&
                productCategory.equals(other.productCategory) &&
                minimumTariff == other.minimumTariff;
    }

    /**
     * Returns a string representation of the Tariff object.
     *
     * @return formatted string with all fields
     */
    @Override
    public String toString() {
        return destinationCountry + " " + originCountry + " " + productCategory + " " + minimumTariff;
    }
}
