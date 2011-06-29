/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureLinkModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the d d m structure link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLinkPersistence
 * @see DDMStructureLinkUtil
 * @generated
 */
public class DDMStructureLinkPersistenceImpl extends BasePersistenceImpl<DDMStructureLink>
	implements DDMStructureLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMStructureLinkUtil} to access the d d m structure link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMStructureLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_CLASSNAMEID = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByClassNameId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_CLASSNAMEID = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByClassNameId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_CLASSPK = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_ENTITY, "fetchByClassPK",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_CLASSPK = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByClassPK",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_STRUCTUREID = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findByStructureId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_STRUCTUREID = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countByStructureId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkModelImpl.FINDER_CACHE_ENABLED,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the d d m structure link in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLink the d d m structure link
	 */
	public void cacheResult(DDMStructureLink ddmStructureLink) {
		EntityCacheUtil.putResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkImpl.class, ddmStructureLink.getPrimaryKey(),
			ddmStructureLink);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CLASSPK,
			new Object[] { Long.valueOf(ddmStructureLink.getClassPK()) },
			ddmStructureLink);

		ddmStructureLink.resetOriginalValues();
	}

	/**
	 * Caches the d d m structure links in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLinks the d d m structure links
	 */
	public void cacheResult(List<DDMStructureLink> ddmStructureLinks) {
		for (DDMStructureLink ddmStructureLink : ddmStructureLinks) {
			if (EntityCacheUtil.getResult(
						DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureLinkImpl.class,
						ddmStructureLink.getPrimaryKey(), this) == null) {
				cacheResult(ddmStructureLink);
			}
		}
	}

	/**
	 * Clears the cache for all d d m structure links.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DDMStructureLinkImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DDMStructureLinkImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the d d m structure link.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMStructureLink ddmStructureLink) {
		EntityCacheUtil.removeResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkImpl.class, ddmStructureLink.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CLASSPK,
			new Object[] { Long.valueOf(ddmStructureLink.getClassPK()) });
	}

	/**
	 * Creates a new d d m structure link with the primary key. Does not add the d d m structure link to the database.
	 *
	 * @param structureLinkId the primary key for the new d d m structure link
	 * @return the new d d m structure link
	 */
	public DDMStructureLink create(long structureLinkId) {
		DDMStructureLink ddmStructureLink = new DDMStructureLinkImpl();

		ddmStructureLink.setNew(true);
		ddmStructureLink.setPrimaryKey(structureLinkId);

		return ddmStructureLink;
	}

	/**
	 * Removes the d d m structure link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m structure link
	 * @return the d d m structure link that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a d d m structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructureLink remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the d d m structure link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureLinkId the primary key of the d d m structure link
	 * @return the d d m structure link that was removed
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink remove(long structureLinkId)
		throws NoSuchStructureLinkException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DDMStructureLink ddmStructureLink = (DDMStructureLink)session.get(DDMStructureLinkImpl.class,
					Long.valueOf(structureLinkId));

			if (ddmStructureLink == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
						structureLinkId);
				}

				throw new NoSuchStructureLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					structureLinkId);
			}

			return ddmStructureLinkPersistence.remove(ddmStructureLink);
		}
		catch (NoSuchStructureLinkException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Removes the d d m structure link from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmStructureLink the d d m structure link
	 * @return the d d m structure link that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructureLink remove(DDMStructureLink ddmStructureLink)
		throws SystemException {
		return super.remove(ddmStructureLink);
	}

	@Override
	protected DDMStructureLink removeImpl(DDMStructureLink ddmStructureLink)
		throws SystemException {
		ddmStructureLink = toUnwrappedModel(ddmStructureLink);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, ddmStructureLink);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		DDMStructureLinkModelImpl ddmStructureLinkModelImpl = (DDMStructureLinkModelImpl)ddmStructureLink;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CLASSPK,
			new Object[] { Long.valueOf(ddmStructureLinkModelImpl.getClassPK()) });

		EntityCacheUtil.removeResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkImpl.class, ddmStructureLink.getPrimaryKey());

		return ddmStructureLink;
	}

	@Override
	public DDMStructureLink updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink ddmStructureLink,
		boolean merge) throws SystemException {
		ddmStructureLink = toUnwrappedModel(ddmStructureLink);

		boolean isNew = ddmStructureLink.isNew();

		DDMStructureLinkModelImpl ddmStructureLinkModelImpl = (DDMStructureLinkModelImpl)ddmStructureLink;

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, ddmStructureLink, merge);

			ddmStructureLink.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureLinkImpl.class, ddmStructureLink.getPrimaryKey(),
			ddmStructureLink);

		if (!isNew &&
				(ddmStructureLink.getClassPK() != ddmStructureLinkModelImpl.getOriginalClassPK())) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CLASSPK,
				new Object[] {
					Long.valueOf(ddmStructureLinkModelImpl.getOriginalClassPK())
				});
		}

		if (isNew ||
				(ddmStructureLink.getClassPK() != ddmStructureLinkModelImpl.getOriginalClassPK())) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CLASSPK,
				new Object[] { Long.valueOf(ddmStructureLink.getClassPK()) },
				ddmStructureLink);
		}

		return ddmStructureLink;
	}

	protected DDMStructureLink toUnwrappedModel(
		DDMStructureLink ddmStructureLink) {
		if (ddmStructureLink instanceof DDMStructureLinkImpl) {
			return ddmStructureLink;
		}

		DDMStructureLinkImpl ddmStructureLinkImpl = new DDMStructureLinkImpl();

		ddmStructureLinkImpl.setNew(ddmStructureLink.isNew());
		ddmStructureLinkImpl.setPrimaryKey(ddmStructureLink.getPrimaryKey());

		ddmStructureLinkImpl.setStructureLinkId(ddmStructureLink.getStructureLinkId());
		ddmStructureLinkImpl.setClassNameId(ddmStructureLink.getClassNameId());
		ddmStructureLinkImpl.setClassPK(ddmStructureLink.getClassPK());
		ddmStructureLinkImpl.setStructureId(ddmStructureLink.getStructureId());

		return ddmStructureLinkImpl;
	}

	/**
	 * Returns the d d m structure link with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure link
	 * @return the d d m structure link
	 * @throws com.liferay.portal.NoSuchModelException if a d d m structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructureLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d d m structure link with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException} if it could not be found.
	 *
	 * @param structureLinkId the primary key of the d d m structure link
	 * @return the d d m structure link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink findByPrimaryKey(long structureLinkId)
		throws NoSuchStructureLinkException, SystemException {
		DDMStructureLink ddmStructureLink = fetchByPrimaryKey(structureLinkId);

		if (ddmStructureLink == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + structureLinkId);
			}

			throw new NoSuchStructureLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				structureLinkId);
		}

		return ddmStructureLink;
	}

	/**
	 * Returns the d d m structure link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure link
	 * @return the d d m structure link, or <code>null</code> if a d d m structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DDMStructureLink fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the d d m structure link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureLinkId the primary key of the d d m structure link
	 * @return the d d m structure link, or <code>null</code> if a d d m structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink fetchByPrimaryKey(long structureLinkId)
		throws SystemException {
		DDMStructureLink ddmStructureLink = (DDMStructureLink)EntityCacheUtil.getResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
				DDMStructureLinkImpl.class, structureLinkId, this);

		if (ddmStructureLink == _nullDDMStructureLink) {
			return null;
		}

		if (ddmStructureLink == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				ddmStructureLink = (DDMStructureLink)session.get(DDMStructureLinkImpl.class,
						Long.valueOf(structureLinkId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (ddmStructureLink != null) {
					cacheResult(ddmStructureLink);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DDMStructureLinkModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureLinkImpl.class, structureLinkId,
						_nullDDMStructureLink);
				}

				closeSession(session);
			}
		}

		return ddmStructureLink;
	}

	/**
	 * Returns all the d d m structure links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching d d m structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> findByClassNameId(long classNameId)
		throws SystemException {
		return findByClassNameId(classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structure links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @return the range of matching d d m structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> findByClassNameId(long classNameId,
		int start, int end) throws SystemException {
		return findByClassNameId(classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structure links where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> findByClassNameId(long classNameId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				classNameId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDMStructureLink> list = (List<DDMStructureLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CLASSNAMEID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				list = (List<DDMStructureLink>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_CLASSNAMEID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_CLASSNAMEID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d d m structure link in the ordered set where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink findByClassNameId_First(long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureLinkException, SystemException {
		List<DDMStructureLink> list = findByClassNameId(classNameId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last d d m structure link in the ordered set where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink findByClassNameId_Last(long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureLinkException, SystemException {
		int count = countByClassNameId(classNameId);

		List<DDMStructureLink> list = findByClassNameId(classNameId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the d d m structure links before and after the current d d m structure link in the ordered set where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureLinkId the primary key of the current d d m structure link
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink[] findByClassNameId_PrevAndNext(
		long structureLinkId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureLinkException, SystemException {
		DDMStructureLink ddmStructureLink = findByPrimaryKey(structureLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLink[] array = new DDMStructureLinkImpl[3];

			array[0] = getByClassNameId_PrevAndNext(session, ddmStructureLink,
					classNameId, orderByComparator, true);

			array[1] = ddmStructureLink;

			array[2] = getByClassNameId_PrevAndNext(session, ddmStructureLink,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureLink getByClassNameId_PrevAndNext(Session session,
		DDMStructureLink ddmStructureLink, long classNameId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

		query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(ddmStructureLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructureLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structure links that the user has permission to view where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching d d m structure links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> filterFindByClassNameId(long classNameId)
		throws SystemException {
		return filterFindByClassNameId(classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structure links that the user has permission to view where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @return the range of matching d d m structure links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> filterFindByClassNameId(long classNameId,
		int start, int end) throws SystemException {
		return filterFindByClassNameId(classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structure links that the user has permissions to view where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structure links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> filterFindByClassNameId(long classNameId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByClassNameId(classNameId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(2);
		}

		query.append(_FILTER_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

		query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

		appendGroupByComparator(query, _FILTER_COLUMN_PK);

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructureLink.class.getName(), _FILTER_COLUMN_PK);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureLinkImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureLinkImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			return (List<DDMStructureLink>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the d d m structure links before and after the current d d m structure link in the ordered set of d d m structure links that the user has permission to view where classNameId = &#63;.
	 *
	 * @param structureLinkId the primary key of the current d d m structure link
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink[] filterFindByClassNameId_PrevAndNext(
		long structureLinkId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureLinkException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByClassNameId_PrevAndNext(structureLinkId, classNameId,
				orderByComparator);
		}

		DDMStructureLink ddmStructureLink = findByPrimaryKey(structureLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLink[] array = new DDMStructureLinkImpl[3];

			array[0] = filterGetByClassNameId_PrevAndNext(session,
					ddmStructureLink, classNameId, orderByComparator, true);

			array[1] = ddmStructureLink;

			array[2] = filterGetByClassNameId_PrevAndNext(session,
					ddmStructureLink, classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureLink filterGetByClassNameId_PrevAndNext(
		Session session, DDMStructureLink ddmStructureLink, long classNameId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_FILTER_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

		query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

		appendGroupByComparator(query, _FILTER_COLUMN_PK);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructureLink.class.getName(), _FILTER_COLUMN_PK);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureLinkImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureLinkImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(ddmStructureLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructureLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the d d m structure link where classPK = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException} if it could not be found.
	 *
	 * @param classPK the class p k
	 * @return the matching d d m structure link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink findByClassPK(long classPK)
		throws NoSuchStructureLinkException, SystemException {
		DDMStructureLink ddmStructureLink = fetchByClassPK(classPK);

		if (ddmStructureLink == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureLinkException(msg.toString());
		}

		return ddmStructureLink;
	}

	/**
	 * Returns the d d m structure link where classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classPK the class p k
	 * @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink fetchByClassPK(long classPK)
		throws SystemException {
		return fetchByClassPK(classPK, true);
	}

	/**
	 * Returns the d d m structure link where classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching d d m structure link, or <code>null</code> if a matching d d m structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink fetchByClassPK(long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CLASSPK,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

				List<DDMStructureLink> list = q.list();

				result = list;

				DDMStructureLink ddmStructureLink = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CLASSPK,
						finderArgs, list);
				}
				else {
					ddmStructureLink = list.get(0);

					cacheResult(ddmStructureLink);

					if ((ddmStructureLink.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CLASSPK,
							finderArgs, ddmStructureLink);
					}
				}

				return ddmStructureLink;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CLASSPK,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (DDMStructureLink)result;
			}
		}
	}

	/**
	 * Returns all the d d m structure links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching d d m structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> findByStructureId(long structureId)
		throws SystemException {
		return findByStructureId(structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structure links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @return the range of matching d d m structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> findByStructureId(long structureId,
		int start, int end) throws SystemException {
		return findByStructureId(structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structure links where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> findByStructureId(long structureId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				structureId,
				
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDMStructureLink> list = (List<DDMStructureLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_STRUCTUREID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				list = (List<DDMStructureLink>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_STRUCTUREID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_STRUCTUREID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d d m structure link in the ordered set where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink findByStructureId_First(long structureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureLinkException, SystemException {
		List<DDMStructureLink> list = findByStructureId(structureId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last d d m structure link in the ordered set where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a matching d d m structure link could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink findByStructureId_Last(long structureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureLinkException, SystemException {
		int count = countByStructureId(structureId);

		List<DDMStructureLink> list = findByStructureId(structureId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchStructureLinkException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the d d m structure links before and after the current d d m structure link in the ordered set where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureLinkId the primary key of the current d d m structure link
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink[] findByStructureId_PrevAndNext(
		long structureLinkId, long structureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureLinkException, SystemException {
		DDMStructureLink ddmStructureLink = findByPrimaryKey(structureLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLink[] array = new DDMStructureLinkImpl[3];

			array[0] = getByStructureId_PrevAndNext(session, ddmStructureLink,
					structureId, orderByComparator, true);

			array[1] = ddmStructureLink;

			array[2] = getByStructureId_PrevAndNext(session, ddmStructureLink,
					structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureLink getByStructureId_PrevAndNext(Session session,
		DDMStructureLink ddmStructureLink, long structureId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

		query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(structureId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(ddmStructureLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructureLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structure links that the user has permission to view where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching d d m structure links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> filterFindByStructureId(long structureId)
		throws SystemException {
		return filterFindByStructureId(structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structure links that the user has permission to view where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @return the range of matching d d m structure links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> filterFindByStructureId(long structureId,
		int start, int end) throws SystemException {
		return filterFindByStructureId(structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structure links that the user has permissions to view where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structure links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> filterFindByStructureId(long structureId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByStructureId(structureId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(2);
		}

		query.append(_FILTER_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

		query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

		appendGroupByComparator(query, _FILTER_COLUMN_PK);

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructureLink.class.getName(), _FILTER_COLUMN_PK);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureLinkImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureLinkImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(structureId);

			return (List<DDMStructureLink>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the d d m structure links before and after the current d d m structure link in the ordered set of d d m structure links that the user has permission to view where structureId = &#63;.
	 *
	 * @param structureLinkId the primary key of the current d d m structure link
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure link
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureLinkException if a d d m structure link with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DDMStructureLink[] filterFindByStructureId_PrevAndNext(
		long structureLinkId, long structureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureLinkException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByStructureId_PrevAndNext(structureLinkId, structureId,
				orderByComparator);
		}

		DDMStructureLink ddmStructureLink = findByPrimaryKey(structureLinkId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLink[] array = new DDMStructureLinkImpl[3];

			array[0] = filterGetByStructureId_PrevAndNext(session,
					ddmStructureLink, structureId, orderByComparator, true);

			array[1] = ddmStructureLink;

			array[2] = filterGetByStructureId_PrevAndNext(session,
					ddmStructureLink, structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureLink filterGetByStructureId_PrevAndNext(
		Session session, DDMStructureLink ddmStructureLink, long structureId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_FILTER_SQL_SELECT_DDMSTRUCTURELINK_WHERE);

		query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

		appendGroupByComparator(query, _FILTER_COLUMN_PK);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructureLink.class.getName(), _FILTER_COLUMN_PK);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureLinkImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureLinkImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(structureId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(ddmStructureLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructureLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structure links.
	 *
	 * @return the d d m structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @return the range of d d m structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structure links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure links
	 * @param end the upper bound of the range of d d m structure links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m structure links
	 * @throws SystemException if a system exception occurred
	 */
	public List<DDMStructureLink> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				String.valueOf(start), String.valueOf(end),
				String.valueOf(orderByComparator)
			};

		List<DDMStructureLink> list = (List<DDMStructureLink>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMSTRUCTURELINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMSTRUCTURELINK;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DDMStructureLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DDMStructureLink>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the d d m structure links where classNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByClassNameId(long classNameId) throws SystemException {
		for (DDMStructureLink ddmStructureLink : findByClassNameId(classNameId)) {
			ddmStructureLinkPersistence.remove(ddmStructureLink);
		}
	}

	/**
	 * Removes the d d m structure link where classPK = &#63; from the database.
	 *
	 * @param classPK the class p k
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByClassPK(long classPK)
		throws NoSuchStructureLinkException, SystemException {
		DDMStructureLink ddmStructureLink = findByClassPK(classPK);

		ddmStructureLinkPersistence.remove(ddmStructureLink);
	}

	/**
	 * Removes all the d d m structure links where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByStructureId(long structureId) throws SystemException {
		for (DDMStructureLink ddmStructureLink : findByStructureId(structureId)) {
			ddmStructureLinkPersistence.remove(ddmStructureLink);
		}
	}

	/**
	 * Removes all the d d m structure links from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DDMStructureLink ddmStructureLink : findAll()) {
			ddmStructureLinkPersistence.remove(ddmStructureLink);
		}
	}

	/**
	 * Returns the number of d d m structure links where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching d d m structure links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByClassNameId(long classNameId) throws SystemException {
		Object[] finderArgs = new Object[] { classNameId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m structure links that the user has permission to view where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching d d m structure links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByClassNameId(long classNameId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByClassNameId(classNameId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DDMSTRUCTURELINK_WHERE);

		query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructureLink.class.getName(), _FILTER_COLUMN_PK);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

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

	/**
	 * Returns the number of d d m structure links where classPK = &#63;.
	 *
	 * @param classPK the class p k
	 * @return the number of matching d d m structure links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByClassPK(long classPK) throws SystemException {
		Object[] finderArgs = new Object[] { classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CLASSPK,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_CLASSPK_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CLASSPK,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m structure links where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching d d m structure links
	 * @throws SystemException if a system exception occurred
	 */
	public int countByStructureId(long structureId) throws SystemException {
		Object[] finderArgs = new Object[] { structureId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURELINK_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m structure links that the user has permission to view where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching d d m structure links that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByStructureId(long structureId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByStructureId(structureId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DDMSTRUCTURELINK_WHERE);

		query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructureLink.class.getName(), _FILTER_COLUMN_PK);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(structureId);

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

	/**
	 * Returns the number of d d m structure links.
	 *
	 * @return the number of d d m structure links
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Object[] finderArgs = new Object[0];

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMSTRUCTURELINK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the d d m structure link persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DDMStructureLink>> listenersList = new ArrayList<ModelListener<DDMStructureLink>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DDMStructureLink>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DDMStructureLinkImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = DDMContentPersistence.class)
	protected DDMContentPersistence ddmContentPersistence;
	@BeanReference(type = DDMStorageLinkPersistence.class)
	protected DDMStorageLinkPersistence ddmStorageLinkPersistence;
	@BeanReference(type = DDMStructurePersistence.class)
	protected DDMStructurePersistence ddmStructurePersistence;
	@BeanReference(type = DDMStructureLinkPersistence.class)
	protected DDMStructureLinkPersistence ddmStructureLinkPersistence;
	@BeanReference(type = DDMTemplatePersistence.class)
	protected DDMTemplatePersistence ddmTemplatePersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_DDMSTRUCTURELINK = "SELECT ddmStructureLink FROM DDMStructureLink ddmStructureLink";
	private static final String _SQL_SELECT_DDMSTRUCTURELINK_WHERE = "SELECT ddmStructureLink FROM DDMStructureLink ddmStructureLink WHERE ";
	private static final String _SQL_COUNT_DDMSTRUCTURELINK = "SELECT COUNT(ddmStructureLink) FROM DDMStructureLink ddmStructureLink";
	private static final String _SQL_COUNT_DDMSTRUCTURELINK_WHERE = "SELECT COUNT(ddmStructureLink) FROM DDMStructureLink ddmStructureLink WHERE ";
	private static final String _FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2 = "ddmStructureLink.classNameId = ?";
	private static final String _FINDER_COLUMN_CLASSPK_CLASSPK_2 = "ddmStructureLink.classPK = ?";
	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2 = "ddmStructureLink.structureId = ?";
	private static final String _FILTER_SQL_SELECT_DDMSTRUCTURELINK_WHERE = "SELECT {ddmStructureLink.*} FROM DDMStructureLink ddmStructureLink WHERE ";
	private static final String _FILTER_SQL_COUNT_DDMSTRUCTURELINK_WHERE = "SELECT COUNT(DISTINCT ddmStructureLink.structureLinkId) AS COUNT_VALUE FROM DDMStructureLink ddmStructureLink WHERE ";
	private static final String _FILTER_COLUMN_PK = "ddmStructureLink.structureLinkId";
	private static final String _FILTER_ENTITY_ALIAS = "ddmStructureLink";
	private static final String _FILTER_ENTITY_TABLE = "DDMStructureLink";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmStructureLink.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DDMStructureLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMStructureLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMStructureLink exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DDMStructureLinkPersistenceImpl.class);
	private static DDMStructureLink _nullDDMStructureLink = new DDMStructureLinkImpl() {
			public Object clone() {
				return this;
			}
		};
}