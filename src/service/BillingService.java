/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Vikasitha
 */
public class BillingService {

    public double calculateTotal(
            double treatmentCost,
            double consultationFee) {

        if (treatmentCost < 0) {
            throw new IllegalArgumentException(
                    "Treatment cost cannot be negative."
            );
        }

        if (consultationFee < 0) {
            throw new IllegalArgumentException(
                    "Consultation fee cannot be negative."
            );
        }

        return treatmentCost + consultationFee;
    }
}
