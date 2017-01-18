package it.unibz.inf.ontop.model.impl;

/*
 * #%L
 * ontop-obdalib-core
 * %%
 * Copyright (C) 2009 - 2014 Free University of Bozen-Bolzano
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import it.unibz.inf.ontop.model.Function;
import it.unibz.inf.ontop.model.OBDAMappingAxiom;
import it.unibz.inf.ontop.model.OBDASQLQuery;
import it.unibz.inf.ontop.model.SourceQuery;
import it.unibz.inf.ontop.utils.IDGenerator;

public class RDBMSMappingAxiom extends AbstractOBDAMappingAxiom
{
	private static final long serialVersionUID = 5793656631843898419L;
	
	private OBDASQLQuery sourceQuery;
	private List<Function> targetQuery;

	@AssistedInject
	protected RDBMSMappingAxiom(@Assisted String id, @Assisted("sourceQuery") SourceQuery sourceQuery,
                                @Assisted("targetQuery") List<Function> targetQuery) {
		super(id);
		setSourceQuery(sourceQuery);
		setTargetQuery(targetQuery);
	}

	@AssistedInject
	private RDBMSMappingAxiom(@Assisted("sourceQuery") SourceQuery sourceQuery,
                              @Assisted("targetQuery") List<Function> targetQuery) {
		this(IDGenerator.getNextUniqueID("MAPID-"), sourceQuery, targetQuery);
	}

	@Override
	public void setSourceQuery(SourceQuery query) {
		if (!(query instanceof OBDASQLQuery)) {
			throw new InvalidParameterException("RDBMSDataSourceMapping must receive a RDBMSSQLQuery as source query");
		}
		this.sourceQuery = (OBDASQLQuery) query;
	}

	@Override
	public void setTargetQuery(List<Function> query) {
		this.targetQuery = query;
	}

	@Override
	public OBDASQLQuery getSourceQuery() {
		return sourceQuery;
	}

	@Override
	public List<Function> getTargetQuery() {
		return targetQuery;
	}

	@Override
	public OBDAMappingAxiom clone() {
		List<Function> newbody = new ArrayList<>(targetQuery.size());
		for (Function f : targetQuery)
			newbody.add((Function)f.clone());

		OBDAMappingAxiom clone = new RDBMSMappingAxiom(this.getId(), sourceQuery.clone(), newbody);
		return clone;
	}
	
	@Override
	public String toString() {
		StringBuffer bf = new StringBuffer();
		bf.append(sourceQuery.toString());
		bf.append(" ==> ");
		bf.append(targetQuery.toString());
		return bf.toString();
	}
}