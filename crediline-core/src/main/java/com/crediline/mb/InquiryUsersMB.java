package com.crediline.mb;

import com.crediline.dao.common.Specifications;
import com.crediline.model.*;
import com.crediline.service.CreditService;
import com.crediline.service.DocumentTemplateService;
import com.crediline.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dimer on 3/27/14.
 */

@Component("inquiryUsers")
@Scope("prototype")
public class InquiryUsersMB extends CommonManagedBean implements ITabBean, ITemplatable, Specification<User> {
    private static final long serialVersionUID = 331543323699875392L;

    @Inject
    private UserService userService;

    @Inject
    private CreditService creditService;

    @Inject
    private DocumentTemplateService documentTemplateService;

    @Inject
    private UserMB updateUserBean;

    private Person selectedPerson;
    private User selectedUser;
    private Credit selectedCredit;
    private Region selectedRegion;
    private Office selectedOffice;
    private Role selectedRole;
    private List<User> result = new ArrayList<User>();
    private List<User> filteredUsers;
    private Boolean userEnabled;
    private PersonAdvancedInfo personAdvancedInfo;

    @PostConstruct
    private void init() {
        selectedCredit = new Credit();
        selectedUser = new User();
        personAdvancedInfo = new PersonAdvancedInfo(creditService, documentTemplateService);
    }

    @Override
    public Predicate toPredicate(Root<User> userRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Fetch<User, Office> officeFetch = userRoot.fetch(User_.office);

        List<Predicate> predicates = new ArrayList<>();
        if (selectedPerson != null) {
            predicates.add(builder.equal(userRoot.get(User_.person), selectedPerson));
        }

        if (selectedOffice != null) {
            predicates.add(builder.equal(userRoot.get(User_.office), selectedOffice));
        }

        if (selectedRole != null) {
            predicates.add(builder.equal(userRoot.get(User_.role), selectedRole));
        }
        return Specifications.and(predicates, builder);
    }

    public void search() {
        result = userService.findAll(this, new Sort("id"));
    }

    @Override
    public Boolean getClosable() {
        return true;
    }


    public List<User> getResult() {
        return result;
    }

    public void setResult(List<User> result) {
        this.result = result;
    }


    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }




    @Override
    public Map<String, Object> getTemplateItems() {
        Map<String, Object> itemMap = new HashMap<String, Object>();

        return itemMap;
    }


    public Office getSelectedOffice() {
        return selectedOffice;
    }

    public void setSelectedOffice(Office selectedOffice) {
        this.selectedOffice = selectedOffice;
    }

    public Region getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(Region selectedRegion) {
        this.selectedRegion = selectedRegion;
    }

    public Role getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }

    public Boolean getUserEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(Boolean userEnabled) {
        this.userEnabled = userEnabled;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        updateUserBean.setUser(selectedUser);
        this.selectedUser = selectedUser;
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

    public UserMB getUpdateUserBean() {
        return updateUserBean;
    }

    public void setUpdateUserBean(UserMB updateUserBean) {
        this.updateUserBean = updateUserBean;
    }

    public PersonAdvancedInfo getPersonAdvancedInfo() {
        return personAdvancedInfo;
    }

    public void setPersonAdvancedInfo(PersonAdvancedInfo personAdvancedInfo) {
        this.personAdvancedInfo = personAdvancedInfo;
    }
}
