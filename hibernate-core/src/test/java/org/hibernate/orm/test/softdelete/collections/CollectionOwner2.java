/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html.
 */
package org.hibernate.orm.test.softdelete.collections;

import java.util.Set;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.orm.test.softdelete.ReverseYesNoConverter;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

/**
 * @author Steve Ebersole
 */
@Entity
@Table(name = "coll_owner2")
public class CollectionOwner2 {
	@Id
	private Integer id;
	private String name;

	@ElementCollection
	@CollectionTable(name="batch_loadables", joinColumns = @JoinColumn(name="owner_fk"))
	@BatchSize(size = 5)
	@SoftDelete(columnName = "active", converter = ReverseYesNoConverter.class)
	private Set<String> batchLoadable;

	@ElementCollection
	@CollectionTable(name="subselect_loadables", joinColumns = @JoinColumn(name="owner_fk"))
	@Fetch(FetchMode.SUBSELECT)
	@SoftDelete(columnName = "active", converter = ReverseYesNoConverter.class)
	private Set<String> subSelectLoadable;

	public CollectionOwner2() {
	}

	public CollectionOwner2(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getBatchLoadable() {
		return batchLoadable;
	}

	public void setBatchLoadable(Set<String> batchLoadable) {
		this.batchLoadable = batchLoadable;
	}

	public Set<String> getSubSelectLoadable() {
		return subSelectLoadable;
	}

	public void setSubSelectLoadable(Set<String> subSelectLoadable) {
		this.subSelectLoadable = subSelectLoadable;
	}
}
