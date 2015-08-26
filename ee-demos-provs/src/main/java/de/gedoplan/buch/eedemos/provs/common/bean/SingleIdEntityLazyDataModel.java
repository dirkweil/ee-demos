package de.gedoplan.buch.eedemos.provs.common.bean;

import de.gedoplan.baselibs.persistence.entity.SingleIdEntity;
import de.gedoplan.baselibs.persistence.repository.QueryFilter;
import de.gedoplan.baselibs.persistence.repository.QueryFilter.FilterOperation;
import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public abstract class SingleIdEntityLazyDataModel<E extends SingleIdEntity<?>> extends LazyDataModel<E>
{
  private static final long serialVersionUID = 1L;

  @Override
  public List<E> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
  {
    List<QueryFilter> queryFilterList = new ArrayList<>();
    for (Entry<String, Object> filter : filters.entrySet())
    {
      final String field = filter.getKey();
      final String value = (String) filter.getValue();
      QueryFilter queryFilter = new QueryFilter(getPathExpression(field), value, getFilterOperation(field));
      queryFilterList.add(queryFilter);
    }

    long rowCount = getRepository().countAll(queryFilterList);
    assert rowCount >= 0 && rowCount <= Integer.MAX_VALUE;
    setRowCount((int) rowCount);

    if (sortOrder.equals(SortOrder.UNSORTED))
    {
      sortField = null;
    }

    return getRepository().findAll(first, pageSize, sortField, sortOrder.equals(SortOrder.DESCENDING), queryFilterList);
  }

  protected String getPathExpression(String field)
  {
    return field;
  }

  protected FilterOperation getFilterOperation(String field)
  {
    return FilterOperation.PREFIX;
  }

  protected abstract SingleIdEntityRepository<?, E> getRepository();
}
