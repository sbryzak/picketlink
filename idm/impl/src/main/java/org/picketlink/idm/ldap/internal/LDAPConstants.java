/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.picketlink.idm.ldap.internal;

/**
 * Define commonly used ldap constants
 *
 * @author anil saldhana
 * @since Aug 31, 2012
 */
public interface LDAPConstants {
    String GIVENNAME = "givenname";
    String CN = "cn";
    String SN = "sn";
    String EMAIL = "mail";
    String MEMBER = "member";
    String MEMBER_OF = "memberOf";
    String OBJECT_CLASS = "objectclass";
    String UID = "uid";
    Object GROUP_OF_NAMES = "groupOfNames";

    String COMMA = ",";
    String EQUAL = "=";
    String SPACE_STRING = " ";
    
    String CUSTOM_ATTRIBUTE_ENABLED = "enabled";
    String CUSTOM_ATTRIBUTE_CREATE_DATE = "createDate";
    String CUSTOM_ATTRIBUTE_EXPIRY_DATE = "expiryDate";

}