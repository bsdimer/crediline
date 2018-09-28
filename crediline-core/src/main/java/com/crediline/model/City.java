package com.crediline.model;

import com.crediline.dataimport.CSVEntity;
import com.crediline.utils.PrintFlag;
import com.crediline.utils.PrintUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "cities", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "oblast", "obshtina"})
})
public class City extends PersistedVersional implements Identifiable, Serializable {

    private static final long serialVersionUID = 5064001811112654811L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String oblast;

    @Column(length = 100)
    private String obshtina;


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


    public String getOblast() {
        return oblast;
    }

    public void setOblast(String oblast) {
        this.oblast = oblast;
    }

    public String getObshtina() {
        return obshtina;
    }

    public void setObshtina(String obshtina) {
        this.obshtina = obshtina;
    }


    @Override
    public String toString() {
        return PrintUtils.print(this, PrintFlag.PRINT_PRETY);
    }

}
