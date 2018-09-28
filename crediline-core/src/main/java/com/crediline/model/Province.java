package com.crediline.model;

import com.crediline.utils.PrintFlag;
import com.crediline.utils.PrintUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "provinces")
public class Province extends PersistedVersional implements Identifiable, Serializable {

    private static final long serialVersionUID = 3114991811444654123L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return PrintUtils.print(this, PrintFlag.PRINT_PRETY);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
