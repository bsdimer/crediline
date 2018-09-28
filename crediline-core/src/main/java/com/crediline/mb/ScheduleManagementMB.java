package com.crediline.mb;

import com.crediline.dao.common.Specifications;
import com.crediline.model.Credit;
import com.crediline.model.CreditState;
import com.crediline.model.specifications.CreationDateSpecification;
import com.crediline.model.specifications.CreditStateSpecification;
import com.crediline.service.CreditService;
import com.crediline.utils.CalculatorUtil;
import com.crediline.utils.PrintUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by dimer on 3/27/14.
 */

@Component("scheduleManagement")
@Scope("prototype")
public class ScheduleManagementMB extends CommonManagedBean implements ITabBean {
    private static final long serialVersionUID = 222543312349875009L;

    @Inject
    private CreditService creditService;

    @Inject
    private CalculatorUtil calculatorUtil;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CreditState creditState;

    @PostConstruct
    public void init() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        endDate = LocalDateTime.now().plusDays(1);
        startDate = LocalDateTime.now().minusDays(1);
    }

    @Override
    public Boolean getClosable() {
        return true;
    }

    public void resyncCredits() {
        try {
            List<Credit> inProgressCredits;
            ArrayList specifications = new ArrayList();
            if (startDate != null && endDate != null && creditState != null) {
                inProgressCredits = creditService.findAll(Specifications.and(
                        new CreationDateSpecification(startDate, endDate),
                        new CreditStateSpecification(creditState)),
                        new Sort("id"));
            } else {
                inProgressCredits = creditService.findAll();
            }

            for (Credit inProgressCredit : inProgressCredits) {
                logger.log(Level.INFO, "SYNCING credit with ID: " + inProgressCredit.getId());

                try {
                    getCalculatorUtil().sync(inProgressCredit);
                    creditService.save(inProgressCredit);
                } catch (Exception e) {
                    logger.log(Level.WARNING, "SYNC ERROR sync credit with ID: " + inProgressCredit.getId());
                    e.printStackTrace();
                    throw new Exception(e);
                }
            }
            logger.info("### SYNC COMPLETE ###");
        } catch (Exception exception) {
            PrintUtils.printExceptionInFile(exception, "cronExceptionLog.txt");
        }
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public CreditState getCreditState() {
        return creditState;
    }

    public void setCreditState(CreditState creditState) {
        this.creditState = creditState;
    }

    public CalculatorUtil getCalculatorUtil() {
        return calculatorUtil;
    }

    public void setCalculatorUtil(CalculatorUtil calculatorUtil) {
        this.calculatorUtil = calculatorUtil;
    }
}
