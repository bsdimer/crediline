package com.crediline.mb;

import com.crediline.exceptions.ApplicationSpecifficExeption;
import com.crediline.model.*;
import com.crediline.service.PersonService;
import com.crediline.utils.LocaleUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by dimer on 2/14/14.
 */
@Component("personBean")
@Scope("prototype")
public class PersonMB extends CommonManagedBean implements Serializable, ITabBean {
    private static final long serialVersionUID = 8439126615761864409L;

    @ManagedProperty("#{msg}")
    private ResourceBundle resourceBundle; // +setter

    @Inject
    private PersonService personService;

    private Person person;
    private Address address;
    private Phone phone;
    private Address selectedAddress;
    private Phone selectedPhone;
    private String lkIssueDate;
    private String personComment;
    private User user;

    @PostConstruct
    public void init() {
        phone = new Phone();
        person = new Person();
        address = new Address();

        address.setCountry("България");
        person.setCitizenship("българско");
        person.setRating(10);
        personComment = "";
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    public void addAddress2Person() {
        person.getAddresses().add(address);
        flushAddress();
    }

    private void flushAddress() {
        address = new Address();
    }

    private void flushPhone() {
        phone = new Phone();
    }

    /*public void removeAddressFromPerson() {
        person.getAddresses().remove(selectedAddress);
    }*/

    public void addPhone2Person() {
        person.getPhones().add(phone);
        flushPhone();
    }


    public void removeAddress(ActionEvent event) {
        Address selectedAddress = (Address) event.getComponent().getAttributes().get("parameter");
        try {
            person.getAddresses().remove(selectedAddress);
        } catch (Exception e) {
            showGrowl(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("error.database"), e.getMessage());
        }
    }

    public void removePhone(ActionEvent event) {
        Phone selectedPhone = (Phone) event.getComponent().getAttributes().get("parameter");
        try {
            person.getPhones().remove(selectedPhone);
        } catch (Exception e) {
            showGrowl(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("error.database"), e.getMessage());
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public String getLkIssueDate() {
        return lkIssueDate;
    }

    public void setLkIssueDate(String lkIssueDate) {
        this.lkIssueDate = lkIssueDate;
        DateTimeFormatter dtFormater = DateTimeFormat.forPattern("dd/MM/yyyy").withLocale(Locale.US);
        try {
            person.setLkIssueDate(dtFormater.parseDateTime(lkIssueDate).toLocalDateTime());
        } catch (IllegalArgumentException e) {
            person.setLkIssueDate(null);
        }
    }

    public void save() throws ApplicationSpecifficExeption {
        try {
            if (personComment.length() > 0) {
                person.getComments().add(new Comment(personComment));
            }

            if (person.getAddresses().size() == 0) {
                person.getAddresses().add(address);
            }

            if (person.getPhones().size() == 0 && phone.getNumber().length() > 0) {
                person.getPhones().add(phone);
            }

            personService.save(person);

            if (getTabViewMB() != null) {
                getTabViewMB().closeByBean(this);
            }
        } catch (Exception e) {
            showGrowl(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("error.database"), e.getMessage());
        }
    }

    public void saveWithoutClose() throws ApplicationSpecifficExeption {
        try {
            if (personComment.length() > 0) {
                person.getComments().add(new Comment(personComment));
            }

            if (person.getAddresses().size() == 0) {
                person.getAddresses().add(address);
            }

            if (person.getPhones().size() == 0 && phone.getNumber().length() > 0) {
                person.getPhones().add(phone);
            }
            personService.save(person);
        } catch (Exception e) {
            showGrowl(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("error.database"), e.getMessage());
        }
    }

    public String getPersonComment() {
        return personComment;
    }

    public void setPersonComment(String personComment) {
        this.personComment = personComment;
    }

    public void setSelectedPhone(Phone selectedPhone) {
        this.selectedPhone = selectedPhone;
    }

    public Phone getSelectedPhone() {
        return this.selectedPhone;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public Address getSelectedAddress() {
        return this.selectedAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getClosable() {
        return true;
    }

    public void onAddressCityChange() {

    }
}
