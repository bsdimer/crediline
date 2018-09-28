package com.crediline.mb;

import com.crediline.model.Credit;
import com.crediline.model.Person;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by dimer on 1/17/14.
 */

/**
 * Must be crated in custom scope which is alive along with wizard creation and destruction
 */
@Component("detailedPersonView")
@Scope("prototype")
public class DetailedPersonViewMB extends CommonManagedBean implements Serializable, ITabBean, ITemplatable {
    private static final long serialVersionUID = 1223804567787652111L;

    private Person selectedPerson;
    private Credit selectedCredit;

    private static Logger logger = Logger.getLogger(DetailedPersonViewMB.class.getName());

    @PostConstruct
    public void init() {

    }

    @Override
    public Boolean getClosable() {
        return null;
    }

    @Override
    public Map<String, Object> getTemplateItems() {
        return null;
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public Credit getSelectedCredit() {
        return selectedCredit;
    }

    public void setSelectedCredit(Credit selectedCredit) {
        this.selectedCredit = selectedCredit;
    }
}

