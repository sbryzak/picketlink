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

package org.picketlink.authentication;

import org.picketlink.idm.model.Agent;

/**
 * Abstract base class that Authenticator implementations can extend for convenience. 
 * 
 * @author Shane Bryzak
 *
 */
public abstract class BaseAuthenticator implements Authenticator
{
    private AuthenticationStatus status = AuthenticationStatus.FAILURE;
    private Agent agent;

    @Override
    public AuthenticationStatus getStatus()
    {
        return status;
    }

    protected void setStatus(AuthenticationStatus status)
    {
        this.status = status;
    }

    protected void setAgent(Agent agent)
    {
        this.agent = agent;
    }

    @Override
    public Agent getAgent()
    {
        return agent;
    }

    @Override
    public void postAuthenticate()
    {
        // No-op, override if any post-authentication processing is required.
    }
}
