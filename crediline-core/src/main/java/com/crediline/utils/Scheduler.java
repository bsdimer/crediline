package com.crediline.utils;

import com.crediline.dao.CreditDao;
import com.crediline.model.Credit;
import com.crediline.model.CreditState;
import com.crediline.model.specifications.CreditStateSpecification;
import org.joda.time.LocalDateTime;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dimer on 5/2/14.
 */
public class Scheduler {

    @Inject
    private CreditDao creditService;

    @Inject
    private PropertiesUtils propertyUtil;

    /*@Inject*/
    private CalculatorUtil calculatorUtil;

    public static Logger logger = Logger.getLogger(Scheduler.class.getName());


    @Scheduled(cron = "0 2 * * * ?")
    public void syncInProgressCredits() {
        String isActive = propertyUtil.getProperties().getProperty("scheduler.active");
        if (isActive.equals("false")) return;
        System.out.println("Start syncing credits at" + LocalDateTime.now());
        List<Credit> inProgressCredits = creditService.findAll(new CreditStateSpecification(CreditState.IN_PROGRESS));
        for (Credit inProgressCredit : inProgressCredits) {
            System.out.println("Syncing credit with id:" + inProgressCredit.getId());
            try {
                calculatorUtil.sync(inProgressCredit);
                creditService.save(inProgressCredit);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error in sync credit with ID: " + inProgressCredit.getId());
                e.printStackTrace();
            }
        }
    }
}
