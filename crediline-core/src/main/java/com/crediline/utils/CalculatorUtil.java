package com.crediline.utils;

import com.crediline.model.*;
import org.apache.poi.ss.formula.functions.FinanceLib;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.naming.OperationNotSupportedException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimer on 1/30/14.
 */
@Component
@Scope("prototype")
public class CalculatorUtil implements Serializable {
    public static BigDecimal maximalRate;

    public List<Payment> calculatePayments(Credit credit) throws IllegalArgumentException {
        double interest = credit.getInterest().doubleValue();
        double sum = credit.getBasis().doubleValue();
        double periods = credit.getPeriod().doubleValue();
        double anuitet = FinanceLib.pmt(interest, periods, (sum * -1), 0, false);
        List<Payment> payments = new ArrayList<>();
        BigDecimal balance = BigDecimal.valueOf(sum);
        LocalDateTime dateTime = credit.getPickUpDate();
        for (int i = 1; i <= periods; i++) {
            Payment payment = new Payment();
            dateTime = dateTime.plus(Period.months(1));
            payment.setDueDate(dateTime);
            payment.setInterest(balance.multiply(BigDecimal.valueOf(interest)).setScale(2, RoundingMode.HALF_UP));
            payment.setBasis(BigDecimal.valueOf(anuitet).subtract(payment.getInterest()).setScale(2, RoundingMode.HALF_UP));
            payment.setSum(payment.getBasis().add(payment.getInterest()));
            payment.setBalance(balance);
            balance = balance.subtract(payment.getBasis()).setScale(2, RoundingMode.HALF_UP);
            payments.add(payment);
            payment.setCredit(credit);
        }
        return payments;
    }

    public double calculateAnuitetValue(Credit credit) {
        double interest = credit.getInterest().doubleValue();
        double sum = credit.getBasis().doubleValue();
        double periods = credit.getPeriod().doubleValue();
        return FinanceLib.pmt(interest, periods, (sum * -1), 0, false);
    }

    public double calculatePMT(Credit credit) {
        double interest = credit.getInterest().doubleValue();
        double sum = credit.getBasis().doubleValue();
        double periods = credit.getPeriod().doubleValue();
        return FinanceLib.pmt(interest, periods, (sum * -1), 0, false);
    }

    public double calculatePMT(double interest, double sum, double periods) {
        return FinanceLib.pmt(interest, periods, (sum * -1), 0, false);
    }

    public List<Payment> calculatePayments(double sum, int periods, double rate) throws IllegalArgumentException {
        List<Payment> payments = new ArrayList<>();
        try {
            double anuitet = FinanceLib.pmt(rate, periods, (sum * -1), 0, false);
            BigDecimal balance = BigDecimal.valueOf(sum);
            LocalDateTime dateTime = LocalDateTime.now();
            for (int i = 1; i <= periods; i++) {
                Payment payment = new Payment();
                dateTime = dateTime.plus(Period.months(1));
                payment.setDueDate(dateTime);
                payment.setInterest(balance.multiply(BigDecimal.valueOf(rate)).setScale(2, RoundingMode.HALF_UP));
                payment.setBasis(BigDecimal.valueOf(anuitet).subtract(payment.getInterest()).setScale(2, RoundingMode.HALF_UP));
                payment.setBalance(balance);
                balance = balance.subtract(payment.getBasis()).setScale(2, RoundingMode.HALF_UP);
                payments.add(payment);
            }
        } catch (Exception e) {
            return payments;
        }
        return payments;
    }

    public double calculateGpr(Credit credit) {
        return Math.pow((1d + calculateIrr(credit)), 12) - 1;
    }

    public double calculateGpr(List<Payment> payments, double outcomeSum, double tax) {
        return Math.pow((1d + calculateIrr(payments, outcomeSum - tax)), 12) - 1;
    }

    public double calculateIrr(List<Payment> payments, double outcomeSum) {
        double[] irrValues = new double[payments.size() + 1];
        irrValues[0] = (outcomeSum * -1);
        int i = 1;
        for (Payment payment : payments) {
            irrValues[i] = payment.getSum().doubleValue();
            i++;
        }
        return org.apache.poi.ss.formula.functions.Irr.irr(irrValues);
    }

    public double calculateIrr(Credit credit) {
        return calculateIrr(credit.getPayments(), credit.getBasis().doubleValue() - credit.getApplicationFee().doubleValue());
    }

    public Object calculateNpv(List<Payment> payments, Credit credit) {
        double[] irrValues = new double[payments.size() + 1];
        irrValues[0] = (credit.getBasis().doubleValue() * -1);
        int i = 1;
        for (Payment payment : payments) {
            irrValues[i] = payment.getSum().doubleValue();
            i++;
        }
        return org.apache.poi.ss.formula.functions.FinanceLib.npv(0.1d, irrValues);
    }


    public List<Payment> fixCreditOnIRR(Credit credit, double fixedIRR) {
        // Get the new rate corresponding to the fixed IRR
        double originalIRR = calculateIrr(credit);
        double deltaIrr = originalIRR - fixedIRR;
        double newRate = credit.getInterest().doubleValue() - deltaIrr;
        BigDecimal originalAnuitet = credit.getAnuitetValue();

        // Set the new credit rate
        List<Payment> payments = calculatePayments(credit);
        if (newRate > 0) {
            // Calculate the payment value delta and get the insurance value
            credit.setInterest(BigDecimal.valueOf(newRate));
            payments = calculatePayments(credit);

            BigDecimal newAnuitet = credit.getAnuitetValue();
            BigDecimal insuranceValue = originalAnuitet.subtract(newAnuitet).setScale(2, RoundingMode.HALF_UP);
            if (insuranceValue.doubleValue() > 0) {

                // Create new payments with insurance value
                for (Payment payment : payments) {
                    payment.setInsurance(insuranceValue);
                    payment.setCredit(credit);
                }

            }
        }
        credit.setPayments(payments);
        Insurance insurance = new Insurance();
        insurance.setSum(calculateAmountInsured(credit));
        credit.setInsurance(insurance);
        return payments;
    }

    public void fixStartDueDate(Credit credit, LocalDateTime startDate) {
        if (credit != null && startDate.isAfter(LocalDateTime.now())) {
            LocalDateTime dateTime = startDate;
            for (Payment payment : credit.getPayments()) {
                payment.setDueDate(dateTime);
                dateTime = dateTime.plus(Period.months(1));
            }
        }
    }

    public void fixStartDueDate(List<Payment> payments, LocalDateTime startDate) {
        if (startDate.isAfter(LocalDateTime.now())) {
            LocalDateTime dateTime = startDate.plus(Period.months(1));
            for (Payment payment : payments) {
                payment.setDueDate(dateTime);
                dateTime = dateTime.plus(Period.months(1));
            }
        }
    }

    public BigDecimal calculateAmountInsured(Credit credit) {
        try {
            BigDecimal realInsurance = credit.getBasis().multiply(BigDecimal.valueOf(0.06d));
            BigDecimal calculatedInsurance = credit.getInsurance().getSum().divide(BigDecimal.valueOf(credit.getPeriod()));
            BigDecimal rate = BigDecimal.valueOf(calculatedInsurance.doubleValue() / realInsurance.doubleValue());
            return rate.multiply(credit.getBasis());
        } catch (Exception e) {
            return BigDecimal.valueOf(0d);
        }
    }

    public void test1(Credit credit) throws OperationNotSupportedException {
        BigDecimal insuredAmount = calculateAmountInsured(credit);
    }

    public BigDecimal calculateReturnedSum(Credit credit) {
        BigDecimal incomesSum = new BigDecimal(0d);

        for (Transaction income : credit.getTransactions()) {
            if (income.getFlowType().equals(FlowType.INCOME)) {
                incomesSum = incomesSum.add(income.getSum());
            }
        }

        return incomesSum;
    }

    public BigDecimal calculateInterestSum(Credit credit) {
        BigDecimal result = new BigDecimal(0d);
        for (Payment payment : credit.getPayments()) {
            result = result.add(payment.getInterest());
        }
        return result;
    }

    /**
     * @return the last payment which is active
     */
    public Payment calculateActivePayment(Credit credit) {
        BigDecimal returnedSum = credit.getReturnedSum();
        if (returnedSum != null && returnedSum.doubleValue() > 0) {
            for (Payment payment : credit.getPayments()) {

                if (returnedSum.compareTo(payment.getSum()) <= 0) {
                    // Catch active payment
                    return payment;
                }
                returnedSum = returnedSum.subtract(payment.getSum());
            }
        } else if (credit.getPayments().size() > 0) {
            // This is newly created credit
            return credit.getPayments().get(0);
        }
        return null;
    }

    public BigDecimal calculateOverdueInterest(Credit credit) {
        BigDecimal overdueInterest = new BigDecimal(0d);
        for (Payment payment : credit.getOverduePayments()) {
            overdueInterest = overdueInterest.add(payment.getInterest());
        }
        return overdueInterest;
    }

    public BigDecimal calculateInsuranceAmount(Credit credit) {
        double originalPMT = calculatePMT(credit);
        double changedPMT = calculatePMT(maximalRate.doubleValue(), credit.getBasis().doubleValue(), credit.getPeriod());
        return BigDecimal.valueOf((originalPMT - changedPMT) * credit.getPeriod());
    }


    public LocalDateTime calculateDueDate(Credit credit) {
        Payment payment = credit.getActivePayment();
        if (payment != null) {
            return payment.getDueDate();
        }
        return null;
    }

    private BigDecimal calculteFullSum(Credit credit) {
        BigDecimal result = new BigDecimal(0d);
        for (Payment payment : credit.getPayments()) {
            result = result.add(payment.getSum());
        }
        return result;
    }

    public Credit sync(Credit credit) {

        // Exit if the credit is not active
        if (credit.getCreditState().equals(CreditState.CLOSED) ||
                credit.getCreditState().equals(CreditState.INVALID) ||
                credit.getCreditState().equals(CreditState.DECLINED)) {
            return credit;
        }

        // Set values needed for calculation
        credit.setIrr(BigDecimal.valueOf(calculateIrr(credit)));
        credit.setGpr(BigDecimal.valueOf(calculateGpr(credit)));
        credit.setAnuitetValue(BigDecimal.valueOf(calculateAnuitetValue(credit)));
        credit.setReturnedSum(calculateReturnedSum(credit));
        credit.setInterestSum(calculateInterestSum(credit));
        credit.setFullSum(calculteFullSum(credit));
        credit.setOustandingSum(credit.getFullSum().subtract(credit.getReturnedSum()));

        // Sync payments according to the returned sum
        BigDecimal returnedSum = credit.getReturnedSum();
        for (Payment payment : credit.getPayments()) {

            if (returnedSum.compareTo(BigDecimal.valueOf(0d)) <= 0) {
                // Fix the income remove behaviour
                payment.setReturnedSum(BigDecimal.valueOf(0d));
                payment.setOutstandingSum(payment.getSum().subtract(payment.getReturnedSum()));
                payment.setClosed(false);
                payment.setClosedDate(null);
                returnedSum = returnedSum.subtract(payment.getSum());
                continue;
            }

            if (returnedSum.compareTo(payment.getSum()) <= 0) {
                // Active payment match
                payment.setReturnedSum(returnedSum);
                payment.setOutstandingSum(payment.getSum().subtract(payment.getReturnedSum()));
                // credit.setActivePayment(payment);

                // Active payment is paid fully
                if (returnedSum.compareTo(payment.getSum()) == 0) {
                    payment.setClosed(true);
                    if (payment.getClosedDate() == null) {
                        payment.setClosedDate(LocalDateTime.now());
                    }
                }

                // Change Credit state according to its Active Payment
                if (payment.getDueDate().isBefore(LocalDateTime.now())) {
                    payment.setItWasInOverdue(true);
                    credit.setItWasInOverdue(true);
                    credit.setCreditState(CreditState.OVERDUE);
                } else {
                    credit.setCreditState(CreditState.IN_PROGRESS);
                }

            } else if (returnedSum.compareTo(payment.getSum()) > 0) {
                // Paid payment but not closed
                payment.setReturnedSum(payment.getSum());
                payment.setOutstandingSum(payment.getSum().subtract(payment.getReturnedSum()));
                payment.setClosed(true);
                if (payment.getClosedDate() == null) {
                    payment.setClosedDate(LocalDateTime.now());
                }
            }
            returnedSum = returnedSum.subtract(payment.getSum());
        }

        credit.setDueDate(calculateDueDate(credit));

        // Check whether the credit is almost closed and close it along with the the rounding
        if (credit.getOustandingSum().doubleValue() > -0.5 && credit.getOustandingSum().doubleValue() < 0.5) {
            LocalDateTime now = LocalDateTime.now();
            for (Payment payment : credit.getPayments()) {
                if (!payment.getClosed()) {
                    payment.setClosed(true);
                    payment.setReturnedSum(payment.getSum());
                    payment.setOutstandingSum(payment.getSum().subtract(payment.getReturnedSum()));
                    if (payment.getClosedDate() == null) {
                        payment.setClosedDate(now);
                    }
                }
            }
            credit.setCreditState(CreditState.CLOSED);
            if (credit.getClosedDate() == null) {
                credit.setClosedDate(now);
            }
        }

        return credit;
    }


}
