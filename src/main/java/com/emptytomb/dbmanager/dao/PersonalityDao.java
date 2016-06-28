package com.emptytomb.dbmanager.dao;

import java.util.List;

import com.emptytomb.dbmanager.domain.Personality;

public interface PersonalityDao {
  public Personality getPersonality(int id) throws DaoException;
  public List<Personality> getPersonalities() throws DaoException;
  public int addPersonality(Personality personality) throws DaoException;
  public void updatePersonality(Personality personality) throws DaoException;
  public void deletePersonality(int id) throws DaoException;
}