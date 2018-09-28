package com.crediline.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "documentParameters")
public class DocumentParameter extends PersistedVersional implements Identifiable, Serializable {

    private static final long serialVersionUID = -2479605166161893948L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private DocumentTemplate documentTemplate;

    @Column(nullable = false)
    private String name;

    @Column
    private String value;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public DocumentTemplate getDocumentTemplate() {
        return documentTemplate;
    }

    public void setDocumentTemplate(DocumentTemplate documentTemplate) {
        this.documentTemplate = documentTemplate;
    }

    @Override
    public String toString() {
        return value;
    }

}
