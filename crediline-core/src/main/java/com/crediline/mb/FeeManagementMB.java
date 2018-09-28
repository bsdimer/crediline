package com.crediline.mb;

import com.crediline.utils.PreferencesUtils;
import com.crediline.utils.PropertiesUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Created by dimer on 3/27/14.
 */

@Component("feeManagement")
@Scope("prototype")
public class FeeManagementMB extends CommonManagedBean implements ITabBean {
    private static final long serialVersionUID = -5712392408825803010L;
    private static Logger LOGGER = Logger.getLogger(FeeManagementMB.class.getName());

    @Inject
    private PropertiesUtils propertyUtil;

    private int smallCreditProperty;
    private int bigCreditProperty;
    private String min;
    private String max;

    public FeeManagementMB() {
        smallCreditProperty = PreferencesUtils.getSmallCreditFeePreference();
        bigCreditProperty = PreferencesUtils.getBigCreditFeePreference();
        min = (String) propertyUtil.getProperties().getProperty("credit.sum.smallCredit.min");
        max = (String) propertyUtil.getProperties().getProperty("credit.sum.smallCredit.max");
    }

    public void save() {
        PreferencesUtils.setSmallCreditFeePreference(smallCreditProperty);
        PreferencesUtils.setBigCreditFeePreference(bigCreditProperty);

        LOGGER.info("Preferences for fees are saved:");
        LOGGER.info("Small credit preferences: " + smallCreditProperty);
        LOGGER.info("Big credit preferences: " + bigCreditProperty);
    }

    @Override
    public Boolean getClosable() {
        return true;
    }

    public int getSmallCreditProperty() {
        return smallCreditProperty;
    }

    public void setSmallCreditProperty(int smallCreditProperty) {
        this.smallCreditProperty = smallCreditProperty;
    }

    public int getBigCreditProperty() {
        return bigCreditProperty;
    }

    public void setBigCreditProperty(int bigCreditProperty) {
        this.bigCreditProperty = bigCreditProperty;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}
