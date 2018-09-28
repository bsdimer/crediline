package com.crediline.mb;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by dimer on 2/23/14.
 */

@Component("mainPanelBean")
@Scope("session")
public class MainPanelBean implements Serializable {
    private static final long serialVersionUID = 6978427431596502427L;

    private String header;

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
