/*
 * JBoss, Home of Professional Open Source
 *
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.picketlink.idm.jpa.internal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.picketlink.idm.config.JPAIdentityStoreConfiguration;
import org.picketlink.idm.config.JPAIdentityStoreConfiguration.PropertyType;
import org.picketlink.idm.event.AbstractBaseEvent;
import org.picketlink.idm.event.RoleCreatedEvent;
import org.picketlink.idm.event.RoleDeletedEvent;
import org.picketlink.idm.event.RoleUpdatedEvent;
import org.picketlink.idm.model.Grant;
import org.picketlink.idm.model.Role;
import org.picketlink.idm.model.SimpleRole;
import org.picketlink.idm.query.RelationshipQuery;
import org.picketlink.idm.spi.SecurityContext;

/**
 * @author <a href="mailto:psilva@redhat.com">Pedro Silva</a>
 *
 */
public class RoleHandler extends IdentityTypeHandler<Role> {

    public RoleHandler() {
        getSortParametersMapping().put(Role.NAME, PropertyType.IDENTITY_NAME);
    }

    @Override
    protected void doPopulateIdentityInstance(SecurityContext context, Object toIdentity, Role fromRole, JPAIdentityStore store) {
        JPAIdentityStoreConfiguration jpaConfig = store.getConfig();

        jpaConfig.setModelPropertyValue(toIdentity, PropertyType.IDENTITY_PARTITION,
                store.lookupAndCreatePartitionObject(context, context.getPartition()), true);
        jpaConfig.setModelPropertyValue(toIdentity, PropertyType.IDENTITY_NAME, fromRole.getName(), true);
    }

    @Override
    protected AbstractBaseEvent raiseCreatedEvent(Role fromIdentityType) {
        return new RoleCreatedEvent(fromIdentityType);
    }

    @Override
    protected AbstractBaseEvent raiseUpdatedEvent(Role fromIdentityType) {
        return new RoleUpdatedEvent(fromIdentityType);
    }

    @Override
    protected AbstractBaseEvent raiseDeletedEvent(Role fromIdentityType) {
        return new RoleDeletedEvent(fromIdentityType);
    }

    @Override
    protected Role doCreateIdentityType(SecurityContext context, Object identity, JPAIdentityStore store) {
        String name = store.getConfig().getModelPropertyValue(String.class, identity, PropertyType.IDENTITY_NAME);

        SimpleRole role = new SimpleRole(name);

        return role;
    }

    @Override
    public List<Predicate> getPredicate(SecurityContext context, JPACriteriaQueryBuilder criteria, JPAIdentityStore store) {
        List<Predicate> predicates = super.getPredicate(context, criteria, store);
        JPAIdentityStoreConfiguration jpaConfig = store.getConfig();

        Object[] parameterValues = criteria.getIdentityQuery().getParameter(Role.NAME);

        if (parameterValues != null) {
            predicates.add(criteria.getBuilder().equal(
                    criteria.getRoot().get(jpaConfig.getModelProperty(PropertyType.IDENTITY_NAME).getName()),
                    parameterValues[0]));
        }

        parameterValues = criteria.getIdentityQuery().getParameter(Role.ROLE_OF);

        if (parameterValues != null) {
            for (Object object : parameterValues) {
                RelationshipQuery<Grant> query = context.getIdentityManager().createRelationshipQuery(Grant.class);

                query.setParameter(Grant.ASSIGNEE, object);

                List<Grant> resultList = query.getResultList();

                if (!resultList.isEmpty()) {
                    List<String> relIds = new ArrayList<String>();

                    for (Grant grant : resultList) {
                        relIds.add(grant.getRole().getId());
                    }

                    predicates.add(criteria.getRoot().get(jpaConfig.getModelProperty(PropertyType.IDENTITY_ID).getName()).in(relIds));
                } else {
                    predicates.add(criteria.getBuilder().equal(
                            criteria.getRoot().get(jpaConfig.getModelProperty(PropertyType.IDENTITY_ID).getName()), "-1"));
                }
            }
        }

        return predicates;
    }
}
