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

package com.liferay.commerce.order.rule.service.persistence;

import com.liferay.commerce.order.rule.exception.NoSuchOrderRuleEntryException;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce order rule entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceOrderRuleEntryUtil
 * @generated
 */
@ProviderType
public interface CommerceOrderRuleEntryPersistence
	extends BasePersistence<CommerceOrderRuleEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrderRuleEntryUtil} to access the commerce order rule entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce order rule entries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByC_A(
		long companyId, boolean active);

	/**
	 * Returns a range of all the commerce order rule entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByC_A(
		long companyId, boolean active, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order rule entries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order rule entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry findByC_A_First(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns the first commerce order rule entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry fetchByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the last commerce order rule entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry findByC_A_Last(
			long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns the last commerce order rule entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry fetchByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public CommerceOrderRuleEntry[] findByC_A_PrevAndNext(
			long commerceOrderRuleEntryId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns all the commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByC_A(
		long companyId, boolean active);

	/**
	 * Returns a range of all the commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByC_A(
		long companyId, boolean active, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entries that the user has permissions to view where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set of commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public CommerceOrderRuleEntry[] filterFindByC_A_PrevAndNext(
			long commerceOrderRuleEntryId, long companyId, boolean active,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Removes all the commerce order rule entries where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	public void removeByC_A(long companyId, boolean active);

	/**
	 * Returns the number of commerce order rule entries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce order rule entries
	 */
	public int countByC_A(long companyId, boolean active);

	/**
	 * Returns the number of commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce order rule entries that the user has permission to view
	 */
	public int filterCountByC_A(long companyId, boolean active);

	/**
	 * Returns all the commerce order rule entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByC_LikeType(
		long companyId, String type);

	/**
	 * Returns a range of all the commerce order rule entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByC_LikeType(
		long companyId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByC_LikeType(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order rule entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByC_LikeType(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order rule entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry findByC_LikeType_First(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns the first commerce order rule entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry fetchByC_LikeType_First(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the last commerce order rule entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry findByC_LikeType_Last(
			long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns the last commerce order rule entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry fetchByC_LikeType_Last(
		long companyId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public CommerceOrderRuleEntry[] findByC_LikeType_PrevAndNext(
			long commerceOrderRuleEntryId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns all the commerce order rule entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByC_LikeType(
		long companyId, String type);

	/**
	 * Returns a range of all the commerce order rule entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByC_LikeType(
		long companyId, String type, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entries that the user has permissions to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByC_LikeType(
		long companyId, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set of commerce order rule entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public CommerceOrderRuleEntry[] filterFindByC_LikeType_PrevAndNext(
			long commerceOrderRuleEntryId, long companyId, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Removes all the commerce order rule entries where companyId = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public void removeByC_LikeType(long companyId, String type);

	/**
	 * Returns the number of commerce order rule entries where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce order rule entries
	 */
	public int countByC_LikeType(long companyId, String type);

	/**
	 * Returns the number of commerce order rule entries that the user has permission to view where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce order rule entries that the user has permission to view
	 */
	public int filterCountByC_LikeType(long companyId, String type);

	/**
	 * Returns all the commerce order rule entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByLtD_S(
		Date displayDate, int status);

	/**
	 * Returns a range of all the commerce order rule entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order rule entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order rule entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry findByLtD_S_First(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns the first commerce order rule entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry fetchByLtD_S_First(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the last commerce order rule entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry findByLtD_S_Last(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns the last commerce order rule entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry fetchByLtD_S_Last(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public CommerceOrderRuleEntry[] findByLtD_S_PrevAndNext(
			long commerceOrderRuleEntryId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns all the commerce order rule entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByLtD_S(
		Date displayDate, int status);

	/**
	 * Returns a range of all the commerce order rule entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entries that the user has permissions to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set of commerce order rule entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public CommerceOrderRuleEntry[] filterFindByLtD_S_PrevAndNext(
			long commerceOrderRuleEntryId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Removes all the commerce order rule entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public void removeByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of commerce order rule entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce order rule entries
	 */
	public int countByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of commerce order rule entries that the user has permission to view where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce order rule entries that the user has permission to view
	 */
	public int filterCountByLtD_S(Date displayDate, int status);

	/**
	 * Returns all the commerce order rule entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByLtE_S(
		Date expirationDate, int status);

	/**
	 * Returns a range of all the commerce order rule entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order rule entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order rule entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry findByLtE_S_First(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns the first commerce order rule entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry fetchByLtE_S_First(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the last commerce order rule entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry findByLtE_S_Last(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns the last commerce order rule entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry fetchByLtE_S_Last(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public CommerceOrderRuleEntry[] findByLtE_S_PrevAndNext(
			long commerceOrderRuleEntryId, Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns all the commerce order rule entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByLtE_S(
		Date expirationDate, int status);

	/**
	 * Returns a range of all the commerce order rule entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entries that the user has permissions to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set of commerce order rule entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public CommerceOrderRuleEntry[] filterFindByLtE_S_PrevAndNext(
			long commerceOrderRuleEntryId, Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Removes all the commerce order rule entries where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	public void removeByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the number of commerce order rule entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce order rule entries
	 */
	public int countByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the number of commerce order rule entries that the user has permission to view where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce order rule entries that the user has permission to view
	 */
	public int filterCountByLtE_S(Date expirationDate, int status);

	/**
	 * Returns all the commerce order rule entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByC_A_LikeType(
		long companyId, boolean active, String type);

	/**
	 * Returns a range of all the commerce order rule entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order rule entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce order rule entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry findByC_A_LikeType_First(
			long companyId, boolean active, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns the first commerce order rule entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry fetchByC_A_LikeType_First(
		long companyId, boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the last commerce order rule entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry findByC_A_LikeType_Last(
			long companyId, boolean active, String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns the last commerce order rule entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry fetchByC_A_LikeType_Last(
		long companyId, boolean active, String type,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public CommerceOrderRuleEntry[] findByC_A_LikeType_PrevAndNext(
			long commerceOrderRuleEntryId, long companyId, boolean active,
			String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns all the commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type);

	/**
	 * Returns a range of all the commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entries that the user has permissions to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entries that the user has permission to view
	 */
	public java.util.List<CommerceOrderRuleEntry> filterFindByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns the commerce order rule entries before and after the current commerce order rule entry in the ordered set of commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the current commerce order rule entry
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public CommerceOrderRuleEntry[] filterFindByC_A_LikeType_PrevAndNext(
			long commerceOrderRuleEntryId, long companyId, boolean active,
			String type,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceOrderRuleEntry> orderByComparator)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Removes all the commerce order rule entries where companyId = &#63; and active = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 */
	public void removeByC_A_LikeType(
		long companyId, boolean active, String type);

	/**
	 * Returns the number of commerce order rule entries where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching commerce order rule entries
	 */
	public int countByC_A_LikeType(long companyId, boolean active, String type);

	/**
	 * Returns the number of commerce order rule entries that the user has permission to view where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching commerce order rule entries that the user has permission to view
	 */
	public int filterCountByC_A_LikeType(
		long companyId, boolean active, String type);

	/**
	 * Returns the commerce order rule entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchOrderRuleEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns the commerce order rule entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the commerce order rule entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order rule entry, or <code>null</code> if a matching commerce order rule entry could not be found
	 */
	public CommerceOrderRuleEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the commerce order rule entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce order rule entry that was removed
	 */
	public CommerceOrderRuleEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns the number of commerce order rule entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce order rule entries
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the commerce order rule entry in the entity cache if it is enabled.
	 *
	 * @param commerceOrderRuleEntry the commerce order rule entry
	 */
	public void cacheResult(CommerceOrderRuleEntry commerceOrderRuleEntry);

	/**
	 * Caches the commerce order rule entries in the entity cache if it is enabled.
	 *
	 * @param commerceOrderRuleEntries the commerce order rule entries
	 */
	public void cacheResult(
		java.util.List<CommerceOrderRuleEntry> commerceOrderRuleEntries);

	/**
	 * Creates a new commerce order rule entry with the primary key. Does not add the commerce order rule entry to the database.
	 *
	 * @param commerceOrderRuleEntryId the primary key for the new commerce order rule entry
	 * @return the new commerce order rule entry
	 */
	public CommerceOrderRuleEntry create(long commerceOrderRuleEntryId);

	/**
	 * Removes the commerce order rule entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry that was removed
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public CommerceOrderRuleEntry remove(long commerceOrderRuleEntryId)
		throws NoSuchOrderRuleEntryException;

	public CommerceOrderRuleEntry updateImpl(
		CommerceOrderRuleEntry commerceOrderRuleEntry);

	/**
	 * Returns the commerce order rule entry with the primary key or throws a <code>NoSuchOrderRuleEntryException</code> if it could not be found.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry
	 * @throws NoSuchOrderRuleEntryException if a commerce order rule entry with the primary key could not be found
	 */
	public CommerceOrderRuleEntry findByPrimaryKey(
			long commerceOrderRuleEntryId)
		throws NoSuchOrderRuleEntryException;

	/**
	 * Returns the commerce order rule entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderRuleEntryId the primary key of the commerce order rule entry
	 * @return the commerce order rule entry, or <code>null</code> if a commerce order rule entry with the primary key could not be found
	 */
	public CommerceOrderRuleEntry fetchByPrimaryKey(
		long commerceOrderRuleEntryId);

	/**
	 * Returns all the commerce order rule entries.
	 *
	 * @return the commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findAll();

	/**
	 * Returns a range of all the commerce order rule entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @return the range of commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce order rule entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce order rule entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entries
	 * @param end the upper bound of the range of commerce order rule entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order rule entries
	 */
	public java.util.List<CommerceOrderRuleEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceOrderRuleEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce order rule entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce order rule entries.
	 *
	 * @return the number of commerce order rule entries
	 */
	public int countAll();

}