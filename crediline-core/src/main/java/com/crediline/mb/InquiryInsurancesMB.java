package com.crediline.mb;

import com.crediline.dao.common.Specifications;
import com.crediline.documents.DocumentCompiler;
import com.crediline.model.*;
import com.crediline.model.specifications.CreationDateSpecification;
import com.crediline.service.DocumentTemplateService;
import com.crediline.service.InsuranceService;
import com.crediline.service.TransactionService;
import com.crediline.utils.LocaleUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.LocalDateTime;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dimer on 3/27/14.
 */

@Component("inquiryInsurances")
@Scope("prototype")
public class InquiryInsurancesMB extends CommonManagedBean implements ITabBean, ITemplatable {
    private static final long serialVersionUID = 331543323699875392L;

    @Inject
    private ApplicationScopedMB applicationScopedMB;

    @Inject
    private InsuranceService insuranceService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private DocumentTemplateService documentTemplateService;

    private LocalDateTime issuedBefore;
    private LocalDateTime issuedAfter;
    private List<Insurance> result = new ArrayList<>();
    private List<Insurance> filteredInsurances;
    private InsuranceStatus insuranceState;

    @PostConstruct
    public void init() {
    }

    public class InsuranceStateSpecification implements Specification<Insurance> {
        @Override
        public Predicate toPredicate(Root<Insurance> insuranceRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {
            if (insuranceState != null) {
                return builder.equal(insuranceRoot.get(Insurance_.status), insuranceState);
            }
            return null;
        }
    }

    public void search() {
        result = insuranceService.findAll(Specifications.and(
                new CreationDateSpecification<Insurance>(issuedAfter, issuedBefore),
                new InsuranceStateSpecification()));
    }

    public void transfer() {
        try {
            if (result.size() > 0) {

                // Calculate full transfer sum
                BigDecimal transferSum = BigDecimal.valueOf(0d);
                for (Insurance insurance : result) {
                    if (insurance.getStatus().equals(InsuranceStatus.NOT_PAID)) {
                        transferSum = transferSum.add(insurance.getSum());
                    }
                }

                // Create and save the Transaction
                if (transferSum.compareTo(BigDecimal.valueOf(0d)) > 0) {
                    Transaction transaction = new Transaction();
                    transaction.setSum(transferSum.multiply(getApplicationScopedMB()
                            .getInsuranceTransferRate()).
                            multiply(BigDecimal.valueOf(-1)));
                    transaction.setPaymentMethod(PaymentMethod.OTHER);
                    transaction.setReason(TransactionReason.INSURANCE_TRANSFER);
                    transactionService.save(transaction);
                }

                // Update the insurance
                for (Insurance insurance : result) {
                    insurance.setPaidDate(LocalDateTime.now());
                    insurance.setStatus(InsuranceStatus.PAID);
                    insuranceService.save(insurance);
                    // ToDo: must implement it with Rollback
                }
            }
        } catch (Exception e) {
            showGrowl(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("error.database"), e.getMessage());
        }
    }

    public void printOverduInsurancesDocument() {
        DocumentTemplate overdueDocumentTemplate = documentTemplateService.findOne(16L);
        DocumentCompiler documentCompiler = new DocumentCompiler(this, overdueDocumentTemplate);
        documentCompiler.compile();
        // this.setOverdueInsurancesDocument(documentCompiler.getDocument());
    }

    public StreamedContent getFile() throws IOException {
        // Generate worksheet
        Workbook wb = new HSSFWorkbook();
        FileOutputStream fileOut = new FileOutputStream("workbook.xls");
        Sheet sheet = wb.createSheet("new sheet");

        // Create table header
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue(LocaleUtils.getLocaliziedMessage("common.name"));
        header.createCell(1).setCellValue(LocaleUtils.getLocaliziedMessage("common.surname"));
        header.createCell(2).setCellValue(LocaleUtils.getLocaliziedMessage("common.egn"));
        header.createCell(3).setCellValue(LocaleUtils.getLocaliziedMessage("common.citizenship"));
        header.createCell(4).setCellValue(LocaleUtils.getLocaliziedMessage("common.birthDate"));
        header.createCell(5).setCellValue(LocaleUtils.getLocaliziedMessage("common.personPlace"));
        header.createCell(6).setCellValue(LocaleUtils.getLocaliziedMessage("common.address"));
        header.createCell(7).setCellValue(LocaleUtils.getLocaliziedMessage("common.country"));
        header.createCell(8).setCellValue(LocaleUtils.getLocaliziedMessage("common.phone"));
        header.createCell(9).setCellValue(LocaleUtils.getLocaliziedMessage("common.numberOfCertificate"));
        header.createCell(10).setCellValue(LocaleUtils.getLocaliziedMessage("common.startOfTheInsurance"));
        header.createCell(11).setCellValue(LocaleUtils.getLocaliziedMessage("common.endOfTheInsurance"));
        header.createCell(12).setCellValue(LocaleUtils.getLocaliziedMessage("common.bonus"));
        header.createCell(13).setCellValue(LocaleUtils.getLocaliziedMessage("common.commission"));
        header.createCell(14).setCellValue(LocaleUtils.getLocaliziedMessage("common.insurancePackage"));
        header.createCell(15).setCellValue(LocaleUtils.getLocaliziedMessage("common.insuranceSum"));
        header.createCell(16).setCellValue(LocaleUtils.getLocaliziedMessage("common.kind"));


        int i = 1;
        for (Insurance insurance : result) {
            Row row = sheet.createRow(i);
            try {
                row.createCell(0).setCellValue(insurance.getCredit().getPerson().getName());
                row.createCell(1).setCellValue(insurance.getCredit().getPerson().getSurname());
                row.createCell(2).setCellValue(insurance.getCredit().getPerson().getEgn());

                try {
                    row.createCell(3).setCellValue(insurance.getCredit().getPerson().getCitizenship());
                } catch (Exception e) {
                    row.createCell(3).setCellValue("NULL");
                }

                try {
                    row.createCell(4).setCellValue(insurance.getCredit().getPerson().getBirthDate().toString("dd/MM/yyyy"));
                } catch (Exception e) {
                    row.createCell(4).setCellValue("NULL");
                }

                try {
                    row.createCell(5).setCellValue(insurance.getCredit().getPerson().getLkBirthPlace().toString());
                } catch (Exception e) {
                    row.createCell(5).setCellValue("NULL");
                }

                try {
                    row.createCell(6).setCellValue(insurance.getCredit().getPerson().getMainAddress().toString());
                } catch (Exception e) {
                    row.createCell(6).setCellValue("NULL");
                }

                try {
                    row.createCell(7).setCellValue(insurance.getCredit().getPerson().getMainAddress().getCountry());
                } catch (Exception e) {
                    row.createCell(7).setCellValue("NULL");
                }

                try {
                    row.createCell(8).setCellValue(insurance.getCredit().getPerson().getMainPhone().toString());
                } catch (Exception e) {
                    row.createCell(8).setCellValue("NULL");
                }

                try {
                    row.createCell(9).setCellValue(insurance.getCredit().getId());
                } catch (Exception e) {
                    row.createCell(9).setCellValue("NULL");
                }

                try {
                    row.createCell(10).setCellValue(insurance.getCredit().getCreationDate().toString("dd/MM/yyyy"));
                } catch (Exception e) {
                    row.createCell(10).setCellValue("NULL");
                }

                try {
                    row.createCell(11).setCellValue(insurance.getCredit().getLastPayment().getDueDate().toString("dd/MM/yyyy"));
                } catch (Exception e) {
                    row.createCell(11).setCellValue("NULL");
                }

                try {
                    row.createCell(12).setCellValue(insurance.getSum().doubleValue());
                } catch (Exception e) {
                    row.createCell(12).setCellValue("NULL");
                }

                try {
                    row.createCell(13).setCellValue(insurance.getSum().multiply(getApplicationScopedMB().getInsuranceTransferRate()).doubleValue());
                } catch (Exception e) {
                    row.createCell(13).setCellValue("NULL");
                }
                //row.createCell(14).setCellValue("standard");

                try {
                    row.createCell(15).setCellValue(insurance.getCredit().getBasis().doubleValue());
                } catch (Exception e) {
                    row.createCell(15).setCellValue("NULL");
                }

                //row.createCell(16).setCellValue("standard");
            } catch (Exception e) {
                row.getCell(17).setCellValue("Error in data");
            }
            i++;
        }

        wb.write(fileOut);
        fileOut.close();

        // Return the worksheet to the browser
        FileInputStream fileIn = new FileInputStream("workbook.xls");
        return new DefaultStreamedContent(fileIn, "application/xls", "transferred_insurances.xls");
    }

    @Override
    public Boolean getClosable() {
        return true;
    }

    public LocalDateTime getIssuedBefore() {
        return issuedBefore;
    }

    public void setIssuedBefore(LocalDateTime issuedBefore) {
        this.issuedBefore = issuedBefore;
    }

    public LocalDateTime getIssuedAfter() {
        return issuedAfter;
    }

    public void setIssuedAfter(LocalDateTime issuedAfter) {
        this.issuedAfter = issuedAfter;
    }


    public List<Insurance> getResult() {
        return result;
    }

    public void setResult(List<Insurance> result) {
        this.result = result;
    }


    public List<Insurance> getFilteredInsurances() {
        return filteredInsurances;
    }

    public void setFilteredInsurances(List<Insurance> filteredInsurances) {
        this.filteredInsurances = filteredInsurances;
    }




    @Override
    public Map<String, Object> getTemplateItems() {
        Map<String, Object> itemMap = new HashMap<String, Object>();
        itemMap.put("insurances", getResult());
        itemMap.put("from", issuedAfter);
        itemMap.put("to", issuedBefore);
        return itemMap;
    }


    public InsuranceStatus getInsuranceState() {
        return insuranceState;
    }

    public void setInsuranceState(InsuranceStatus insuranceState) {
        this.insuranceState = insuranceState;
    }

    public ApplicationScopedMB getApplicationScopedMB() {
        if (applicationScopedMB == null) {
            WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
            applicationScopedMB = ctx.getBean(ApplicationScopedMB.class);
        }
        return applicationScopedMB;
    }

    public void setApplicationScopedMB(ApplicationScopedMB applicationScopedMB) {
        this.applicationScopedMB = applicationScopedMB;
    }

}
