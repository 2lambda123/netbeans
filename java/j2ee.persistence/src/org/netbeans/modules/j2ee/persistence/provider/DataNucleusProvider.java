/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.j2ee.persistence.provider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.netbeans.modules.j2ee.persistence.dd.common.Persistence;
import static org.netbeans.modules.j2ee.persistence.dd.common.PersistenceUnit.JAKARTA_NAMESPACE;
import static org.netbeans.modules.j2ee.persistence.dd.common.PersistenceUnit.JAVAX_NAMESPACE;
import org.openide.util.NbBundle;

/**
 *
 * @author pblaha
 */

/**
 * This class represents DataNucleus provider.
 *
 * @author pblaha
 */
class DataNucleusProvider extends Provider{

    protected DataNucleusProvider(){
        this(null);
    }
    
    protected DataNucleusProvider(String version){
        super("org.datanucleus.api.jpa.PersistenceProviderImpl", version); //NOI18N
    }

    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(DataNucleusProvider.class, "LBL_DataNucleus") + (getVersion()!=null ? " (JPA "+getVersion()+")" : ""); //NOI18N
    }

    @Override
    public String getJdbcUrl() {
        return Persistence.VERSION_1_0.equals(getVersion()) ? "datanucleus.connectionURL" : super.getJdbcUrl();
    }

    @Override
    public String getJdbcDriver() {
        return Persistence.VERSION_1_0.equals(getVersion()) ? "datanucleus.connectionDriverName" : super.getJdbcDriver();
    }

    @Override
    public String getJdbcUsername() {
        return Persistence.VERSION_1_0.equals(getVersion()) ? "datanucleus.connectionUserName" : super.getJdbcUsername();
    }

    @Override
    public String getJdbcPassword() {
        return Persistence.VERSION_1_0.equals(getVersion()) ? "datanucleus.connectionPassword" : super.getJdbcPassword();
    }
    
    @Override
    public String getAnnotationProcessor() {
        return (getVersion()!=null && !Persistence.VERSION_1_0.equals(getVersion())) ? "org.datanucleus.jpa.query.JPACriteriaProcessor" : super.getAnnotationProcessor();
    }

    @Override
    public String getTableGenerationPropertyName() {
        String result = (getVersion() != null && Float.parseFloat(Persistence.VERSION_2_1) <= (Float.parseFloat(getVersion())))
                        ? super.getTableGenerationPropertyName()
                        : "";
        if (isJakartaNamespace()) {
            result = result.replace(JAVAX_NAMESPACE, JAKARTA_NAMESPACE);
        }
        return result;
    }

    @Override
    public String getTableGenerationDropCreateValue() {
        String result = (getVersion() != null && Float.parseFloat(Persistence.VERSION_2_1) <= (Float.parseFloat(getVersion())))
                        ? super.getTableGenerationDropCreateValue()
                        : "";
        if (isJakartaNamespace()) {
            result = result.replace(JAVAX_NAMESPACE, JAKARTA_NAMESPACE);
        }
        return result;
    }

    @Override
    public String getTableGenerationCreateValue() {
        String result = (getVersion() != null && Float.parseFloat(Persistence.VERSION_2_1) <= (Float.parseFloat(getVersion())))
                        ? super.getTableGenerationCreateValue()
                : "";
        if (isJakartaNamespace()) {
            result = result.replace(JAVAX_NAMESPACE, JAKARTA_NAMESPACE);
        }
        return result;
    }

    @Override
    public Map getUnresolvedVendorSpecificProperties() {
        return Collections.emptyMap();
    }

    @Override
    public Map getDefaultVendorSpecificProperties() {
        Map<String,String> properties = new HashMap<>();
        properties.put("datanucleus.transaction.nontx.read", "true"); //NOI18N
        properties.put("datanucleus.transaction.nontx.write", "true"); //NOI18N
        properties.put("datanucleus.ConnectionURL", "appengine"); //NOI18N
        return properties;
    }

}
