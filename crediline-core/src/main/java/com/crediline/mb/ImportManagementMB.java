package com.crediline.mb;

import com.crediline.dataimport.impl.*;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by dimer on 3/27/14.
 */

@Component("importManagement")
@Scope("prototype")
public class ImportManagementMB extends CommonManagedBean implements ITabBean {
    private static final long serialVersionUID = 123543312349875123L;

    @Inject
    private CityImporter cityImporter;

    @Inject
    private StreetImporter streetImporter;

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


    private UploadedFile personsFile;
    private UploadedFile creditsFile;
    private UploadedFile officesFile;
    private UploadedFile citiesFile;
    private UploadedFile streetsFile;
    private UploadedFile guarantorsFile;
    private UploadedFile registeredCreditsFile;
    private UploadedFile lkDataFile;

    @PostConstruct
    public void init() {

    }

    @Override
    public Boolean getClosable() {
        return true;
    }

    public void uploadCities() {
        try {
            cityImporter.parse(citiesFile.getInputstream()).persist();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void uploadStreets() {
        try {
            streetImporter.parse(streetsFile.getInputstream()).persist();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void uploadPersons() {
        try {
            personImporter.parse(personsFile.getInputstream()).persist();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void uploadOffices() {
        try {
            officeImporter.parse(officesFile.getInputstream()).persist();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void uploadCredits() {
        try {
            creditImporter.parse(creditsFile.getInputstream()).persist();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void uploadGuarantors() {
        try {
            guarantorImporter.parse(guarantorsFile.getInputstream()).persist();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void uploadRegisteredCredits() {
        try {
            registeredCreditsImporter.parse(registeredCreditsFile.getInputstream()).persist();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadLkData() {
        try {
            lkDataImporter.parse(lkDataFile.getInputstream()).persist();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void uploadPaymentsData() {
        try {
            lkDataImporter.parse(lkDataFile.getInputstream()).persist();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public UploadedFile getPersonsFile() {
        return personsFile;
    }

    public void setPersonsFile(UploadedFile personsFile) {
        this.personsFile = personsFile;
    }

    public UploadedFile getCreditsFile() {
        return creditsFile;
    }

    public void setCreditsFile(UploadedFile creditsFile) {
        this.creditsFile = creditsFile;
    }


    public UploadedFile getOfficesFile() {
        return officesFile;
    }

    public void setOfficesFile(UploadedFile officesFile) {
        this.officesFile = officesFile;
    }

    public UploadedFile getCitiesFile() {
        return citiesFile;
    }

    public void setCitiesFile(UploadedFile citiesFile) {
        this.citiesFile = citiesFile;
    }

    public UploadedFile getStreetsFile() {
        return streetsFile;
    }

    public void setStreetsFile(UploadedFile streetsFile) {
        this.streetsFile = streetsFile;
    }

    public UploadedFile getGuarantorsFile() {
        return guarantorsFile;
    }

    public void setGuarantorsFile(UploadedFile guarantorsFile) {
        this.guarantorsFile = guarantorsFile;
    }

    public UploadedFile getRegisteredCreditsFile() {
        return registeredCreditsFile;
    }

    public void setRegisteredCreditsFile(UploadedFile registeredCreditsFile) {
        this.registeredCreditsFile = registeredCreditsFile;
    }

    public UploadedFile getLkDataFile() {
        return lkDataFile;
    }

    public void setLkDataFile(UploadedFile lkDataFile) {
        this.lkDataFile = lkDataFile;
    }
}
