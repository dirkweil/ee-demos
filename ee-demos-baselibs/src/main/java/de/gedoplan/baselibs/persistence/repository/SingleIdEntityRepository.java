/*
 * Copyright (c) 2009
 * GEDOPLAN Unternehmensberatung und EDV-Organisation GmbH
 * Stieghorster Str. 60, D-33605 Bielefeld, Germany
 * http://www.gedoplan.de
 * All rights reserved.
 */

package de.gedoplan.baselibs.persistence.repository;

import de.gedoplan.baselibs.persistence.entity.SingleIdEntity;
import de.gedoplan.baselibs.utils.exception.BugException;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Standard-Implementierung für ein Repository-Object, welches die CRUD-Operationen (create, read, update, delete) auf einer
 * Entity anbietet und diese unter Verwendung eines Entity-Manager implementiert.
 * 
 * @param <K> Klasse des Schlüssels der Entity
 * @param <E> Entity-Klasse
 */
public abstract class SingleIdEntityRepository<K, E extends SingleIdEntity<K>> implements CrudAccessor<K, E>, Serializable
{
  /**
   * Entitymanager.
   */
  protected EntityManager entityManager;

  /**
   * Klassenobjekt der Entity-Klasse.
   */
  private Class<E>        entityClass;

  /**
   * EntityManager setzen.
   * 
   * Diese Methode wird zur Injektion des zu nutzenden EntityManagers verwendet. Sie ist hier so annotiert, dass ein @Default
   * EntityManager injiziert wird. Sollte eine andere Version verwendet werden müssen, muss die ableitende Klasse die Methode
   * überschreiben und geeignet annotieren.
   * 
   * @param entityManager Entity Manager
   */
  @Inject
  protected void setEntityManager(EntityManager entityManager)
  {
    this.entityManager = entityManager;
  }

  /**
   * Klassenobject der Entity liefern.
   * 
   * @return Klassenobjekt E.class
   */
  @SuppressWarnings("unchecked")
  protected Class<E> getEntityClass()
  {
    if (this.entityClass == null)
    {
      Class<?> repoClass = getClass();
      while (true)
      {
        Class<?> baseClass = repoClass.getSuperclass();
        assert baseClass != null : "Repository must be derived from " + SingleIdEntityRepository.class.getName();

        if (baseClass == SingleIdEntityRepository.class)
        {
          break;
        }

        repoClass = baseClass;
      }

      Type genericSuperClass = repoClass.getGenericSuperclass();
      assert genericSuperClass instanceof ParameterizedType : SingleIdEntityRepository.class.getName() + " must be generic";

      Type[] typeParms = ((ParameterizedType) genericSuperClass).getActualTypeArguments();
      assert typeParms.length == 2 : SingleIdEntityRepository.class.getName() + " must have 2 type parameters but has " + typeParms.length;

      Type entityType = typeParms[1];

      if (entityType instanceof ParameterizedType)
      {
        entityType = ((ParameterizedType) entityType).getRawType();
      }

      assert entityType instanceof Class<?> : "Entity must be a class type";

      this.entityClass = (Class<E>) entityType;
    }

    return this.entityClass;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.repository.CrudAccessor#persist(java.lang.Object)
   */
  @Override
  public void persist(E entity)
  {
    this.entityManager.persist(entity);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.repository.CrudAccessor#merge(java.lang.Object)
   */
  @Override
  public E merge(E entity)
  {
    return this.entityManager.merge(entity);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.repository.CrudAccessor#remove(java.lang.Object)
   */
  @Override
  public boolean remove(E entity)
  {
    if (entity == null)
    {
      return false;
    }

    if (!this.entityManager.contains(entity))
    {
      if (!isIdSet(entity))
      {
        return false;
      }

      entity = findById(entity.getId());
      if (entity == null)
      {
        return false;
      }
    }

    this.entityManager.remove(entity);
    return true;
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.repository.CrudAccessor#flush()
   */
  @Override
  public void flush()
  {
    this.entityManager.flush();
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.repository.CrudAccessor#isAttached(java.lang.Object)
   */
  @Override
  public boolean isAttached(E entity)
  {
    return this.entityManager.contains(entity);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.repository.CrudAccessor#isIdSet(java.lang.Object)
   */
  @Override
  public boolean isIdSet(E entity)
  {
    PersistenceUnitUtil persistenceUnitUtil = this.entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
    Object id = persistenceUnitUtil.getIdentifier(entity);
    return id != null;
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.repository.CrudAccessor#findById(java.lang.Object)
   */
  @Override
  public E findById(K id)
  {
    return this.entityManager.find(getEntityClass(), id);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.repository.CrudAccessor#findAll()
   */
  @Override
  public List<E> findAll()
  {
    return findAll(0, Integer.MAX_VALUE, null, false, null);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.repository.CrudAccessor#findAll(int, int, java.util.List)
   */
  @Override
  public List<E> findAll(int firstResult, int maxResults, String sortFieldName, boolean sortDescending, List<QueryFilter> queryFilter)
  {
    // Query für alle Einträge aufbauen
    CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
    Root<E> root = criteriaQuery.from(getEntityClass());
    criteriaQuery.select(root);

    // Where-Klausel entsprechend Filter hinzufügen
    Map<String, Object> parameterMap = filter(criteriaQuery, root, queryFilter);

    // Sortierung hinzufügen
    if (sortFieldName != null)
    {
      Path<?> sortPath = getPath(root, sortFieldName);
      Order order;
      if (sortDescending)
      {
        order = criteriaBuilder.desc(sortPath);
      }
      else
      {
        order = criteriaBuilder.asc(sortPath);
      }
      criteriaQuery.orderBy(order);
    }

    // In normale Query wandeln und Filterparameter setzen
    TypedQuery<E> query = this.entityManager.createQuery(criteriaQuery);
    setParameters(query, parameterMap);

    // Query ausführen
    return findMulti(query, firstResult, maxResults);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.repository.CrudAccessor#countAll()
   */
  @Override
  public long countAll()
  {
    return countAll(null);
  }

  /**
   * {@inheritDoc}
   * 
   * @see de.gedoplan.baselibs.persistence.repository.CrudAccessor#countAll(java.util.List)
   */
  @Override
  public long countAll(List<QueryFilter> queryFilter)
  {
    CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    Root<E> root = criteriaQuery.from(getEntityClass());
    criteriaQuery.select(criteriaBuilder.count(root));

    // Where-Klausel entsprechend Filter hinzufügen
    Map<String, Object> parameterMap = filter(criteriaQuery, root, queryFilter);

    // In normale Query wandeln und Filterparameter setzen
    TypedQuery<Long> query = this.entityManager.createQuery(criteriaQuery);
    setParameters(query, parameterMap);

    // Query ausführen
    return findSingle(query);
  }

  /**
   * CriteriaBuilder liefern.
   * 
   * @return CriteriaBuilder
   */
  public CriteriaBuilder getCriteriaBuilder()
  {
    return this.entityManager.getCriteriaBuilder();
  }

  /**
   * Select-Query ausführen mit Einzel-Ergebnis. Diese Methode soll Unterklassen zur bequemen Formulierung von <code>find</code>
   * -Methoden dienen.
   * 
   * @param <T> Typ des Query-Ergebnisses
   * @param query Select-Query
   * @return gefundener Eintrag oder <code>null</code>
   */
  protected <T> T findSingle(TypedQuery<T> query)
  {
    try
    {
      return query.getSingleResult();
    }
    catch (NoResultException e)
    {
      return null;
    }
  }

  /**
   * Select-Query ausführen mit Einzel-Ergebnis.
   * 
   * Die übergebene CriteriaQuery wird in eine {@link TypedQuery} umgesetzt und an {@link #findSingle(TypedQuery)} übergeben.
   * 
   * @param <T> Typ des Query-Ergebnisses
   * @param criteriaQuery Criteria Query
   * @return gefundener Eintrag oder <code>null</code>
   */
  protected <T> T findSingle(CriteriaQuery<T> criteriaQuery)
  {
    return findSingle(this.entityManager.createQuery(criteriaQuery));
  }

  /**
   * Select-JPQL ausführen mit Einzel-Ergebnis.
   * 
   * Der übergebene Query-Text wird in eine {@link TypedQuery} umgesetzt und an {@link #findSingle(TypedQuery)} übergeben.
   * 
   * @param jpql Select-JPQL
   * @return gefundener Eintrag oder <code>null</code>
   */
  protected E findSingle(String jpql)
  {
    return findSingle(jpql, getEntityClass());
  }

  /**
   * Select-JPQL ausführen mit Einzel-Ergebnis.
   * 
   * Der übergebene Query-Text wird in eine {@link TypedQuery} umgesetzt und an {@link #findSingle(TypedQuery)} übergeben.
   * 
   * @param <T> Typ des Query-Ergebnisses
   * @param jpql Select-JPQL
   * @param entityClass Klasse des Query-Ergebnisses
   * @return gefundener Eintrag oder <code>null</code>
   */
  protected <T> T findSingle(String jpql, Class<T> entityClass)
  {
    return findSingle(this.entityManager.createQuery(jpql, entityClass));
  }

  /**
   * Select-Query ausführen für die Suche nach einem Einzel-Ergebnis mit einem bestimmten Property-Wert. Diese Methode soll
   * Unterklassen zur bequemen Formulierung von <code>find</code> -Methoden dienen.
   * 
   * @param <T> Typ des Property-Wertes
   * @param attribute Property-Attribut
   * @param value Wert der Property
   * @return gefundener Eintrag oder <code>null</code>
   */
  protected <T> E findSingleByProperty(SingularAttribute<E, T> attribute, T value)
  {
    String parameterName = attribute.getName();
    CriteriaQuery<E> criteriaQuery = getSelectByPropertyQuery(attribute, parameterName);
    TypedQuery<E> query = this.entityManager.createQuery(criteriaQuery);
    query.setParameter(parameterName, value);
    return findSingle(query);
  }

  /**
   * Select-JPQL ausführen mit Ergebnis-Liste. Diese Methode soll Unterklassen zur bequemen Formulierung von <code>find</code>
   * -Methoden dienen.
   * 
   * @param <T> Typ des Query-Ergebnisses
   * @param query Select-Query
   * @return Liste mit gefundenen Einträgen oder leere Liste
   */
  protected <T> List<T> findMulti(TypedQuery<T> query)
  {
    return query.getResultList();
  }

  /**
   * Select-Query ausführen mit Ergebnis-Liste. Diese Methode soll Unterklassen zur bequemen Formulierung von <code>find</code>
   * -Methoden dienen.
   * 
   * @param <T> Typ des Query-Ergebnisses
   * @param query Select-Query
   * @param firstResult Index des ersten zu liefernden Ergebnisses
   * @param maxResults max. Anzahl zu liefernder Ergebnisse
   * @return Liste mit gefundenen Einträgen oder leere Liste
   */
  protected <T> List<T> findMulti(TypedQuery<T> query, int firstResult, int maxResults)
  {
    return query.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
  }

  /**
   * Select-Query ausführen mit Ergebnis-Liste.
   * 
   * Die übergebene CriteriaQuery wird in eine {@link TypedQuery} umgesetzt und an {@link #findMulti(TypedQuery)} übergeben.
   * 
   * @param <T> Typ des Query-Ergebnisses
   * @param criteriaQuery Criteria Query
   * @return Liste mit gefundenen Einträgen oder leere Liste
   */
  protected <T> List<T> findMulti(CriteriaQuery<T> criteriaQuery)
  {
    return findMulti(this.entityManager.createQuery(criteriaQuery));
  }

  /**
   * Select-Query ausführen mit Ergebnis-Liste.
   * 
   * Die übergebene CriteriaQuery wird in eine {@link TypedQuery} umgesetzt und an {@link #findMulti(TypedQuery)} übergeben.
   * 
   * @param <T> Typ des Query-Ergebnisses
   * @param criteriaQuery Criteria Query
   * @param firstResult Index des ersten zu liefernden Ergebnisses
   * @param maxResults max. Anzahl zu liefernder Ergebnisse
   * @return Liste mit gefundenen Einträgen oder leere Liste
   */
  protected <T> List<T> findMulti(CriteriaQuery<T> criteriaQuery, int firstResult, int maxResults)
  {
    return findMulti(this.entityManager.createQuery(criteriaQuery), firstResult, maxResults);
  }

  /**
   * Select-JPQL ausführen mit Ergebnis-Liste.
   * 
   * Der übergebene Query-Text wird in eine {@link TypedQuery} umgesetzt und an {@link #findMulti(TypedQuery)} übergeben.
   * 
   * @param jpql Select-JPQL
   * @return Liste mit gefundenen Einträgen oder leere Liste
   */
  protected List<E> findMulti(String jpql)
  {
    return findMulti(jpql, getEntityClass());
  }

  /**
   * Select-JPQL ausführen mit Ergebnis-Liste.
   * 
   * Der übergebene Query-Text wird in eine {@link TypedQuery} umgesetzt und an {@link #findMulti(TypedQuery)} übergeben.
   * 
   * @param <T> Typ des Query-Ergebnisses
   * @param jpql Select-JPQL
   * @param entityClass Klasse des Query-Ergebnisses
   * @return Liste mit gefundenen Einträgen oder leere Liste
   */
  protected <T> List<T> findMulti(String jpql, Class<T> entityClass)
  {
    return findMulti(this.entityManager.createQuery(jpql, entityClass));
  }

  /**
   * Select-JPQL ausführen mit Ergebnis-Liste.
   * 
   * Der übergebene Query-Text wird in eine {@link TypedQuery} umgesetzt und an {@link #findMulti(TypedQuery)} übergeben.
   * 
   * @param jpql Select-JPQL
   * @param firstResult Index des ersten zu liefernden Ergebnisses
   * @param maxResults max. Anzahl zu liefernder Ergebnisse
   * @return Liste mit gefundenen Einträgen oder leere Liste
   */
  protected List<E> findMulti(String jpql, int firstResult, int maxResults)
  {
    return findMulti(jpql, getEntityClass(), firstResult, maxResults);
  }

  /**
   * Select-JPQL ausführen mit Ergebnis-Liste.
   * 
   * Der übergebene Query-Text wird in eine {@link TypedQuery} umgesetzt und an {@link #findMulti(TypedQuery)} übergeben.
   * 
   * @param <T> Typ des Query-Ergebnisses
   * @param jpql Select-JPQL
   * @param entityClass Klasse des Query-Ergebnisses
   * @param firstResult Index des ersten zu liefernden Ergebnisses
   * @param maxResults max. Anzahl zu liefernder Ergebnisse
   * @return Liste mit gefundenen Einträgen oder leere Liste
   */
  protected <T> List<T> findMulti(String jpql, Class<T> entityClass, int firstResult, int maxResults)
  {
    return findMulti(this.entityManager.createQuery(jpql, entityClass), firstResult, maxResults);
  }

  /**
   * Select-Query ausführen für die Suche nach einer Ergebnis-Liste mit einem bestimmten Property-Wert. Diese Methode soll
   * Unterklassen zur bequemen Formulierung von <code>find</code> -Methoden dienen.
   * 
   * @param <T> Typ des Property-Wertes
   * @param attribute Property-Attribut
   * @param value Wert der Property
   * @return Liste mit gefundenen Einträgen oder leere Liste
   */
  protected <T> List<E> findMultiByProperty(SingularAttribute<E, T> attribute, T value)
  {
    String parameterName = attribute.getName();
    CriteriaQuery<E> criteriaQuery = getSelectByPropertyQuery(attribute, parameterName);
    TypedQuery<E> query = this.entityManager.createQuery(criteriaQuery);
    query.setParameter(parameterName, value);
    return findMulti(query);
  }

  /**
   * Select-Query ausführen für die Suche nach Objekten, deren String-Repräsentation {@link SingleIdEntity#toString()} mit dem
   * angegebenen Präfix beginnt. Diese Methode soll Unterklassen zur bequemen Formulierung von <code>find</code> -Methoden dienen.
   * 
   * Achtung: Diese Methode geht davon aus, dass die String-Repräsentation mit dem Inhalt des übergebenen Namensattributs beginnt!
   * 
   * @param prefix Präfix
   * @param nameAttribute Namensattribut
   * @return gefundene Objekte
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected List<E> findMultiByToStringPrefix(String prefix, SingularAttribute<?, ?>... nameAttribute)
  {
    final String paramName = "pattern";
    String pattern = prefix.replace("%", "").split("\\s*,")[0] + "%";

    CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
    Root<E> root = criteriaQuery.from(getEntityClass());
    Path namePath = null;
    for (SingularAttribute attr : nameAttribute)
    {
      namePath = namePath == null ? root.get(attr) : namePath.get(attr);
    }
    criteriaQuery.select(root).where(criteriaBuilder.like(namePath, criteriaBuilder.parameter(String.class, paramName)));
    List<E> found = findMulti(this.entityManager.createQuery(criteriaQuery).setParameter(paramName, pattern));

    Iterator<E> iter = found.iterator();
    while (iter.hasNext())
    {
      E entity = iter.next();
      if (!entity.toString().startsWith(prefix))
      {
        iter.remove();
      }
    }
    return found;
  }

  // Hilfsmethode zur Erstellung einer Query zur Selektion anhand einer Property
  private <T> CriteriaQuery<E> getSelectByPropertyQuery(SingularAttribute<E, T> attribute, String parameterName)
  {
    CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
    CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
    Root<E> root = criteriaQuery.from(getEntityClass());
    criteriaQuery.select(root);
    criteriaQuery.where(criteriaBuilder.equal(root.get(attribute), criteriaBuilder.parameter(attribute.getJavaType(), parameterName)));
    return criteriaQuery;
  }

  // Helfermethode zur Ermittlung des Path eines Attributs
  private static Path<?> getPath(Root<?> root, String pathExpression)
  {
    Path<Object> path = null;
    for (String fieldName : pathExpression.split("\\."))
    {
      if (path == null)
      {
        path = root.get(fieldName);
      }
      else
      {
        path = path.get(fieldName);
      }
    }
    return path;
  }

  // Hilfsmethode zum Anfügen einer Liste von Bedingungen an eine Query
  private Map<String, Object> filter(CriteriaQuery<?> criteriaQuery, Root<E> root, List<QueryFilter> queryFilterList)
  {
    Map<String, Object> parameterMap = new HashMap<>();

    if (queryFilterList != null)
    {
      CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();

      int condCount = queryFilterList.size();
      Predicate[] condition = new Predicate[condCount];

      int i = 0;
      for (QueryFilter qf : queryFilterList)
      {
        Path<?> path = getPath(root, qf.getPathExpression());
        String parameterName = "qf__" + i;
        Object parameterValue = qf.getValue();

        ParameterExpression<Object> parameter = criteriaBuilder.parameter(Object.class, parameterName);

        switch (qf.getFilterOperation())
        {
        case EQUAL:
          condition[i] = criteriaBuilder.equal(path, parameter);
          break;

        case CONTAINS:
          parameterValue = "%" + parameterValue;
          /* fall through */

        case PREFIX:
          @SuppressWarnings("unchecked")
          final Path<String> stringPath = (Path<String>) path;
          condition[i] = criteriaBuilder.like(stringPath, criteriaBuilder.parameter(String.class, parameterName));
          parameterValue += "%";
          break;

        default:
          throw new BugException("Illegal filter operation");
        }

        parameterMap.put(parameterName, parameterValue);
        ++i;
      }

      criteriaQuery.where(condition);
    }

    return parameterMap;
  }

  // Helfermethode zum Setzen von Parametern in einer Query
  private static void setParameters(TypedQuery<?> query, Map<String, Object> parameterMap)
  {
    for (Entry<String, Object> entry : parameterMap.entrySet())
    {
      query.setParameter(entry.getKey(), entry.getValue());
    }
  }

}
