package com.crediline.mb;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Component("statisticBean")
@Scope("prototype")
public class StatisticMB extends CommonManagedBean implements Serializable, ITabBean {
    private static final long serialVersionUID = -8859716622773047276L;

    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

    private String todayDateString;
    private BigInteger creditCount;
    private BigDecimal outcomesSum;
    private BigDecimal outcomesSumForCredits;

    private BigDecimal outcomesSumForOther;

    private BigDecimal incomesSum;

    private BigDecimal cashCaseSum;

    public StatisticMB() {
        refresh();
    }

    @Override
    public Boolean getClosable() {
        return true;
    }

    public void refresh() {
        /*LocalDateTime now = new LocalDateTime();
    	todayDateString = DATE_FORMAT.format(now.toDate());
    	creditCount = getCreditService().findApprovedCreditsCountByDay(now);
    	outcomesSum = getOutcomeService().findOutcomesSumByDate(now);
    	outcomesSumForCredits = getOutcomeService().findOutcomesSumForCreditsByDate(now);
    	outcomesSumForOther = getOutcomeService().findOutcomesSumForOtherByDate(now);
    	incomesSum = getTransactionService().findIncomesSumByDate(now);
    	cashCaseSum = incomesSum.subtract(outcomesSum);*/
    }

    public String getTodayDateString() {
        return todayDateString;
    }

    public void setTodayDateString(String todayDateString) {
        this.todayDateString = todayDateString;
    }

    public BigInteger getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(BigInteger creditCount) {
        this.creditCount = creditCount;
    }

    public BigDecimal getOutcomesSum() {
        return outcomesSum;
    }

    public void setOutcomesSum(BigDecimal outcomesSum) {
        this.outcomesSum = outcomesSum;
    }

    public BigDecimal getOutcomesSumForCredits() {
        return outcomesSumForCredits;
    }

    public void setOutcomesSumForCredits(BigDecimal outcomesSumForCredits) {
        this.outcomesSumForCredits = outcomesSumForCredits;
    }

    public BigDecimal getOutcomesSumForOther() {
        return outcomesSumForOther;
    }

    public void setOutcomesSumForOther(BigDecimal outcomesSumForOther) {
        this.outcomesSumForOther = outcomesSumForOther;
    }

    public BigDecimal getIncomesSum() {
        return incomesSum;
    }

    public void setIncomesSum(BigDecimal incomesSum) {
        this.incomesSum = incomesSum;
    }

    public BigDecimal getCashCaseSum() {
        return cashCaseSum;
    }

    public void setCashCaseSum(BigDecimal cashCaseSum) {
        this.cashCaseSum = cashCaseSum;
    }
}
