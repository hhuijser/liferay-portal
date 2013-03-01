/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.NoSuchValueException;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.model.impl.ExpandoValueImpl;
import com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the expando value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoValuePersistence
 * @see ExpandoValueUtil
 * @generated
 */
public class ExpandoValuePersistenceImpl extends BasePersistenceImpl<ExpandoValue>
	implements ExpandoValuePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ExpandoValueUtil} to access the expando value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ExpandoValueImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TABLEID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTableId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TABLEID =
		new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByTableId",
			new String[] { Long.class.getName() },
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TABLEID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByTableId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the expando values where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @return the matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByTableId(long tableId)
		throws SystemException {
		return findByTableId(tableId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByTableId(long tableId, int start, int end)
		throws SystemException {
		return findByTableId(tableId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByTableId(long tableId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TABLEID;
			finderArgs = new Object[] { tableId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TABLEID;
			finderArgs = new Object[] { tableId, start, end, orderByComparator };
		}

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ExpandoValue expandoValue : list) {
				if ((tableId != expandoValue.getTableId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				if (!pagination) {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<ExpandoValue>(list);
				}
				else {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByTableId_First(long tableId,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByTableId_First(tableId,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tableId=");
		msg.append(tableId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByTableId_First(long tableId,
		OrderByComparator orderByComparator) throws SystemException {
		List<ExpandoValue> list = findByTableId(tableId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByTableId_Last(long tableId,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByTableId_Last(tableId,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tableId=");
		msg.append(tableId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByTableId_Last(long tableId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByTableId(tableId);

		List<ExpandoValue> list = findByTableId(tableId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where tableId = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param tableId the table ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue[] findByTableId_PrevAndNext(long valueId, long tableId,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByTableId_PrevAndNext(session, expandoValue, tableId,
					orderByComparator, true);

			array[1] = expandoValue;

			array[2] = getByTableId_PrevAndNext(session, expandoValue, tableId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByTableId_PrevAndNext(Session session,
		ExpandoValue expandoValue, long tableId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(tableId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(expandoValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExpandoValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where tableId = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByTableId(long tableId) throws SystemException {
		for (ExpandoValue expandoValue : findByTableId(tableId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where tableId = &#63;.
	 *
	 * @param tableId the table ID
	 * @return the number of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public int countByTableId(long tableId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TABLEID;

		Object[] finderArgs = new Object[] { tableId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_TABLEID_TABLEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_TABLEID_TABLEID_2 = "expandoValue.tableId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COLUMNID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByColumnId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COLUMNID =
		new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByColumnId",
			new String[] { Long.class.getName() },
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK |
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COLUMNID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByColumnId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the expando values where columnId = &#63;.
	 *
	 * @param columnId the column ID
	 * @return the matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByColumnId(long columnId)
		throws SystemException {
		return findByColumnId(columnId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the expando values where columnId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param columnId the column ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByColumnId(long columnId, int start, int end)
		throws SystemException {
		return findByColumnId(columnId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where columnId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param columnId the column ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByColumnId(long columnId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COLUMNID;
			finderArgs = new Object[] { columnId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COLUMNID;
			finderArgs = new Object[] { columnId, start, end, orderByComparator };
		}

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ExpandoValue expandoValue : list) {
				if ((columnId != expandoValue.getColumnId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_COLUMNID_COLUMNID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				if (!pagination) {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<ExpandoValue>(list);
				}
				else {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first expando value in the ordered set where columnId = &#63;.
	 *
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByColumnId_First(long columnId,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByColumnId_First(columnId,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("columnId=");
		msg.append(columnId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where columnId = &#63;.
	 *
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByColumnId_First(long columnId,
		OrderByComparator orderByComparator) throws SystemException {
		List<ExpandoValue> list = findByColumnId(columnId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where columnId = &#63;.
	 *
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByColumnId_Last(long columnId,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByColumnId_Last(columnId,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("columnId=");
		msg.append(columnId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where columnId = &#63;.
	 *
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByColumnId_Last(long columnId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByColumnId(columnId);

		List<ExpandoValue> list = findByColumnId(columnId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where columnId = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue[] findByColumnId_PrevAndNext(long valueId,
		long columnId, OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByColumnId_PrevAndNext(session, expandoValue,
					columnId, orderByComparator, true);

			array[1] = expandoValue;

			array[2] = getByColumnId_PrevAndNext(session, expandoValue,
					columnId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByColumnId_PrevAndNext(Session session,
		ExpandoValue expandoValue, long columnId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		query.append(_FINDER_COLUMN_COLUMNID_COLUMNID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(columnId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(expandoValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExpandoValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where columnId = &#63; from the database.
	 *
	 * @param columnId the column ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByColumnId(long columnId) throws SystemException {
		for (ExpandoValue expandoValue : findByColumnId(columnId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where columnId = &#63;.
	 *
	 * @param columnId the column ID
	 * @return the number of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public int countByColumnId(long columnId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COLUMNID;

		Object[] finderArgs = new Object[] { columnId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_COLUMNID_COLUMNID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COLUMNID_COLUMNID_2 = "expandoValue.columnId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ROWID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRowId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROWID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRowId",
			new String[] { Long.class.getName() },
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK |
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ROWID = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRowId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the expando values where rowId = &#63;.
	 *
	 * @param rowId the row ID
	 * @return the matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByRowId(long rowId) throws SystemException {
		return findByRowId(rowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values where rowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param rowId the row ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByRowId(long rowId, int start, int end)
		throws SystemException {
		return findByRowId(rowId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where rowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param rowId the row ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByRowId(long rowId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROWID;
			finderArgs = new Object[] { rowId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ROWID;
			finderArgs = new Object[] { rowId, start, end, orderByComparator };
		}

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ExpandoValue expandoValue : list) {
				if ((rowId != expandoValue.getRowId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_ROWID_ROWID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rowId);

				if (!pagination) {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<ExpandoValue>(list);
				}
				else {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first expando value in the ordered set where rowId = &#63;.
	 *
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByRowId_First(long rowId,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByRowId_First(rowId, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("rowId=");
		msg.append(rowId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where rowId = &#63;.
	 *
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByRowId_First(long rowId,
		OrderByComparator orderByComparator) throws SystemException {
		List<ExpandoValue> list = findByRowId(rowId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where rowId = &#63;.
	 *
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByRowId_Last(long rowId,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByRowId_Last(rowId, orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("rowId=");
		msg.append(rowId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where rowId = &#63;.
	 *
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByRowId_Last(long rowId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByRowId(rowId);

		List<ExpandoValue> list = findByRowId(rowId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where rowId = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue[] findByRowId_PrevAndNext(long valueId, long rowId,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByRowId_PrevAndNext(session, expandoValue, rowId,
					orderByComparator, true);

			array[1] = expandoValue;

			array[2] = getByRowId_PrevAndNext(session, expandoValue, rowId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByRowId_PrevAndNext(Session session,
		ExpandoValue expandoValue, long rowId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		query.append(_FINDER_COLUMN_ROWID_ROWID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(rowId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(expandoValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExpandoValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where rowId = &#63; from the database.
	 *
	 * @param rowId the row ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByRowId(long rowId) throws SystemException {
		for (ExpandoValue expandoValue : findByRowId(rowId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where rowId = &#63;.
	 *
	 * @param rowId the row ID
	 * @return the number of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public int countByRowId(long rowId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ROWID;

		Object[] finderArgs = new Object[] { rowId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_ROWID_ROWID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(rowId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ROWID_ROWID_2 = "expandoValue.rowId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_T_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the expando values where tableId = &#63; and columnId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @return the matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByT_C(long tableId, long columnId)
		throws SystemException {
		return findByT_C(tableId, columnId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values where tableId = &#63; and columnId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByT_C(long tableId, long columnId, int start,
		int end) throws SystemException {
		return findByT_C(tableId, columnId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByT_C(long tableId, long columnId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_C;
			finderArgs = new Object[] { tableId, columnId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_T_C;
			finderArgs = new Object[] {
					tableId, columnId,
					
					start, end, orderByComparator
				};
		}

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ExpandoValue expandoValue : list) {
				if ((tableId != expandoValue.getTableId()) ||
						(columnId != expandoValue.getColumnId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_C_TABLEID_2);

			query.append(_FINDER_COLUMN_T_C_COLUMNID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				if (!pagination) {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<ExpandoValue>(list);
				}
				else {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByT_C_First(long tableId, long columnId,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByT_C_First(tableId, columnId,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tableId=");
		msg.append(tableId);

		msg.append(", columnId=");
		msg.append(columnId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByT_C_First(long tableId, long columnId,
		OrderByComparator orderByComparator) throws SystemException {
		List<ExpandoValue> list = findByT_C(tableId, columnId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByT_C_Last(long tableId, long columnId,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByT_C_Last(tableId, columnId,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tableId=");
		msg.append(tableId);

		msg.append(", columnId=");
		msg.append(columnId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByT_C_Last(long tableId, long columnId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByT_C(tableId, columnId);

		List<ExpandoValue> list = findByT_C(tableId, columnId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and columnId = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue[] findByT_C_PrevAndNext(long valueId, long tableId,
		long columnId, OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByT_C_PrevAndNext(session, expandoValue, tableId,
					columnId, orderByComparator, true);

			array[1] = expandoValue;

			array[2] = getByT_C_PrevAndNext(session, expandoValue, tableId,
					columnId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByT_C_PrevAndNext(Session session,
		ExpandoValue expandoValue, long tableId, long columnId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		query.append(_FINDER_COLUMN_T_C_TABLEID_2);

		query.append(_FINDER_COLUMN_T_C_COLUMNID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(tableId);

		qPos.add(columnId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(expandoValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExpandoValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where tableId = &#63; and columnId = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByT_C(long tableId, long columnId)
		throws SystemException {
		for (ExpandoValue expandoValue : findByT_C(tableId, columnId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where tableId = &#63; and columnId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @return the number of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_C(long tableId, long columnId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_T_C;

		Object[] finderArgs = new Object[] { tableId, columnId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_C_TABLEID_2);

			query.append(_FINDER_COLUMN_T_C_COLUMNID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_T_C_TABLEID_2 = "expandoValue.tableId = ? AND ";
	private static final String _FINDER_COLUMN_T_C_COLUMNID_2 = "expandoValue.columnId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_T_CPK = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_CPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_CPK = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_CPK",
			new String[] { Long.class.getName(), Long.class.getName() },
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.CLASSPK_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_CPK = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_CPK",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the expando values where tableId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param classPK the class p k
	 * @return the matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByT_CPK(long tableId, long classPK)
		throws SystemException {
		return findByT_CPK(tableId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values where tableId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByT_CPK(long tableId, long classPK,
		int start, int end) throws SystemException {
		return findByT_CPK(tableId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByT_CPK(long tableId, long classPK,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_CPK;
			finderArgs = new Object[] { tableId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_T_CPK;
			finderArgs = new Object[] {
					tableId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ExpandoValue expandoValue : list) {
				if ((tableId != expandoValue.getTableId()) ||
						(classPK != expandoValue.getClassPK())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_CPK_TABLEID_2);

			query.append(_FINDER_COLUMN_T_CPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<ExpandoValue>(list);
				}
				else {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByT_CPK_First(long tableId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByT_CPK_First(tableId, classPK,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tableId=");
		msg.append(tableId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByT_CPK_First(long tableId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		List<ExpandoValue> list = findByT_CPK(tableId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByT_CPK_Last(long tableId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByT_CPK_Last(tableId, classPK,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tableId=");
		msg.append(tableId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByT_CPK_Last(long tableId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByT_CPK(tableId, classPK);

		List<ExpandoValue> list = findByT_CPK(tableId, classPK, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and classPK = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param tableId the table ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue[] findByT_CPK_PrevAndNext(long valueId, long tableId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByT_CPK_PrevAndNext(session, expandoValue, tableId,
					classPK, orderByComparator, true);

			array[1] = expandoValue;

			array[2] = getByT_CPK_PrevAndNext(session, expandoValue, tableId,
					classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByT_CPK_PrevAndNext(Session session,
		ExpandoValue expandoValue, long tableId, long classPK,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		query.append(_FINDER_COLUMN_T_CPK_TABLEID_2);

		query.append(_FINDER_COLUMN_T_CPK_CLASSPK_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(tableId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(expandoValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExpandoValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where tableId = &#63; and classPK = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByT_CPK(long tableId, long classPK)
		throws SystemException {
		for (ExpandoValue expandoValue : findByT_CPK(tableId, classPK,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where tableId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param classPK the class p k
	 * @return the number of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_CPK(long tableId, long classPK)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_T_CPK;

		Object[] finderArgs = new Object[] { tableId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_CPK_TABLEID_2);

			query.append(_FINDER_COLUMN_T_CPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_T_CPK_TABLEID_2 = "expandoValue.tableId = ? AND ";
	private static final String _FINDER_COLUMN_T_CPK_CLASSPK_2 = "expandoValue.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_T_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_R",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_R",
			new String[] { Long.class.getName(), Long.class.getName() },
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_R",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the expando values where tableId = &#63; and rowId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @return the matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByT_R(long tableId, long rowId)
		throws SystemException {
		return findByT_R(tableId, rowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the expando values where tableId = &#63; and rowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByT_R(long tableId, long rowId, int start,
		int end) throws SystemException {
		return findByT_R(tableId, rowId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63; and rowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByT_R(long tableId, long rowId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_R;
			finderArgs = new Object[] { tableId, rowId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_T_R;
			finderArgs = new Object[] {
					tableId, rowId,
					
					start, end, orderByComparator
				};
		}

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ExpandoValue expandoValue : list) {
				if ((tableId != expandoValue.getTableId()) ||
						(rowId != expandoValue.getRowId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_R_TABLEID_2);

			query.append(_FINDER_COLUMN_T_R_ROWID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(rowId);

				if (!pagination) {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<ExpandoValue>(list);
				}
				else {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByT_R_First(long tableId, long rowId,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByT_R_First(tableId, rowId,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tableId=");
		msg.append(tableId);

		msg.append(", rowId=");
		msg.append(rowId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByT_R_First(long tableId, long rowId,
		OrderByComparator orderByComparator) throws SystemException {
		List<ExpandoValue> list = findByT_R(tableId, rowId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByT_R_Last(long tableId, long rowId,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByT_R_Last(tableId, rowId,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tableId=");
		msg.append(tableId);

		msg.append(", rowId=");
		msg.append(rowId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByT_R_Last(long tableId, long rowId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByT_R(tableId, rowId);

		List<ExpandoValue> list = findByT_R(tableId, rowId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and rowId = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue[] findByT_R_PrevAndNext(long valueId, long tableId,
		long rowId, OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByT_R_PrevAndNext(session, expandoValue, tableId,
					rowId, orderByComparator, true);

			array[1] = expandoValue;

			array[2] = getByT_R_PrevAndNext(session, expandoValue, tableId,
					rowId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByT_R_PrevAndNext(Session session,
		ExpandoValue expandoValue, long tableId, long rowId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		query.append(_FINDER_COLUMN_T_R_TABLEID_2);

		query.append(_FINDER_COLUMN_T_R_ROWID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(tableId);

		qPos.add(rowId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(expandoValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExpandoValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where tableId = &#63; and rowId = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByT_R(long tableId, long rowId) throws SystemException {
		for (ExpandoValue expandoValue : findByT_R(tableId, rowId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where tableId = &#63; and rowId = &#63;.
	 *
	 * @param tableId the table ID
	 * @param rowId the row ID
	 * @return the number of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_R(long tableId, long rowId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_T_R;

		Object[] finderArgs = new Object[] { tableId, rowId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_R_TABLEID_2);

			query.append(_FINDER_COLUMN_T_R_ROWID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(rowId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_T_R_TABLEID_2 = "expandoValue.tableId = ? AND ";
	private static final String _FINDER_COLUMN_T_R_ROWID_2 = "expandoValue.rowId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_R",
			new String[] { Long.class.getName(), Long.class.getName() },
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_R = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_R",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the expando value where columnId = &#63; and rowId = &#63; or throws a {@link com.liferay.portlet.expando.NoSuchValueException} if it could not be found.
	 *
	 * @param columnId the column ID
	 * @param rowId the row ID
	 * @return the matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByC_R(long columnId, long rowId)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByC_R(columnId, rowId);

		if (expandoValue == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("columnId=");
			msg.append(columnId);

			msg.append(", rowId=");
			msg.append(rowId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchValueException(msg.toString());
		}

		return expandoValue;
	}

	/**
	 * Returns the expando value where columnId = &#63; and rowId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param columnId the column ID
	 * @param rowId the row ID
	 * @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByC_R(long columnId, long rowId)
		throws SystemException {
		return fetchByC_R(columnId, rowId, true);
	}

	/**
	 * Returns the expando value where columnId = &#63; and rowId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param columnId the column ID
	 * @param rowId the row ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByC_R(long columnId, long rowId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { columnId, rowId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_R,
					finderArgs, this);
		}

		if (result instanceof ExpandoValue) {
			ExpandoValue expandoValue = (ExpandoValue)result;

			if ((columnId != expandoValue.getColumnId()) ||
					(rowId != expandoValue.getRowId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_C_R_COLUMNID_2);

			query.append(_FINDER_COLUMN_C_R_ROWID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				qPos.add(rowId);

				List<ExpandoValue> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R,
						finderArgs, list);
				}
				else {
					ExpandoValue expandoValue = list.get(0);

					result = expandoValue;

					cacheResult(expandoValue);

					if ((expandoValue.getColumnId() != columnId) ||
							(expandoValue.getRowId() != rowId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R,
							finderArgs, expandoValue);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_R,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ExpandoValue)result;
		}
	}

	/**
	 * Removes the expando value where columnId = &#63; and rowId = &#63; from the database.
	 *
	 * @param columnId the column ID
	 * @param rowId the row ID
	 * @return the expando value that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue removeByC_R(long columnId, long rowId)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByC_R(columnId, rowId);

		return remove(expandoValue);
	}

	/**
	 * Returns the number of expando values where columnId = &#63; and rowId = &#63;.
	 *
	 * @param columnId the column ID
	 * @param rowId the row ID
	 * @return the number of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_R(long columnId, long rowId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_R;

		Object[] finderArgs = new Object[] { columnId, rowId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_C_R_COLUMNID_2);

			query.append(_FINDER_COLUMN_C_R_ROWID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(columnId);

				qPos.add(rowId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_R_COLUMNID_2 = "expandoValue.columnId = ? AND ";
	private static final String _FINDER_COLUMN_C_R_ROWID_2 = "expandoValue.rowId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			ExpandoValueModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.CLASSPK_COLUMN_BITMASK |
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the expando values where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByC_C(long classNameId, long classPK)
		throws SystemException {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByC_C(long classNameId, long classPK,
		int start, int end) throws SystemException {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByC_C(long classNameId, long classPK,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ExpandoValue expandoValue : list) {
				if ((classNameId != expandoValue.getClassNameId()) ||
						(classPK != expandoValue.getClassPK())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<ExpandoValue>(list);
				}
				else {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByC_C_First(classNameId, classPK,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByC_C_First(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		List<ExpandoValue> list = findByC_C(classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByC_C_Last(classNameId, classPK,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_C(classNameId, classPK);

		List<ExpandoValue> list = findByC_C(classNameId, classPK, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue[] findByC_C_PrevAndNext(long valueId, long classNameId,
		long classPK, OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByC_C_PrevAndNext(session, expandoValue, classNameId,
					classPK, orderByComparator, true);

			array[1] = expandoValue;

			array[2] = getByC_C_PrevAndNext(session, expandoValue, classNameId,
					classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByC_C_PrevAndNext(Session session,
		ExpandoValue expandoValue, long classNameId, long classPK,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(expandoValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExpandoValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_C(long classNameId, long classPK)
		throws SystemException {
		for (ExpandoValue expandoValue : findByC_C(classNameId, classPK,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "expandoValue.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "expandoValue.classPK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_T_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByT_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK |
			ExpandoValueModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_C_C = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.expando.NoSuchValueException} if it could not be found.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param classPK the class p k
	 * @return the matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByT_C_C(long tableId, long columnId, long classPK)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByT_C_C(tableId, columnId, classPK);

		if (expandoValue == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("tableId=");
			msg.append(tableId);

			msg.append(", columnId=");
			msg.append(columnId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchValueException(msg.toString());
		}

		return expandoValue;
	}

	/**
	 * Returns the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param classPK the class p k
	 * @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByT_C_C(long tableId, long columnId, long classPK)
		throws SystemException {
		return fetchByT_C_C(tableId, columnId, classPK, true);
	}

	/**
	 * Returns the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByT_C_C(long tableId, long columnId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { tableId, columnId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_T_C_C,
					finderArgs, this);
		}

		if (result instanceof ExpandoValue) {
			ExpandoValue expandoValue = (ExpandoValue)result;

			if ((tableId != expandoValue.getTableId()) ||
					(columnId != expandoValue.getColumnId()) ||
					(classPK != expandoValue.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_C_C_TABLEID_2);

			query.append(_FINDER_COLUMN_T_C_C_COLUMNID_2);

			query.append(_FINDER_COLUMN_T_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				qPos.add(classPK);

				List<ExpandoValue> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C,
						finderArgs, list);
				}
				else {
					ExpandoValue expandoValue = list.get(0);

					result = expandoValue;

					cacheResult(expandoValue);

					if ((expandoValue.getTableId() != tableId) ||
							(expandoValue.getColumnId() != columnId) ||
							(expandoValue.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C,
							finderArgs, expandoValue);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C_C,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ExpandoValue)result;
		}
	}

	/**
	 * Removes the expando value where tableId = &#63; and columnId = &#63; and classPK = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param classPK the class p k
	 * @return the expando value that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue removeByT_C_C(long tableId, long columnId, long classPK)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByT_C_C(tableId, columnId, classPK);

		return remove(expandoValue);
	}

	/**
	 * Returns the number of expando values where tableId = &#63; and columnId = &#63; and classPK = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param classPK the class p k
	 * @return the number of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_C_C(long tableId, long columnId, long classPK)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_T_C_C;

		Object[] finderArgs = new Object[] { tableId, columnId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_C_C_TABLEID_2);

			query.append(_FINDER_COLUMN_T_C_C_COLUMNID_2);

			query.append(_FINDER_COLUMN_T_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_T_C_C_TABLEID_2 = "expandoValue.tableId = ? AND ";
	private static final String _FINDER_COLUMN_T_C_C_COLUMNID_2 = "expandoValue.columnId = ? AND ";
	private static final String _FINDER_COLUMN_T_C_C_CLASSPK_2 = "expandoValue.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_T_C_D = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_C_D = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, ExpandoValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			ExpandoValueModelImpl.TABLEID_COLUMN_BITMASK |
			ExpandoValueModelImpl.COLUMNID_COLUMN_BITMASK |
			ExpandoValueModelImpl.DATA_COLUMN_BITMASK |
			ExpandoValueModelImpl.ROWID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_C_D = new FinderPath(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @return the matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByT_C_D(long tableId, long columnId,
		String data) throws SystemException {
		return findByT_C_D(tableId, columnId, data, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByT_C_D(long tableId, long columnId,
		String data, int start, int end) throws SystemException {
		return findByT_C_D(tableId, columnId, data, start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findByT_C_D(long tableId, long columnId,
		String data, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_C_D;
			finderArgs = new Object[] { tableId, columnId, data };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_T_C_D;
			finderArgs = new Object[] {
					tableId, columnId, data,
					
					start, end, orderByComparator
				};
		}

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ExpandoValue expandoValue : list) {
				if ((tableId != expandoValue.getTableId()) ||
						(columnId != expandoValue.getColumnId()) ||
						!Validator.equals(data, expandoValue.getData())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_C_D_TABLEID_2);

			query.append(_FINDER_COLUMN_T_C_D_COLUMNID_2);

			boolean bindData = false;

			if (data == null) {
				query.append(_FINDER_COLUMN_T_C_D_DATA_1);
			}
			else if (data.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_T_C_D_DATA_3);
			}
			else {
				bindData = true;

				query.append(_FINDER_COLUMN_T_C_D_DATA_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				if (bindData) {
					qPos.add(data);
				}

				if (!pagination) {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<ExpandoValue>(list);
				}
				else {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByT_C_D_First(long tableId, long columnId,
		String data, OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByT_C_D_First(tableId, columnId, data,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tableId=");
		msg.append(tableId);

		msg.append(", columnId=");
		msg.append(columnId);

		msg.append(", data=");
		msg.append(data);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the first expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByT_C_D_First(long tableId, long columnId,
		String data, OrderByComparator orderByComparator)
		throws SystemException {
		List<ExpandoValue> list = findByT_C_D(tableId, columnId, data, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByT_C_D_Last(long tableId, long columnId,
		String data, OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByT_C_D_Last(tableId, columnId, data,
				orderByComparator);

		if (expandoValue != null) {
			return expandoValue;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("tableId=");
		msg.append(tableId);

		msg.append(", columnId=");
		msg.append(columnId);

		msg.append(", data=");
		msg.append(data);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchValueException(msg.toString());
	}

	/**
	 * Returns the last expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching expando value, or <code>null</code> if a matching expando value could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByT_C_D_Last(long tableId, long columnId,
		String data, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByT_C_D(tableId, columnId, data);

		List<ExpandoValue> list = findByT_C_D(tableId, columnId, data,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the expando values before and after the current expando value in the ordered set where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param valueId the primary key of the current expando value
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue[] findByT_C_D_PrevAndNext(long valueId, long tableId,
		long columnId, String data, OrderByComparator orderByComparator)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = findByPrimaryKey(valueId);

		Session session = null;

		try {
			session = openSession();

			ExpandoValue[] array = new ExpandoValueImpl[3];

			array[0] = getByT_C_D_PrevAndNext(session, expandoValue, tableId,
					columnId, data, orderByComparator, true);

			array[1] = expandoValue;

			array[2] = getByT_C_D_PrevAndNext(session, expandoValue, tableId,
					columnId, data, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExpandoValue getByT_C_D_PrevAndNext(Session session,
		ExpandoValue expandoValue, long tableId, long columnId, String data,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_EXPANDOVALUE_WHERE);

		query.append(_FINDER_COLUMN_T_C_D_TABLEID_2);

		query.append(_FINDER_COLUMN_T_C_D_COLUMNID_2);

		boolean bindData = false;

		if (data == null) {
			query.append(_FINDER_COLUMN_T_C_D_DATA_1);
		}
		else if (data.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_T_C_D_DATA_3);
		}
		else {
			bindData = true;

			query.append(_FINDER_COLUMN_T_C_D_DATA_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ExpandoValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(tableId);

		qPos.add(columnId);

		if (bindData) {
			qPos.add(data);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(expandoValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ExpandoValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the expando values where tableId = &#63; and columnId = &#63; and data = &#63; from the database.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByT_C_D(long tableId, long columnId, String data)
		throws SystemException {
		for (ExpandoValue expandoValue : findByT_C_D(tableId, columnId, data,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values where tableId = &#63; and columnId = &#63; and data = &#63;.
	 *
	 * @param tableId the table ID
	 * @param columnId the column ID
	 * @param data the data
	 * @return the number of matching expando values
	 * @throws SystemException if a system exception occurred
	 */
	public int countByT_C_D(long tableId, long columnId, String data)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_T_C_D;

		Object[] finderArgs = new Object[] { tableId, columnId, data };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_EXPANDOVALUE_WHERE);

			query.append(_FINDER_COLUMN_T_C_D_TABLEID_2);

			query.append(_FINDER_COLUMN_T_C_D_COLUMNID_2);

			boolean bindData = false;

			if (data == null) {
				query.append(_FINDER_COLUMN_T_C_D_DATA_1);
			}
			else if (data.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_T_C_D_DATA_3);
			}
			else {
				bindData = true;

				query.append(_FINDER_COLUMN_T_C_D_DATA_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(tableId);

				qPos.add(columnId);

				if (bindData) {
					qPos.add(data);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_T_C_D_TABLEID_2 = "expandoValue.tableId = ? AND ";
	private static final String _FINDER_COLUMN_T_C_D_COLUMNID_2 = "expandoValue.columnId = ? AND ";
	private static final String _FINDER_COLUMN_T_C_D_DATA_1 = "expandoValue.data IS NULL";
	private static final String _FINDER_COLUMN_T_C_D_DATA_2 = "expandoValue.data = ?";
	private static final String _FINDER_COLUMN_T_C_D_DATA_3 = "(expandoValue.data IS NULL OR expandoValue.data = '')";

	/**
	 * Caches the expando value in the entity cache if it is enabled.
	 *
	 * @param expandoValue the expando value
	 */
	public void cacheResult(ExpandoValue expandoValue) {
		EntityCacheUtil.putResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueImpl.class, expandoValue.getPrimaryKey(), expandoValue);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R,
			new Object[] { expandoValue.getColumnId(), expandoValue.getRowId() },
			expandoValue);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C,
			new Object[] {
				expandoValue.getTableId(), expandoValue.getColumnId(),
				expandoValue.getClassPK()
			}, expandoValue);

		expandoValue.resetOriginalValues();
	}

	/**
	 * Caches the expando values in the entity cache if it is enabled.
	 *
	 * @param expandoValues the expando values
	 */
	public void cacheResult(List<ExpandoValue> expandoValues) {
		for (ExpandoValue expandoValue : expandoValues) {
			if (EntityCacheUtil.getResult(
						ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
						ExpandoValueImpl.class, expandoValue.getPrimaryKey()) == null) {
				cacheResult(expandoValue);
			}
			else {
				expandoValue.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all expando values.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ExpandoValueImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ExpandoValueImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the expando value.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ExpandoValue expandoValue) {
		EntityCacheUtil.removeResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueImpl.class, expandoValue.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(expandoValue);
	}

	@Override
	public void clearCache(List<ExpandoValue> expandoValues) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ExpandoValue expandoValue : expandoValues) {
			EntityCacheUtil.removeResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
				ExpandoValueImpl.class, expandoValue.getPrimaryKey());

			clearUniqueFindersCache(expandoValue);
		}
	}

	protected void cacheUniqueFindersCache(ExpandoValue expandoValue) {
		if (expandoValue.isNew()) {
			Object[] args = new Object[] {
					expandoValue.getColumnId(), expandoValue.getRowId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_R, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R, args,
				expandoValue);

			args = new Object[] {
					expandoValue.getTableId(), expandoValue.getColumnId(),
					expandoValue.getClassPK()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_C_C, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C, args,
				expandoValue);
		}
		else {
			ExpandoValueModelImpl expandoValueModelImpl = (ExpandoValueModelImpl)expandoValue;

			if ((expandoValueModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						expandoValue.getColumnId(), expandoValue.getRowId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_R, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_R, args,
					expandoValue);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_T_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						expandoValue.getTableId(), expandoValue.getColumnId(),
						expandoValue.getClassPK()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_T_C_C, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_T_C_C, args,
					expandoValue);
			}
		}
	}

	protected void clearUniqueFindersCache(ExpandoValue expandoValue) {
		ExpandoValueModelImpl expandoValueModelImpl = (ExpandoValueModelImpl)expandoValue;

		Object[] args = new Object[] {
				expandoValue.getColumnId(), expandoValue.getRowId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_R, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_R, args);

		if ((expandoValueModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_R.getColumnBitmask()) != 0) {
			args = new Object[] {
					expandoValueModelImpl.getOriginalColumnId(),
					expandoValueModelImpl.getOriginalRowId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_R, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_R, args);
		}

		args = new Object[] {
				expandoValue.getTableId(), expandoValue.getColumnId(),
				expandoValue.getClassPK()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_C_C, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C_C, args);

		if ((expandoValueModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_T_C_C.getColumnBitmask()) != 0) {
			args = new Object[] {
					expandoValueModelImpl.getOriginalTableId(),
					expandoValueModelImpl.getOriginalColumnId(),
					expandoValueModelImpl.getOriginalClassPK()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_C_C, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_T_C_C, args);
		}
	}

	/**
	 * Creates a new expando value with the primary key. Does not add the expando value to the database.
	 *
	 * @param valueId the primary key for the new expando value
	 * @return the new expando value
	 */
	public ExpandoValue create(long valueId) {
		ExpandoValue expandoValue = new ExpandoValueImpl();

		expandoValue.setNew(true);
		expandoValue.setPrimaryKey(valueId);

		return expandoValue;
	}

	/**
	 * Removes the expando value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param valueId the primary key of the expando value
	 * @return the expando value that was removed
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue remove(long valueId)
		throws NoSuchValueException, SystemException {
		return remove((Serializable)valueId);
	}

	/**
	 * Removes the expando value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the expando value
	 * @return the expando value that was removed
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ExpandoValue remove(Serializable primaryKey)
		throws NoSuchValueException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ExpandoValue expandoValue = (ExpandoValue)session.get(ExpandoValueImpl.class,
					primaryKey);

			if (expandoValue == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchValueException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(expandoValue);
		}
		catch (NoSuchValueException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ExpandoValue removeImpl(ExpandoValue expandoValue)
		throws SystemException {
		expandoValue = toUnwrappedModel(expandoValue);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(expandoValue)) {
				expandoValue = (ExpandoValue)session.get(ExpandoValueImpl.class,
						expandoValue.getPrimaryKeyObj());
			}

			if (expandoValue != null) {
				session.delete(expandoValue);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (expandoValue != null) {
			clearCache(expandoValue);
		}

		return expandoValue;
	}

	@Override
	public ExpandoValue updateImpl(
		com.liferay.portlet.expando.model.ExpandoValue expandoValue)
		throws SystemException {
		expandoValue = toUnwrappedModel(expandoValue);

		boolean isNew = expandoValue.isNew();

		ExpandoValueModelImpl expandoValueModelImpl = (ExpandoValueModelImpl)expandoValue;

		Session session = null;

		try {
			session = openSession();

			if (expandoValue.isNew()) {
				session.save(expandoValue);

				expandoValue.setNew(false);
			}
			else {
				session.merge(expandoValue);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ExpandoValueModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((expandoValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TABLEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						expandoValueModelImpl.getOriginalTableId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TABLEID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TABLEID,
					args);

				args = new Object[] { expandoValueModelImpl.getTableId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TABLEID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TABLEID,
					args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COLUMNID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						expandoValueModelImpl.getOriginalColumnId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COLUMNID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COLUMNID,
					args);

				args = new Object[] { expandoValueModelImpl.getColumnId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COLUMNID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COLUMNID,
					args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROWID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						expandoValueModelImpl.getOriginalRowId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ROWID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROWID,
					args);

				args = new Object[] { expandoValueModelImpl.getRowId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ROWID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ROWID,
					args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						expandoValueModelImpl.getOriginalTableId(),
						expandoValueModelImpl.getOriginalColumnId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_C,
					args);

				args = new Object[] {
						expandoValueModelImpl.getTableId(),
						expandoValueModelImpl.getColumnId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_C,
					args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_CPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						expandoValueModelImpl.getOriginalTableId(),
						expandoValueModelImpl.getOriginalClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_CPK, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_CPK,
					args);

				args = new Object[] {
						expandoValueModelImpl.getTableId(),
						expandoValueModelImpl.getClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_CPK, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_CPK,
					args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_R.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						expandoValueModelImpl.getOriginalTableId(),
						expandoValueModelImpl.getOriginalRowId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_R, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_R,
					args);

				args = new Object[] {
						expandoValueModelImpl.getTableId(),
						expandoValueModelImpl.getRowId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_R, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_R,
					args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						expandoValueModelImpl.getOriginalClassNameId(),
						expandoValueModelImpl.getOriginalClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						expandoValueModelImpl.getClassNameId(),
						expandoValueModelImpl.getClassPK()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((expandoValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_C_D.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						expandoValueModelImpl.getOriginalTableId(),
						expandoValueModelImpl.getOriginalColumnId(),
						expandoValueModelImpl.getOriginalData()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_C_D, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_C_D,
					args);

				args = new Object[] {
						expandoValueModelImpl.getTableId(),
						expandoValueModelImpl.getColumnId(),
						expandoValueModelImpl.getData()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_C_D, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_C_D,
					args);
			}
		}

		EntityCacheUtil.putResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
			ExpandoValueImpl.class, expandoValue.getPrimaryKey(), expandoValue);

		clearUniqueFindersCache(expandoValue);
		cacheUniqueFindersCache(expandoValue);

		return expandoValue;
	}

	protected ExpandoValue toUnwrappedModel(ExpandoValue expandoValue) {
		if (expandoValue instanceof ExpandoValueImpl) {
			return expandoValue;
		}

		ExpandoValueImpl expandoValueImpl = new ExpandoValueImpl();

		expandoValueImpl.setNew(expandoValue.isNew());
		expandoValueImpl.setPrimaryKey(expandoValue.getPrimaryKey());

		expandoValueImpl.setValueId(expandoValue.getValueId());
		expandoValueImpl.setCompanyId(expandoValue.getCompanyId());
		expandoValueImpl.setTableId(expandoValue.getTableId());
		expandoValueImpl.setColumnId(expandoValue.getColumnId());
		expandoValueImpl.setRowId(expandoValue.getRowId());
		expandoValueImpl.setClassNameId(expandoValue.getClassNameId());
		expandoValueImpl.setClassPK(expandoValue.getClassPK());
		expandoValueImpl.setData(expandoValue.getData());

		return expandoValueImpl;
	}

	/**
	 * Returns the expando value with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the expando value
	 * @return the expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ExpandoValue findByPrimaryKey(Serializable primaryKey)
		throws NoSuchValueException, SystemException {
		ExpandoValue expandoValue = fetchByPrimaryKey(primaryKey);

		if (expandoValue == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchValueException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return expandoValue;
	}

	/**
	 * Returns the expando value with the primary key or throws a {@link com.liferay.portlet.expando.NoSuchValueException} if it could not be found.
	 *
	 * @param valueId the primary key of the expando value
	 * @return the expando value
	 * @throws com.liferay.portlet.expando.NoSuchValueException if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue findByPrimaryKey(long valueId)
		throws NoSuchValueException, SystemException {
		return findByPrimaryKey((Serializable)valueId);
	}

	/**
	 * Returns the expando value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the expando value
	 * @return the expando value, or <code>null</code> if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ExpandoValue fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		ExpandoValue expandoValue = (ExpandoValue)EntityCacheUtil.getResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
				ExpandoValueImpl.class, primaryKey);

		if (expandoValue == _nullExpandoValue) {
			return null;
		}

		if (expandoValue == null) {
			Session session = null;

			try {
				session = openSession();

				expandoValue = (ExpandoValue)session.get(ExpandoValueImpl.class,
						primaryKey);

				if (expandoValue != null) {
					cacheResult(expandoValue);
				}
				else {
					EntityCacheUtil.putResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
						ExpandoValueImpl.class, primaryKey, _nullExpandoValue);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ExpandoValueModelImpl.ENTITY_CACHE_ENABLED,
					ExpandoValueImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return expandoValue;
	}

	/**
	 * Returns the expando value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param valueId the primary key of the expando value
	 * @return the expando value, or <code>null</code> if a expando value with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public ExpandoValue fetchByPrimaryKey(long valueId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)valueId);
	}

	/**
	 * Returns all the expando values.
	 *
	 * @return the expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the expando values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @return the range of expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the expando values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.expando.model.impl.ExpandoValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of expando values
	 * @param end the upper bound of the range of expando values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of expando values
	 * @throws SystemException if a system exception occurred
	 */
	public List<ExpandoValue> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<ExpandoValue> list = (List<ExpandoValue>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_EXPANDOVALUE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_EXPANDOVALUE;

				if (pagination) {
					sql = sql.concat(ExpandoValueModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<ExpandoValue>(list);
				}
				else {
					list = (List<ExpandoValue>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the expando values from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (ExpandoValue expandoValue : findAll()) {
			remove(expandoValue);
		}
	}

	/**
	 * Returns the number of expando values.
	 *
	 * @return the number of expando values
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_EXPANDOVALUE);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the expando value persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.expando.model.ExpandoValue")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ExpandoValue>> listenersList = new ArrayList<ModelListener<ExpandoValue>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ExpandoValue>)InstanceFactory.newInstance(
							getClass().getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ExpandoValueImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_EXPANDOVALUE = "SELECT expandoValue FROM ExpandoValue expandoValue";
	private static final String _SQL_SELECT_EXPANDOVALUE_WHERE = "SELECT expandoValue FROM ExpandoValue expandoValue WHERE ";
	private static final String _SQL_COUNT_EXPANDOVALUE = "SELECT COUNT(expandoValue) FROM ExpandoValue expandoValue";
	private static final String _SQL_COUNT_EXPANDOVALUE_WHERE = "SELECT COUNT(expandoValue) FROM ExpandoValue expandoValue WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "expandoValue.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ExpandoValue exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ExpandoValue exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ExpandoValuePersistenceImpl.class);
	private static ExpandoValue _nullExpandoValue = new ExpandoValueImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ExpandoValue> toCacheModel() {
				return _nullExpandoValueCacheModel;
			}
		};

	private static CacheModel<ExpandoValue> _nullExpandoValueCacheModel = new CacheModel<ExpandoValue>() {
			public ExpandoValue toEntityModel() {
				return _nullExpandoValue;
			}
		};
}