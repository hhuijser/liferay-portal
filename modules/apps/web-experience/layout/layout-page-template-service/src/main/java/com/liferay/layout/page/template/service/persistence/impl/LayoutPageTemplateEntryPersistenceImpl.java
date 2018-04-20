/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.layout.page.template.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateEntryImpl;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateEntryModelImpl;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateEntryPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the layout page template entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntryPersistence
 * @see com.liferay.layout.page.template.service.persistence.LayoutPageTemplateEntryUtil
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntryPersistenceImpl extends BasePersistenceImpl<LayoutPageTemplateEntry>
	implements LayoutPageTemplateEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LayoutPageTemplateEntryUtil} to access the layout page template entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutPageTemplateEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			LayoutPageTemplateEntryModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the layout page template entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<LayoutPageTemplateEntry> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchPageTemplateEntryException(msg.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByGroupId_First(long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		List<LayoutPageTemplateEntry> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchPageTemplateEntryException(msg.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByGroupId_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array = new LayoutPageTemplateEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByGroupId_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByGroupId_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByGroupId(long groupId,
		int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByGroupId(long groupId,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					LayoutPageTemplateEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					LayoutPageTemplateEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByGroupId_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(layoutPageTemplateEntryId,
				groupId, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array = new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByGroupId_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layoutPageTemplateEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_L = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_L",
			new String[] { Long.class.getName(), Long.class.getName() },
			LayoutPageTemplateEntryModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateEntryModelImpl.LAYOUTPAGETEMPLATECOLLECTIONID_COLUMN_BITMASK |
			LayoutPageTemplateEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_L = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_L",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L(long groupId,
		long layoutPageTemplateCollectionId) {
		return findByG_L(groupId, layoutPageTemplateCollectionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L(long groupId,
		long layoutPageTemplateCollectionId, int start, int end) {
		return findByG_L(groupId, layoutPageTemplateCollectionId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L(long groupId,
		long layoutPageTemplateCollectionId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return findByG_L(groupId, layoutPageTemplateCollectionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L(long groupId,
		long layoutPageTemplateCollectionId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L;
			finderArgs = new Object[] { groupId, layoutPageTemplateCollectionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_L;
			finderArgs = new Object[] {
					groupId, layoutPageTemplateCollectionId,
					
					start, end, orderByComparator
				};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
							(layoutPageTemplateCollectionId != layoutPageTemplateEntry.getLayoutPageTemplateCollectionId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(layoutPageTemplateCollectionId);

				if (!pagination) {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_First(long groupId,
		long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_L_First(groupId,
				layoutPageTemplateCollectionId, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateCollectionId=");
		msg.append(layoutPageTemplateCollectionId);

		msg.append("}");

		throw new NoSuchPageTemplateEntryException(msg.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_First(long groupId,
		long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		List<LayoutPageTemplateEntry> list = findByG_L(groupId,
				layoutPageTemplateCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_Last(long groupId,
		long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_L_Last(groupId,
				layoutPageTemplateCollectionId, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateCollectionId=");
		msg.append(layoutPageTemplateCollectionId);

		msg.append("}");

		throw new NoSuchPageTemplateEntryException(msg.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_Last(long groupId,
		long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		int count = countByG_L(groupId, layoutPageTemplateCollectionId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_L(groupId,
				layoutPageTemplateCollectionId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_L_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId,
		long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array = new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_L_PrevAndNext(session, layoutPageTemplateEntry,
					groupId, layoutPageTemplateCollectionId, orderByComparator,
					true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_L_PrevAndNext(session, layoutPageTemplateEntry,
					groupId, layoutPageTemplateCollectionId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_L_PrevAndNext(Session session,
		LayoutPageTemplateEntry layoutPageTemplateEntry, long groupId,
		long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2);

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
			query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(layoutPageTemplateCollectionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L(long groupId,
		long layoutPageTemplateCollectionId) {
		return filterFindByG_L(groupId, layoutPageTemplateCollectionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L(long groupId,
		long layoutPageTemplateCollectionId, int start, int end) {
		return filterFindByG_L(groupId, layoutPageTemplateCollectionId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L(long groupId,
		long layoutPageTemplateCollectionId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L(groupId, layoutPageTemplateCollectionId, start,
				end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					LayoutPageTemplateEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					LayoutPageTemplateEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(layoutPageTemplateCollectionId);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_L_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId,
		long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L_PrevAndNext(layoutPageTemplateEntryId, groupId,
				layoutPageTemplateCollectionId, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array = new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_L_PrevAndNext(session,
					layoutPageTemplateEntry, groupId,
					layoutPageTemplateCollectionId, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_L_PrevAndNext(session,
					layoutPageTemplateEntry, groupId,
					layoutPageTemplateCollectionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_L_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(layoutPageTemplateCollectionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 */
	@Override
	public void removeByG_L(long groupId, long layoutPageTemplateCollectionId) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry : findByG_L(
				groupId, layoutPageTemplateCollectionId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_L(long groupId, long layoutPageTemplateCollectionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_L;

		Object[] finderArgs = new Object[] {
				groupId, layoutPageTemplateCollectionId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(layoutPageTemplateCollectionId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_L(long groupId,
		long layoutPageTemplateCollectionId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_L(groupId, layoutPageTemplateCollectionId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(layoutPageTemplateCollectionId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_L_GROUPID_2 = "layoutPageTemplateEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2 =
		"layoutPageTemplateEntry.layoutPageTemplateCollectionId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() },
			LayoutPageTemplateEntryModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the layout page template entry where groupId = &#63; and name = &#63; or throws a {@link NoSuchPageTemplateEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_N(long groupId, String name)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_N(groupId,
				name);

		if (layoutPageTemplateEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchPageTemplateEntryException(msg.toString());
		}

		return layoutPageTemplateEntry;
	}

	/**
	 * Returns the layout page template entry where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_N(long groupId, String name) {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the layout page template entry where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result instanceof LayoutPageTemplateEntry) {
			LayoutPageTemplateEntry layoutPageTemplateEntry = (LayoutPageTemplateEntry)result;

			if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
					!Objects.equals(name, layoutPageTemplateEntry.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
				}

				List<LayoutPageTemplateEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, finderArgs,
						list);
				}
				else {
					LayoutPageTemplateEntry layoutPageTemplateEntry = list.get(0);

					result = layoutPageTemplateEntry;

					cacheResult(layoutPageTemplateEntry);

					if ((layoutPageTemplateEntry.getGroupId() != groupId) ||
							(layoutPageTemplateEntry.getName() == null) ||
							!layoutPageTemplateEntry.getName().equals(name)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, layoutPageTemplateEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, finderArgs);

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
			return (LayoutPageTemplateEntry)result;
		}
	}

	/**
	 * Removes the layout page template entry where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the layout page template entry that was removed
	 */
	@Override
	public LayoutPageTemplateEntry removeByG_N(long groupId, String name)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = findByG_N(groupId,
				name);

		return remove(layoutPageTemplateEntry);
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N;

		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "layoutPageTemplateEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "layoutPageTemplateEntry.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "layoutPageTemplateEntry.name = ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(layoutPageTemplateEntry.name IS NULL OR layoutPageTemplateEntry.name = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			LayoutPageTemplateEntryModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateEntryModelImpl.TYPE_COLUMN_BITMASK |
			LayoutPageTemplateEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the layout page template entries where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T(long groupId, int type) {
		return findByG_T(groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T(long groupId, int type,
		int start, int end) {
		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T(long groupId, int type,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return findByG_T(groupId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T(long groupId, int type,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] { groupId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] {
					groupId, type,
					
					start, end, orderByComparator
				};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
							(type != layoutPageTemplateEntry.getType())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

				if (!pagination) {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_First(long groupId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_T_First(groupId,
				type, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchPageTemplateEntryException(msg.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_First(long groupId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		List<LayoutPageTemplateEntry> list = findByG_T(groupId, type, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_Last(long groupId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_T_Last(groupId,
				type, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchPageTemplateEntryException(msg.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_Last(long groupId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		int count = countByG_T(groupId, type);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_T(groupId, type,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_T_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array = new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_T_PrevAndNext(session, layoutPageTemplateEntry,
					groupId, type, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_T_PrevAndNext(session, layoutPageTemplateEntry,
					groupId, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_T_PrevAndNext(Session session,
		LayoutPageTemplateEntry layoutPageTemplateEntry, long groupId,
		int type, OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2);

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
			query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T(long groupId, int type) {
		return filterFindByG_T(groupId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T(long groupId,
		int type, int start, int end) {
		return filterFindByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T(long groupId,
		int type, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T(groupId, type, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					LayoutPageTemplateEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					LayoutPageTemplateEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(type);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_T_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_PrevAndNext(layoutPageTemplateEntryId, groupId,
				type, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array = new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_T_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, type, orderByComparator,
					true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_T_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, type, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_T_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_T(long groupId, int type) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry : findByG_T(
				groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_T(long groupId, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T;

		Object[] finderArgs = new Object[] { groupId, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_T(long groupId, int type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T(groupId, type);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(type);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "layoutPageTemplateEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TYPE_2 = "layoutPageTemplateEntry.type = ?";
	private static final String _FINDER_COLUMN_G_T_TYPE_2_SQL = "layoutPageTemplateEntry.type_ = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_L_LIKEN =
		new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_L_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_L_LIKEN =
		new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_L_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, String name) {
		return findByG_L_LikeN(groupId, layoutPageTemplateCollectionId, name,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, String name, int start, int end) {
		return findByG_L_LikeN(groupId, layoutPageTemplateCollectionId, name,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, String name, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return findByG_L_LikeN(groupId, layoutPageTemplateCollectionId, name,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, String name, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_L_LIKEN;
		finderArgs = new Object[] {
				groupId, layoutPageTemplateCollectionId, name,
				
				start, end, orderByComparator
			};

		List<LayoutPageTemplateEntry> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
							(layoutPageTemplateCollectionId != layoutPageTemplateEntry.getLayoutPageTemplateCollectionId()) ||
							!StringUtil.wildcardMatches(
								layoutPageTemplateEntry.getName(), name, '_',
								'%', '\\', false)) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_L_LIKEN_GROUPID_2);

			query.append(_FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(layoutPageTemplateCollectionId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
				}

				if (!pagination) {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_LikeN_First(long groupId,
		long layoutPageTemplateCollectionId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_L_LikeN_First(groupId,
				layoutPageTemplateCollectionId, name, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateCollectionId=");
		msg.append(layoutPageTemplateCollectionId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchPageTemplateEntryException(msg.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_LikeN_First(long groupId,
		long layoutPageTemplateCollectionId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		List<LayoutPageTemplateEntry> list = findByG_L_LikeN(groupId,
				layoutPageTemplateCollectionId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_LikeN_Last(long groupId,
		long layoutPageTemplateCollectionId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_L_LikeN_Last(groupId,
				layoutPageTemplateCollectionId, name, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", layoutPageTemplateCollectionId=");
		msg.append(layoutPageTemplateCollectionId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchPageTemplateEntryException(msg.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_LikeN_Last(long groupId,
		long layoutPageTemplateCollectionId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		int count = countByG_L_LikeN(groupId, layoutPageTemplateCollectionId,
				name);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_L_LikeN(groupId,
				layoutPageTemplateCollectionId, name, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_L_LikeN_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId,
		long layoutPageTemplateCollectionId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array = new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_L_LikeN_PrevAndNext(session,
					layoutPageTemplateEntry, groupId,
					layoutPageTemplateCollectionId, name, orderByComparator,
					true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_L_LikeN_PrevAndNext(session,
					layoutPageTemplateEntry, groupId,
					layoutPageTemplateCollectionId, name, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_L_LikeN_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long layoutPageTemplateCollectionId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_L_LIKEN_GROUPID_2);

		query.append(_FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_2);
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
			query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(layoutPageTemplateCollectionId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, String name) {
		return filterFindByG_L_LikeN(groupId, layoutPageTemplateCollectionId,
			name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, String name, int start, int end) {
		return filterFindByG_L_LikeN(groupId, layoutPageTemplateCollectionId,
			name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, String name, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L_LikeN(groupId, layoutPageTemplateCollectionId,
				name, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_L_LIKEN_GROUPID_2);

		query.append(_FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					LayoutPageTemplateEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					LayoutPageTemplateEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(layoutPageTemplateCollectionId);

			if (bindName) {
				qPos.add(StringUtil.toLowerCase(name));
			}

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_L_LikeN_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId,
		long layoutPageTemplateCollectionId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L_LikeN_PrevAndNext(layoutPageTemplateEntryId,
				groupId, layoutPageTemplateCollectionId, name, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array = new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_L_LikeN_PrevAndNext(session,
					layoutPageTemplateEntry, groupId,
					layoutPageTemplateCollectionId, name, orderByComparator,
					true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_L_LikeN_PrevAndNext(session,
					layoutPageTemplateEntry, groupId,
					layoutPageTemplateCollectionId, name, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_L_LikeN_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long layoutPageTemplateCollectionId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_L_LIKEN_GROUPID_2);

		query.append(_FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(layoutPageTemplateCollectionId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 */
	@Override
	public void removeByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, String name) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry : findByG_L_LikeN(
				groupId, layoutPageTemplateCollectionId, name,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, String name) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_L_LIKEN;

		Object[] finderArgs = new Object[] {
				groupId, layoutPageTemplateCollectionId, name
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_L_LIKEN_GROUPID_2);

			query.append(_FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(layoutPageTemplateCollectionId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_L_LikeN(long groupId,
		long layoutPageTemplateCollectionId, String name) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_L_LikeN(groupId, layoutPageTemplateCollectionId,
				name);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_L_LIKEN_GROUPID_2);

		query.append(_FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_L_LIKEN_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(layoutPageTemplateCollectionId);

			if (bindName) {
				qPos.add(StringUtil.toLowerCase(name));
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_L_LIKEN_GROUPID_2 = "layoutPageTemplateEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2 =
		"layoutPageTemplateEntry.layoutPageTemplateCollectionId = ? AND ";
	private static final String _FINDER_COLUMN_G_L_LIKEN_NAME_1 = "layoutPageTemplateEntry.name IS NULL";
	private static final String _FINDER_COLUMN_G_L_LIKEN_NAME_2 = "lower(layoutPageTemplateEntry.name) LIKE ?";
	private static final String _FINDER_COLUMN_G_L_LIKEN_NAME_3 = "(layoutPageTemplateEntry.name IS NULL OR layoutPageTemplateEntry.name LIKE '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_LIKEN =
		new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T_LIKEN =
		new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_T_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_LikeN(long groupId,
		String name, int type) {
		return findByG_T_LikeN(groupId, name, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_LikeN(long groupId,
		String name, int type, int start, int end) {
		return findByG_T_LikeN(groupId, name, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_LikeN(long groupId,
		String name, int type, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return findByG_T_LikeN(groupId, name, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_LikeN(long groupId,
		String name, int type, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_LIKEN;
		finderArgs = new Object[] {
				groupId, name, type,
				
				start, end, orderByComparator
			};

		List<LayoutPageTemplateEntry> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
							!StringUtil.wildcardMatches(
								layoutPageTemplateEntry.getName(), name, '_',
								'%', '\\', false) ||
							(type != layoutPageTemplateEntry.getType())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_T_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_2);
			}

			query.append(_FINDER_COLUMN_G_T_LIKEN_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
				}

				qPos.add(type);

				if (!pagination) {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_LikeN_First(long groupId,
		String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_T_LikeN_First(groupId,
				name, type, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchPageTemplateEntryException(msg.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_LikeN_First(long groupId,
		String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		List<LayoutPageTemplateEntry> list = findByG_T_LikeN(groupId, name,
				type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_LikeN_Last(long groupId,
		String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_T_LikeN_Last(groupId,
				name, type, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchPageTemplateEntryException(msg.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_LikeN_Last(long groupId,
		String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		int count = countByG_T_LikeN(groupId, name, type);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_T_LikeN(groupId, name,
				type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_T_LikeN_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId, String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array = new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_T_LikeN_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, name, type,
					orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_T_LikeN_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, name, type,
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

	protected LayoutPageTemplateEntry getByG_T_LikeN_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_T_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_T_LIKEN_TYPE_2);

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
			query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_LikeN(long groupId,
		String name, int type) {
		return filterFindByG_T_LikeN(groupId, name, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_LikeN(long groupId,
		String name, int type, int start, int end) {
		return filterFindByG_T_LikeN(groupId, name, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_LikeN(long groupId,
		String name, int type, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_LikeN(groupId, name, type, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_T_LIKEN_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					LayoutPageTemplateEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					LayoutPageTemplateEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(StringUtil.toLowerCase(name));
			}

			qPos.add(type);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_T_LikeN_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId, String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_LikeN_PrevAndNext(layoutPageTemplateEntryId,
				groupId, name, type, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array = new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_T_LikeN_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, name, type,
					orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_T_LikeN_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, name, type,
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

	protected LayoutPageTemplateEntry filterGetByG_T_LikeN_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_T_LIKEN_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(StringUtil.toLowerCase(name));
		}

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 */
	@Override
	public void removeByG_T_LikeN(long groupId, String name, int type) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry : findByG_T_LikeN(
				groupId, name, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_T_LikeN(long groupId, String name, int type) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_T_LIKEN;

		Object[] finderArgs = new Object[] { groupId, name, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_T_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_1);
			}
			else if (name.equals("")) {
				query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_2);
			}

			query.append(_FINDER_COLUMN_G_T_LIKEN_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
				}

				qPos.add(type);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_T_LikeN(long groupId, String name, int type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T_LikeN(groupId, name, type);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_T_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_1);
		}
		else if (name.equals("")) {
			query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_T_LIKEN_NAME_2);
		}

		query.append(_FINDER_COLUMN_G_T_LIKEN_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(StringUtil.toLowerCase(name));
			}

			qPos.add(type);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_T_LIKEN_GROUPID_2 = "layoutPageTemplateEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_LIKEN_NAME_1 = "layoutPageTemplateEntry.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_T_LIKEN_NAME_2 = "lower(layoutPageTemplateEntry.name) LIKE ? AND ";
	private static final String _FINDER_COLUMN_G_T_LIKEN_NAME_3 = "(layoutPageTemplateEntry.name IS NULL OR layoutPageTemplateEntry.name LIKE '') AND ";
	private static final String _FINDER_COLUMN_G_T_LIKEN_TYPE_2 = "layoutPageTemplateEntry.type = ?";
	private static final String _FINDER_COLUMN_G_T_LIKEN_TYPE_2_SQL = "layoutPageTemplateEntry.type_ = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_D = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_D =
		new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			LayoutPageTemplateEntryModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutPageTemplateEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			LayoutPageTemplateEntryModelImpl.CLASSTYPEID_COLUMN_BITMASK |
			LayoutPageTemplateEntryModelImpl.DEFAULTTEMPLATE_COLUMN_BITMASK |
			LayoutPageTemplateEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C_D = new FinderPath(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_D(long groupId,
		long classNameId, long classTypeId, boolean defaultTemplate) {
		return findByG_C_C_D(groupId, classNameId, classTypeId,
			defaultTemplate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_D(long groupId,
		long classNameId, long classTypeId, boolean defaultTemplate, int start,
		int end) {
		return findByG_C_C_D(groupId, classNameId, classTypeId,
			defaultTemplate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_D(long groupId,
		long classNameId, long classTypeId, boolean defaultTemplate, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return findByG_C_C_D(groupId, classNameId, classTypeId,
			defaultTemplate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_D(long groupId,
		long classNameId, long classTypeId, boolean defaultTemplate, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_D;
			finderArgs = new Object[] {
					groupId, classNameId, classTypeId, defaultTemplate
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_C_D;
			finderArgs = new Object[] {
					groupId, classNameId, classTypeId, defaultTemplate,
					
					start, end, orderByComparator
				};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
							(classNameId != layoutPageTemplateEntry.getClassNameId()) ||
							(classTypeId != layoutPageTemplateEntry.getClassTypeId()) ||
							(defaultTemplate != layoutPageTemplateEntry.isDefaultTemplate())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_D_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2);

			query.append(_FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classTypeId);

				qPos.add(defaultTemplate);

				if (!pagination) {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_D_First(long groupId,
		long classNameId, long classTypeId, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_C_C_D_First(groupId,
				classNameId, classTypeId, defaultTemplate, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classTypeId=");
		msg.append(classTypeId);

		msg.append(", defaultTemplate=");
		msg.append(defaultTemplate);

		msg.append("}");

		throw new NoSuchPageTemplateEntryException(msg.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_D_First(long groupId,
		long classNameId, long classTypeId, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		List<LayoutPageTemplateEntry> list = findByG_C_C_D(groupId,
				classNameId, classTypeId, defaultTemplate, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_D_Last(long groupId,
		long classNameId, long classTypeId, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_C_C_D_Last(groupId,
				classNameId, classTypeId, defaultTemplate, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classTypeId=");
		msg.append(classTypeId);

		msg.append(", defaultTemplate=");
		msg.append(defaultTemplate);

		msg.append("}");

		throw new NoSuchPageTemplateEntryException(msg.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_D_Last(long groupId,
		long classNameId, long classTypeId, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		int count = countByG_C_C_D(groupId, classNameId, classTypeId,
				defaultTemplate);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_C_C_D(groupId,
				classNameId, classTypeId, defaultTemplate, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_C_C_D_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId, long classNameId,
		long classTypeId, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array = new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_C_C_D_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, classNameId, classTypeId,
					defaultTemplate, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_C_C_D_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, classNameId, classTypeId,
					defaultTemplate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_C_C_D_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_D_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2);

		query.append(_FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2);

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
			query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classTypeId);

		qPos.add(defaultTemplate);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_D(long groupId,
		long classNameId, long classTypeId, boolean defaultTemplate) {
		return filterFindByG_C_C_D(groupId, classNameId, classTypeId,
			defaultTemplate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_D(long groupId,
		long classNameId, long classTypeId, boolean defaultTemplate, int start,
		int end) {
		return filterFindByG_C_C_D(groupId, classNameId, classTypeId,
			defaultTemplate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_D(long groupId,
		long classNameId, long classTypeId, boolean defaultTemplate, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_D(groupId, classNameId, classTypeId,
				defaultTemplate, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_D_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2);

		query.append(_FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS,
					LayoutPageTemplateEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE,
					LayoutPageTemplateEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classTypeId);

			qPos.add(defaultTemplate);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
				getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_C_C_D_PrevAndNext(
		long layoutPageTemplateEntryId, long groupId, long classNameId,
		long classTypeId, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_D_PrevAndNext(layoutPageTemplateEntryId,
				groupId, classNameId, classTypeId, defaultTemplate,
				orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array = new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_C_C_D_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, classNameId, classTypeId,
					defaultTemplate, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_C_C_D_PrevAndNext(session,
					layoutPageTemplateEntry, groupId, classNameId, classTypeId,
					defaultTemplate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_C_C_D_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(8 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_C_D_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2);

		query.append(_FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		qPos.add(classTypeId);

		qPos.add(defaultTemplate);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(layoutPageTemplateEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LayoutPageTemplateEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 */
	@Override
	public void removeByG_C_C_D(long groupId, long classNameId,
		long classTypeId, boolean defaultTemplate) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry : findByG_C_C_D(
				groupId, classNameId, classTypeId, defaultTemplate,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_C_C_D(long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C_D;

		Object[] finderArgs = new Object[] {
				groupId, classNameId, classTypeId, defaultTemplate
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_D_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2);

			query.append(_FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classTypeId);

				qPos.add(defaultTemplate);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C_D(long groupId, long classNameId,
		long classTypeId, boolean defaultTemplate) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C_D(groupId, classNameId, classTypeId,
				defaultTemplate);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_C_C_D_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2);

		query.append(_FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				LayoutPageTemplateEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			qPos.add(classTypeId);

			qPos.add(defaultTemplate);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_C_C_D_GROUPID_2 = "layoutPageTemplateEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2 = "layoutPageTemplateEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2 = "layoutPageTemplateEntry.classTypeId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2 = "layoutPageTemplateEntry.defaultTemplate = ?";

	public LayoutPageTemplateEntryPersistenceImpl() {
		setModelClass(LayoutPageTemplateEntry.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the layout page template entry in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateEntry the layout page template entry
	 */
	@Override
	public void cacheResult(LayoutPageTemplateEntry layoutPageTemplateEntry) {
		entityCache.putResult(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			layoutPageTemplateEntry.getPrimaryKey(), layoutPageTemplateEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				layoutPageTemplateEntry.getGroupId(),
				layoutPageTemplateEntry.getName()
			}, layoutPageTemplateEntry);

		layoutPageTemplateEntry.resetOriginalValues();
	}

	/**
	 * Caches the layout page template entries in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateEntries the layout page template entries
	 */
	@Override
	public void cacheResult(
		List<LayoutPageTemplateEntry> layoutPageTemplateEntries) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry : layoutPageTemplateEntries) {
			if (entityCache.getResult(
						LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPageTemplateEntryImpl.class,
						layoutPageTemplateEntry.getPrimaryKey()) == null) {
				cacheResult(layoutPageTemplateEntry);
			}
			else {
				layoutPageTemplateEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout page template entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutPageTemplateEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout page template entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutPageTemplateEntry layoutPageTemplateEntry) {
		entityCache.removeResult(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			layoutPageTemplateEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LayoutPageTemplateEntryModelImpl)layoutPageTemplateEntry,
			true);
	}

	@Override
	public void clearCache(
		List<LayoutPageTemplateEntry> layoutPageTemplateEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutPageTemplateEntry layoutPageTemplateEntry : layoutPageTemplateEntries) {
			entityCache.removeResult(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateEntryImpl.class,
				layoutPageTemplateEntry.getPrimaryKey());

			clearUniqueFindersCache((LayoutPageTemplateEntryModelImpl)layoutPageTemplateEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutPageTemplateEntryModelImpl layoutPageTemplateEntryModelImpl) {
		Object[] args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.getName()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_N, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_N, args,
			layoutPageTemplateEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LayoutPageTemplateEntryModelImpl layoutPageTemplateEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.getName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}

		if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_N.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getOriginalGroupId(),
					layoutPageTemplateEntryModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_N, args);
		}
	}

	/**
	 * Creates a new layout page template entry with the primary key. Does not add the layout page template entry to the database.
	 *
	 * @param layoutPageTemplateEntryId the primary key for the new layout page template entry
	 * @return the new layout page template entry
	 */
	@Override
	public LayoutPageTemplateEntry create(long layoutPageTemplateEntryId) {
		LayoutPageTemplateEntry layoutPageTemplateEntry = new LayoutPageTemplateEntryImpl();

		layoutPageTemplateEntry.setNew(true);
		layoutPageTemplateEntry.setPrimaryKey(layoutPageTemplateEntryId);

		layoutPageTemplateEntry.setCompanyId(companyProvider.getCompanyId());

		return layoutPageTemplateEntry;
	}

	/**
	 * Removes the layout page template entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the layout page template entry
	 * @return the layout page template entry that was removed
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry remove(long layoutPageTemplateEntryId)
		throws NoSuchPageTemplateEntryException {
		return remove((Serializable)layoutPageTemplateEntryId);
	}

	/**
	 * Removes the layout page template entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout page template entry
	 * @return the layout page template entry that was removed
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry remove(Serializable primaryKey)
		throws NoSuchPageTemplateEntryException {
		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry layoutPageTemplateEntry = (LayoutPageTemplateEntry)session.get(LayoutPageTemplateEntryImpl.class,
					primaryKey);

			if (layoutPageTemplateEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPageTemplateEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutPageTemplateEntry);
		}
		catch (NoSuchPageTemplateEntryException nsee) {
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
	protected LayoutPageTemplateEntry removeImpl(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {
		layoutPageTemplateEntry = toUnwrappedModel(layoutPageTemplateEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutPageTemplateEntry)) {
				layoutPageTemplateEntry = (LayoutPageTemplateEntry)session.get(LayoutPageTemplateEntryImpl.class,
						layoutPageTemplateEntry.getPrimaryKeyObj());
			}

			if (layoutPageTemplateEntry != null) {
				session.delete(layoutPageTemplateEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutPageTemplateEntry != null) {
			clearCache(layoutPageTemplateEntry);
		}

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry updateImpl(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {
		layoutPageTemplateEntry = toUnwrappedModel(layoutPageTemplateEntry);

		boolean isNew = layoutPageTemplateEntry.isNew();

		LayoutPageTemplateEntryModelImpl layoutPageTemplateEntryModelImpl = (LayoutPageTemplateEntryModelImpl)layoutPageTemplateEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutPageTemplateEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutPageTemplateEntry.setCreateDate(now);
			}
			else {
				layoutPageTemplateEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!layoutPageTemplateEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutPageTemplateEntry.setModifiedDate(now);
			}
			else {
				layoutPageTemplateEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutPageTemplateEntry.isNew()) {
				session.save(layoutPageTemplateEntry);

				layoutPageTemplateEntry.setNew(false);
			}
			else {
				layoutPageTemplateEntry = (LayoutPageTemplateEntry)session.merge(layoutPageTemplateEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutPageTemplateEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.getLayoutPageTemplateCollectionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_L, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L,
				args);

			args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
				args);

			args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.getClassNameId(),
					layoutPageTemplateEntryModelImpl.getClassTypeId(),
					layoutPageTemplateEntryModelImpl.isDefaultTemplate()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_D, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_D,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateEntryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						layoutPageTemplateEntryModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateEntryModelImpl.getOriginalGroupId(),
						layoutPageTemplateEntryModelImpl.getOriginalLayoutPageTemplateCollectionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_L, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L,
					args);

				args = new Object[] {
						layoutPageTemplateEntryModelImpl.getGroupId(),
						layoutPageTemplateEntryModelImpl.getLayoutPageTemplateCollectionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_L, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_L,
					args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateEntryModelImpl.getOriginalGroupId(),
						layoutPageTemplateEntryModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);

				args = new Object[] {
						layoutPageTemplateEntryModelImpl.getGroupId(),
						layoutPageTemplateEntryModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_D.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutPageTemplateEntryModelImpl.getOriginalGroupId(),
						layoutPageTemplateEntryModelImpl.getOriginalClassNameId(),
						layoutPageTemplateEntryModelImpl.getOriginalClassTypeId(),
						layoutPageTemplateEntryModelImpl.getOriginalDefaultTemplate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_D, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_D,
					args);

				args = new Object[] {
						layoutPageTemplateEntryModelImpl.getGroupId(),
						layoutPageTemplateEntryModelImpl.getClassNameId(),
						layoutPageTemplateEntryModelImpl.getClassTypeId(),
						layoutPageTemplateEntryModelImpl.getDefaultTemplate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C_D, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C_C_D,
					args);
			}
		}

		entityCache.putResult(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateEntryImpl.class,
			layoutPageTemplateEntry.getPrimaryKey(), layoutPageTemplateEntry,
			false);

		clearUniqueFindersCache(layoutPageTemplateEntryModelImpl, false);
		cacheUniqueFindersCache(layoutPageTemplateEntryModelImpl);

		layoutPageTemplateEntry.resetOriginalValues();

		return layoutPageTemplateEntry;
	}

	protected LayoutPageTemplateEntry toUnwrappedModel(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {
		if (layoutPageTemplateEntry instanceof LayoutPageTemplateEntryImpl) {
			return layoutPageTemplateEntry;
		}

		LayoutPageTemplateEntryImpl layoutPageTemplateEntryImpl = new LayoutPageTemplateEntryImpl();

		layoutPageTemplateEntryImpl.setNew(layoutPageTemplateEntry.isNew());
		layoutPageTemplateEntryImpl.setPrimaryKey(layoutPageTemplateEntry.getPrimaryKey());

		layoutPageTemplateEntryImpl.setLayoutPageTemplateEntryId(layoutPageTemplateEntry.getLayoutPageTemplateEntryId());
		layoutPageTemplateEntryImpl.setGroupId(layoutPageTemplateEntry.getGroupId());
		layoutPageTemplateEntryImpl.setCompanyId(layoutPageTemplateEntry.getCompanyId());
		layoutPageTemplateEntryImpl.setUserId(layoutPageTemplateEntry.getUserId());
		layoutPageTemplateEntryImpl.setUserName(layoutPageTemplateEntry.getUserName());
		layoutPageTemplateEntryImpl.setCreateDate(layoutPageTemplateEntry.getCreateDate());
		layoutPageTemplateEntryImpl.setModifiedDate(layoutPageTemplateEntry.getModifiedDate());
		layoutPageTemplateEntryImpl.setLayoutPageTemplateCollectionId(layoutPageTemplateEntry.getLayoutPageTemplateCollectionId());
		layoutPageTemplateEntryImpl.setClassNameId(layoutPageTemplateEntry.getClassNameId());
		layoutPageTemplateEntryImpl.setClassTypeId(layoutPageTemplateEntry.getClassTypeId());
		layoutPageTemplateEntryImpl.setName(layoutPageTemplateEntry.getName());
		layoutPageTemplateEntryImpl.setType(layoutPageTemplateEntry.getType());
		layoutPageTemplateEntryImpl.setHtmlPreviewEntryId(layoutPageTemplateEntry.getHtmlPreviewEntryId());
		layoutPageTemplateEntryImpl.setDefaultTemplate(layoutPageTemplateEntry.isDefaultTemplate());

		return layoutPageTemplateEntryImpl;
	}

	/**
	 * Returns the layout page template entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template entry
	 * @return the layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPageTemplateEntryException {
		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByPrimaryKey(primaryKey);

		if (layoutPageTemplateEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPageTemplateEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layoutPageTemplateEntry;
	}

	/**
	 * Returns the layout page template entry with the primary key or throws a {@link NoSuchPageTemplateEntryException} if it could not be found.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the layout page template entry
	 * @return the layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByPrimaryKey(
		long layoutPageTemplateEntryId) throws NoSuchPageTemplateEntryException {
		return findByPrimaryKey((Serializable)layoutPageTemplateEntryId);
	}

	/**
	 * Returns the layout page template entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template entry
	 * @return the layout page template entry, or <code>null</code> if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = (LayoutPageTemplateEntry)serializable;

		if (layoutPageTemplateEntry == null) {
			Session session = null;

			try {
				session = openSession();

				layoutPageTemplateEntry = (LayoutPageTemplateEntry)session.get(LayoutPageTemplateEntryImpl.class,
						primaryKey);

				if (layoutPageTemplateEntry != null) {
					cacheResult(layoutPageTemplateEntry);
				}
				else {
					entityCache.putResult(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
						LayoutPageTemplateEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return layoutPageTemplateEntry;
	}

	/**
	 * Returns the layout page template entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the layout page template entry
	 * @return the layout page template entry, or <code>null</code> if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByPrimaryKey(
		long layoutPageTemplateEntryId) {
		return fetchByPrimaryKey((Serializable)layoutPageTemplateEntryId);
	}

	@Override
	public Map<Serializable, LayoutPageTemplateEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LayoutPageTemplateEntry> map = new HashMap<Serializable, LayoutPageTemplateEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByPrimaryKey(primaryKey);

			if (layoutPageTemplateEntry != null) {
				map.put(primaryKey, layoutPageTemplateEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LayoutPageTemplateEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (LayoutPageTemplateEntry layoutPageTemplateEntry : (List<LayoutPageTemplateEntry>)q.list()) {
				map.put(layoutPageTemplateEntry.getPrimaryKeyObj(),
					layoutPageTemplateEntry);

				cacheResult(layoutPageTemplateEntry);

				uncachedPrimaryKeys.remove(layoutPageTemplateEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(LayoutPageTemplateEntryModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateEntryImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the layout page template entries.
	 *
	 * @return the layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LayoutPageTemplateEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findAll(int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean retrieveFromCache) {
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

		List<LayoutPageTemplateEntry> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTPAGETEMPLATEENTRY;

				if (pagination) {
					sql = sql.concat(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the layout page template entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry : findAll()) {
			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries.
	 *
	 * @return the number of layout page template entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutPageTemplateEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout page template entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(LayoutPageTemplateEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEENTRY = "SELECT layoutPageTemplateEntry FROM LayoutPageTemplateEntry layoutPageTemplateEntry";
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE_PKS_IN =
		"SELECT layoutPageTemplateEntry FROM LayoutPageTemplateEntry layoutPageTemplateEntry WHERE layoutPageTemplateEntryId IN (";
	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE = "SELECT layoutPageTemplateEntry FROM LayoutPageTemplateEntry layoutPageTemplateEntry WHERE ";
	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATEENTRY = "SELECT COUNT(layoutPageTemplateEntry) FROM LayoutPageTemplateEntry layoutPageTemplateEntry";
	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE = "SELECT COUNT(layoutPageTemplateEntry) FROM LayoutPageTemplateEntry layoutPageTemplateEntry WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "layoutPageTemplateEntry.layoutPageTemplateEntryId";
	private static final String _FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE =
		"SELECT DISTINCT {layoutPageTemplateEntry.*} FROM LayoutPageTemplateEntry layoutPageTemplateEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {LayoutPageTemplateEntry.*} FROM (SELECT DISTINCT layoutPageTemplateEntry.layoutPageTemplateEntryId FROM LayoutPageTemplateEntry layoutPageTemplateEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN LayoutPageTemplateEntry ON TEMP_TABLE.layoutPageTemplateEntryId = LayoutPageTemplateEntry.layoutPageTemplateEntryId";
	private static final String _FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE = "SELECT COUNT(DISTINCT layoutPageTemplateEntry.layoutPageTemplateEntryId) AS COUNT_VALUE FROM LayoutPageTemplateEntry layoutPageTemplateEntry WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "layoutPageTemplateEntry";
	private static final String _FILTER_ENTITY_TABLE = "LayoutPageTemplateEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutPageTemplateEntry.";
	private static final String _ORDER_BY_ENTITY_TABLE = "LayoutPageTemplateEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutPageTemplateEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutPageTemplateEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LayoutPageTemplateEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
}