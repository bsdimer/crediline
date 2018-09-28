package com.crediline.mb;

import com.crediline.model.Office;
import com.crediline.service.OfficeService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dimer on 3/27/14.
 */

@Component("inquiryOffices")
@Scope("prototype")
public class InquiryOfficesMB extends CommonManagedBean implements ITabBean, ITemplatable {
    private static final long serialVersionUID = 331543323699875392L;

    @Inject
    private OfficeService officeService;

    @Inject
    private OfficeMB updateOfficeBean;

    private List<Office> result = new ArrayList<Office>();
    private List<Office> filteredOffices;
    private Office office;
    private Office selectedOffice;

    @PostConstruct
    public void init() {
        result = officeService.findAll();
        selectedOffice = new Office();
    }

    @Override
    public Boolean getClosable() {
        return true;
    }


    public List<Office> getResult() {
        return result;
    }

    public void setResult(List<Office> result) {
        this.result = result;
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
        updateOfficeBean.setOffice(selectedOffice);
    }

    public List<Office> getFilteredOffices() {
        return filteredOffices;
    }

    public void setFilteredOffices(List<Office> filteredOffices) {
        this.filteredOffices = filteredOffices;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public OfficeMB getUpdateOfficeBean() {
        return updateOfficeBean;
    }

    public void setUpdateOfficeBean(OfficeMB updateOfficeBean) {
        this.updateOfficeBean = updateOfficeBean;
    }

    public void refresh() {
        result = officeService.findAll();
    }

}
