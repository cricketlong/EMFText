/*******************************************************************************
 * Copyright (c) 2006-2012
 * Software Technology Group, Dresden University of Technology
 * DevBoost GmbH, Berlin, Amtsgericht Charlottenburg, HRB 140026
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany;
 *   DevBoost GmbH - Berlin, Germany
 *      - initial API and implementation
 ******************************************************************************/
package org.emftext.language.hedl.example.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "CustomerOrder"
)
/*
 * This class is generated from the entity 'CustomerOrder' defined in the HEDL model.
 * Note: Any change made to this class will be overridden.
 */
public class CustomerOrder {
	
	@GenericGenerator(name="CustomerOrderIdGenerator", strategy="org.hibernate.id.MultipleHiLoPerTableGenerator",
	  parameters = {
	    @Parameter(name="table", value="IdentityGenerator"),
	    @Parameter(name="primary_key_column", value="sequence_name"),
	    @Parameter(name="primary_key_value", value="CustomerOrder"),
	    @Parameter(name="value_column", value="next_hi_value"),
	    @Parameter(name="primary_key_length", value="100"),
	    @Parameter(name="max_lo", value="1000")
	  }
	)
	@Id 
	@GeneratedValue(strategy=GenerationType.TABLE, generator="CustomerOrderIdGenerator")
	private int id;

	@OneToMany(cascade={CascadeType.ALL})
	private List<OrderedItem> items;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="customer", nullable=false)
	private Customer customer;
	
	/**
	 * Default constructor. Only used by Hibernate.
	 */
	public CustomerOrder() {
		super();
	}

	/**
	 * Returns the automatically generated id the identifies this entity object.
	 */
	public int getId() {
		return id;
	}

	/**
	 * The property 'id' is read-only. 
	 * This setter is only provided for Hibernate. 
	 * It must not be used directly.
	 */
	@Deprecated
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the value of property 'items'.
	 */
	public List<OrderedItem> getItems() {
		return items;
	}
	
	/**
	 * Sets the value of property 'items'.
	 */
	public void setItems(List<OrderedItem> newValue) {
		this.items = newValue;
	}
	
	/**
	 * Returns the value of property 'customer'.
	 */
	public Customer getCustomer() {
		return customer;
	}
	
	/**
	 * Sets the value of property 'customer'.
	 */
	public void setCustomer(Customer newValue) {
		this.customer = newValue;
	}
	
}
