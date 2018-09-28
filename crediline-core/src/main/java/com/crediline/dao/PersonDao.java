package com.crediline.dao;

import com.crediline.model.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("personDao")
public interface PersonDao extends CredilineRepository<Person> {
    List<Person> findByName(String name);

    List<Person> findByName(String name, Pageable pageable);

    List<Person> findByEgn(String egn);

    List<Person> findByEgn(String egn, Pageable pageable);

    List<Person> findByEgnLike(String egn, Pageable pageable);

    @Query("select p from Person p left join fetch p.credits where p.egn like :egn")
    public List<Person> findByEgnLikeEager(@Param("egn") String egn);

    @Query("select p from Person p left join fetch p.credits where p.egn like :egn")
    public List<Person> findByEgnLikeEager(@Param("egn") String egn, Pageable pageable);

    @Query("SELECT p FROM Person p join fetch p.credits WHERE p.name like :name")
    public List<Person> findByFullNameLikeEager(@Param("name") String name);

    @Query("SELECT p FROM Person p join fetch p.credits WHERE p.name like :name and p.midname like :midname")
    public List<Person> findByFullNameLikeEager(@Param("name") String name, @Param("midname") String midname);

    @Query("SELECT p FROM Person p join fetch p.credits WHERE p.name like :name and p.midname like :midname and p.surname like :surname")
    public List<Person> findByFullNameLikeEager(@Param("name") String name, @Param("midname") String midname, @Param("surname") String surname);

}