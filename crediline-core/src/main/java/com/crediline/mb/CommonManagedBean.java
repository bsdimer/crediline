package com.crediline.mb;

import com.crediline.dataimport.impl.*;
import com.crediline.model.Office;
import com.crediline.model.User;
import com.crediline.service.*;
import com.crediline.utils.PropertiesUtils;
import org.primefaces.context.RequestContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by dimer on 2/23/14.
 */
public abstract class CommonManagedBean implements Serializable {
    private static final long serialVersionUID = 3704415468482908984L;
    public static Logger logger = Logger.getLogger(CommonManagedBean.class.getName());

    @Inject
    private PersonService personService;

    @Inject
    private UserService userService;

    @Inject
    private OfficeService officeService;

    @Inject
    private DocumentTemplateService documentTemplateService;

    @Inject
    private CreditService creditService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private PaymentService paymentService;

    @Inject
    private TabViewMB tabViewMB;

    @Inject
    private StreetService streetService;

    @Inject
    private RegionService regionService;

    @Inject
    private CityImporter cityImporter;

    @Inject
    private StreetImporter streetImporter;

    @Inject
    private InsuranceService insuranceService;

    @Inject
    private PersonImporter personImporter;

    @Inject
    private OfficeImporter officeImporter;

    @Inject
    private CreditImporter creditImporter;

    @Inject
    private GuarantorImporter guarantorImporter;

    @Inject
    private RegisteredCreditsImporter registeredCreditsImporter;

    @Inject
    private LKImporter lkDataImporter;

    @Inject
    private PropertiesUtils propertyUtil;

    @Inject
    private PaymentsImporter paymentsImporter;

    protected CommonManagedBean() {
        logger = Logger.getLogger(this.getClass().getName());
    }

    public TabViewMB getTabViewMB() {
        return tabViewMB;
    }

    public void setTabViewMB(TabViewMB tabViewMB) {
        this.tabViewMB = tabViewMB;
    }

    public PersonService getPersonService() {
        return personService;
    }

    public UserService getUserService() {
        return userService;
    }

    public DocumentTemplateService getDocumentTemplateService() {
        return documentTemplateService;
    }

    public CreditService getCreditService() {
        return creditService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public OfficeService getOfficeService() {
        return officeService;
    }

    public StreetService getStreetService() {
        return streetService;
    }

    public RegionService getRegionService() {
        return regionService;
    }

    public CityImporter getCityImporter() {
        return cityImporter;
    }

    public StreetImporter getStreetImporter() {
        return streetImporter;
    }

    public PersonImporter getPersonImporter() {
        return personImporter;
    }

    public OfficeImporter getOfficeImporter() {
        return officeImporter;
    }

    public CreditImporter getCreditImporter() {
        return creditImporter;
    }

    public GuarantorImporter getGuarantorImporter() {
        return guarantorImporter;
    }

    public PropertiesUtils getPropertyUtil() {
        return propertyUtil;
    }

    public RegisteredCreditsImporter getRegisteredCreditsImporter() {
        return registeredCreditsImporter;
    }

    public InsuranceService getInsuranceService() {
        return insuranceService;
    }

    public LKImporter getLkDataImporter() {
        return lkDataImporter;
    }

    public PaymentsImporter getPaymentsImporter() {
        return paymentsImporter;
    }

    public void showMessageInDialog(FacesMessage.Severity severity, String header, String details) {
        FacesMessage message = new FacesMessage(severity, header, details);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public void showMessage(FacesMessage.Severity severity, String header, String details) {
        FacesContext context = FacesContext.getCurrentInstance();
        for (FacesMessage facesMessage : context.getMessageList()) {
            if (facesMessage.getSeverity() == severity
                    && facesMessage.getSummary().equals(header)
                    && facesMessage.getDetail().equals(details)) {
                return;
            }
        }
        context.addMessage(null, new FacesMessage(severity, header, details));
    }

    public void showGrowl(FacesMessage.Severity severity, String header, String details) {
        FacesContext context = FacesContext.getCurrentInstance();
        for (FacesMessage facesMessage : context.getMessageList()) {
            if (facesMessage.getSeverity() == severity
                    && facesMessage.getSummary().equals(header)
                    && facesMessage.getDetail().equals(details)) {
                return;
            }
        }
        context.addMessage(null, new FacesMessage(severity, header, details));
    }

    public Office getCurrentOffice() {
        return getCurrentUser().getOffice();
    }

    public User getCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(currentUsername);
    }

}
