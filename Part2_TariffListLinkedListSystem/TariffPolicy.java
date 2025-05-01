package Part2_TariffListLinkedListSystem;

// Assignment 3
// Question: TariffPolicy Interface for part 2
// Written by: Huseyin Pilavci, 40312242

public interface TariffPolicy {
    /**
     * Evaluates a trade request based on the proposed tariff and the minimum required tariff.
     * @param proposedTariff The tariff rate offered in the trade request.
     * @param minimumTariff The minimum acceptable tariff required by destination country.
     * @return A decision string: "Accepted", "Conditionally Accepted", or "Rejected".
     */
    String evaluateTrade(double proposedTariff, double minimumTariff);
}
