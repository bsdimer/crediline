package com.crediline.mb;

import com.crediline.documents.CreditDocumentHelper;
import com.crediline.documents.DocumentFactory;
import com.crediline.utils.CalculatorUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dimer on 1/20/14.
 */
@Component("applicationControlMB")
public class ApplicationScopedMB implements Serializable {
    private static final long serialVersionUID = 2741242821023784208L;

    private BigDecimal minimalCreditSum = BigDecimal.valueOf(0d);
    private BigDecimal minimalIncome = BigDecimal.valueOf(0d);
    private Integer minimalCreditPeriod = 1;
    private BigDecimal defaultIntereset = BigDecimal.valueOf(0d);
    private BigDecimal insuranceTransferRate = BigDecimal.valueOf(0.8d);

    private BigDecimal maximalRate = BigDecimal.valueOf(0.0341d);

    private String topic;
    private Map<String, String> valueRegister = new HashMap<String, String>();
    private BigDecimal maxCreditSum = BigDecimal.valueOf(10000.0d);
    private BigDecimal fee4Agreement = BigDecimal.valueOf(5d);

    @Inject
    private DocumentFactory documentFactory;

    @PostConstruct
    private void init() {
        CalculatorUtil.maximalRate = maximalRate;
        CreditDocumentHelper.documentFactory = documentFactory;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setMaxCreditSum(BigDecimal maxCreditSum) {
        this.maxCreditSum = maxCreditSum;
    }

    public BigDecimal getMaxCreditSum() {
        return maxCreditSum;
    }

    public BigDecimal getMinimalCreditSum() {
        return minimalCreditSum;
    }

    public void setMinimalCreditSum(BigDecimal minimalCreditSum) {
        this.minimalCreditSum = minimalCreditSum;
    }

    public BigDecimal getMinimalIncome() {
        return minimalIncome;
    }

    public void setMinimalIncome(BigDecimal minimalIncome) {
        this.minimalIncome = minimalIncome;
    }

    public Integer getMinimalCreditPeriod() {
        return minimalCreditPeriod;
    }

    public void setMinimalCreditPeriod(Integer minimalCreditPeriod) {
        this.minimalCreditPeriod = minimalCreditPeriod;
    }

    public BigDecimal getDefaultIntereset() {
        return defaultIntereset;
    }

    public void setDefaultIntereset(BigDecimal defaultIntereset) {
        this.defaultIntereset = defaultIntereset;
    }

    public BigDecimal getInsuranceTransferRate() {
        return insuranceTransferRate;
    }

    public void setInsuranceTransferRate(BigDecimal insuranceTransferRate) {
        this.insuranceTransferRate = insuranceTransferRate;
    }

    public BigDecimal getFee4Agreement() {
        return fee4Agreement;
    }


    public BigDecimal getMaximalRate() {
        return maximalRate;
    }

    public void setMaximalRate(BigDecimal maximalRate) {
        this.maximalRate = maximalRate;
    }

    public Map<String, String> getValueRegister() {
        return valueRegister;
    }

    public void setValueRegister(Map<String, String> valueRegister) {
        this.valueRegister = valueRegister;
    }

    public void setFee4Agreement(BigDecimal fee4Agreement) {
        this.fee4Agreement = fee4Agreement;
    }

}
