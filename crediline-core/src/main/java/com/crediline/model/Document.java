package com.crediline.model;

import com.crediline.documents.DocumentCompiler;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "documents")
public class Document extends PersistedVersional implements Identifiable, Serializable {


    private static final long serialVersionUID = -8035771693826738429L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, columnDefinition = "LONGTEXT")
    private String source;

    @Column
    private Boolean printed = false;

    @Column
    private Boolean archived = false;

    @OneToOne(fetch = FetchType.LAZY)
    private Office location;

    @Column(unique = true)
    private String referenceNumber;

    @Column(unique = false)
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private DocumentTemplatePurpose documentTemplatePurpose;

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

    public String getSource() {
        return source;
    }

    public void setSource(String template) {
        this.source = template;
    }


    public Boolean getPrinted() {
        return printed;
    }

    public void setPrinted(Boolean printed) {
        this.printed = printed;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }


    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }


    public Office getLocation() {
        return location;
    }

    public void setLocation(Office location) {
        this.location = location;
    }

    public DocumentTemplatePurpose getDocumentTemplatePurpose() {
        return documentTemplatePurpose;
    }

    public void setDocumentTemplatePurpose(DocumentTemplatePurpose documentTemplatePurpose) {
        this.documentTemplatePurpose = documentTemplatePurpose;
    }

    @Override
    public String toString() {
        return source;
    }

}
