package com.crediline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "documentTemplates", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "documentTemplatePurpose"}))
public class DocumentTemplate extends PersistedVersional implements Identifiable, Serializable {


    private static final long serialVersionUID = 5391438826377492737L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private DocumentTemplatePurpose documentTemplatePurpose;

    @Column(nullable = true, columnDefinition = "LONGTEXT")
    private String source = "";

    @OneToMany(mappedBy = "documentTemplate")
    private List<DocumentParameter> parameters = new ArrayList<DocumentParameter>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String template) {
        this.source = template;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<DocumentParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<DocumentParameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return getName();
    }


    public DocumentTemplatePurpose getDocumentTemplatePurpose() {
        return documentTemplatePurpose;
    }

    public void setDocumentTemplatePurpose(DocumentTemplatePurpose documentTemplatePurpose) {
        this.documentTemplatePurpose = documentTemplatePurpose;
    }
}
