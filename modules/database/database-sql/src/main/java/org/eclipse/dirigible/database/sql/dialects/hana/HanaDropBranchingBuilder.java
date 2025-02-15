/*
 * Copyright (c) 2022 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.dirigible.database.sql.dialects.hana;

import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.builders.DropBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.sequence.DropSequenceBuilder;
import org.eclipse.dirigible.database.sql.builders.tableType.DropTableTypeBuilder;

public class HanaDropBranchingBuilder extends DropBranchingBuilder {

    /**
     * Instantiates a new hana drop branching builder.
     *
     * @param dialect the dialect
     */
    public HanaDropBranchingBuilder(ISqlDialect dialect) {
        super(dialect);
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.dirigible.database.sql.builders.DropBranchingBuilder#sequence(java.lang.String)
     */
    @Override
    public DropSequenceBuilder sequence(String sequence) {
        return new HanaDropSequenceBuilder(this.getDialect(), sequence);
    }

    @Override
    public DropTableTypeBuilder tableType(String tableType) {
        return new HanaDropTableTypeBuilder(this.getDialect(), tableType);
    }

}
