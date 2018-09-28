package com.crediline.mb;

import com.crediline.files.FileDownloader;
import com.crediline.files.register.CreditRegisterFileCreator;
import com.crediline.files.register.model.BorrowerData;
import com.crediline.files.register.model.CreditContractData;
import com.crediline.files.register.model.CreditStateData;
import com.crediline.files.register.model.IRegisterData;
import com.crediline.model.Credit;
import com.crediline.model.Person;
import com.crediline.service.CreditService;
import com.crediline.service.PersonService;
import com.crediline.utils.LocaleUtils;
import org.joda.time.LocalDateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component("creditRegisterReportBean")
@Scope("prototype")
public class CreditRegisterReportMB extends CommonManagedBean implements ITabBean {
    private static final long serialVersionUID = -8125597877996545968L;
    private static Logger LOGGER = Logger.getLogger(CreditRegisterReportMB.class.getName());

    private static final String REGISTER_DATA_COUNT_INFO = "register.data.count.info";
    private static final String FISCAL_PRINTER_DIRECTORY = "crediline_cr_reports";
    private static final String APPLET_CONTAINER = "applet_container";
    private static final String REGISTER_MESSAGE_NO_RESULTS = "register.message.no.results";

    @Inject
    private PersonService personService;

    @Inject
    private CreditService creditService;

    private CreditRegisterFileCreator creditRegisterFileCreator = new CreditRegisterFileCreator();

    private RegisterFileType registerFileType;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    private boolean generated = false;
    private String registerFileName;
    private String countMessage;

    private List<? extends IRegisterData> registerData;
    private List<Credit> credits;

    public RegisterFileType[] getRegisterFileTypes() {
        return RegisterFileType.values();
    }

    @Override
    public Boolean getClosable() {
        return true;
    }

    public RegisterFileType getRegisterFileType() {
        return registerFileType;
    }

    public void setRegisterFileType(RegisterFileType registerFileType) {
        this.registerFileType = registerFileType;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public void create() {
        try {
            if (RegisterFileType.BORR.equals(registerFileType)) {
                registerFileName = BorrowerData.getFilename();
                LOGGER.info("Start generating " + registerFileName);
                List<Person> borrowers = personService.findBorrowersInRange(fromDate, toDate);
                //			List<Person> borrowers = getPersonService().findAll();
                LOGGER.info("Data loaded from db " + borrowers.size() + " records");
                if (borrowers != null && !borrowers.isEmpty()) {
                    registerData = creditRegisterFileCreator.createBorrowersRegister(borrowers, fromDate);
                    generated = true;
                    countMessage = MessageFormat.format(LocaleUtils.getLocaliziedMessage(REGISTER_DATA_COUNT_INFO), borrowers.size());
                    LOGGER.info("Generating of " + registerFileName + " completed");
                } else {
                    showNoResultsFound();
                    generated = false;
                }
            } else if (RegisterFileType.CRED.equals(registerFileType)) {
                registerFileName = CreditContractData.getFilename();
                LOGGER.info("Start generating " + registerFileName);
                credits = creditService.findApprovedCreditsInRangeDeep(fromDate, toDate);
                //			List<Credit> credits = getCreditService().findAll();
                LOGGER.info("Data loaded from db " + credits.size() + " records");
                if (credits != null && !credits.isEmpty()) {
                    registerData = creditRegisterFileCreator.createCreditContractsRegister(credits);
                    generated = true;
                    countMessage = MessageFormat.format(LocaleUtils.getLocaliziedMessage(REGISTER_DATA_COUNT_INFO), credits.size());
                    LOGGER.info("Generating of " + registerFileName + " completed");
                } else {
                    showNoResultsFound();
                    generated = false;
                }
            } else {
                registerFileName = CreditStateData.getFilename();
                LOGGER.info("Start generating " + registerFileName);
                List<Credit> credits = creditService.findInProgressNotRegisteredCreditsToDate(toDate);
                //			List<Credit> credits = getCreditService().findAll();
                LOGGER.info("Data loaded from db " + credits.size() + " records");
                if (credits != null && !credits.isEmpty()) {
                    registerData = creditRegisterFileCreator.createCreditsStateRegister(credits);
                    generated = true;
                    countMessage = MessageFormat.format(LocaleUtils.getLocaliziedMessage(REGISTER_DATA_COUNT_INFO), credits.size());
                    LOGGER.info("Generating of " + registerFileName + " completed");
                } else {
                    showNoResultsFound();
                    generated = false;
                }
            }
        } catch (Exception exception) {
            showExceptionMessage(exception.getMessage());
            LOGGER.log(Level.SEVERE, exception.getMessage(), exception);
        }
    }

    public void save() {
        FileDownloader.downloadFile(registerFileName, registerFileName, APPLET_CONTAINER, FISCAL_PRINTER_DIRECTORY);

        if (RegisterFileType.CRED.equals(registerFileType)) {
            for (Credit credit : credits) {
                credit.setIsRegistered(true);
                creditService.updateIsRegistered(credit.getId(), true);
            }
        }
    }

    private void showNoResultsFound() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, LocaleUtils.getLocaliziedMessage(REGISTER_MESSAGE_NO_RESULTS), ""));
    }

    private void showExceptionMessage(String messageKey) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, LocaleUtils.getLocaliziedMessage(messageKey), ""));
    }

    public String getDownloadFileJSFunction() {
        return registerFileName;
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
    }

    public List<? extends IRegisterData> getRegisterData() {
        return registerData;
    }

    public void setRegisterData(List<? extends IRegisterData> registerData) {
        this.registerData = registerData;
    }

    public boolean isBigData() {
        if (registerData.size() > 5) {
            return true;
        } else {
            return false;
        }
    }

    public String getCountMessage() {
        return countMessage;
    }

    public void setCountMessage(String countMessage) {
        this.countMessage = countMessage;
    }

    public enum RegisterFileType {
        BORR("register.file.borr", "BORR"), CRED("register.file.cred", "CRED"), CUCR("register.file.cucr", "CUCR");

        private String label;
        private String value;

        RegisterFileType(String label, String value) {
            this.value = value;
            this.label = label;
        }

        public String getLabel() {
            String message = LocaleUtils.getLocaliziedMessage(label);
            return message;
        }

        public String getValue() {
            return value;
        }
    }
}
