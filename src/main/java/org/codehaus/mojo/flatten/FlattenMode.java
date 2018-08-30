package org.codehaus.mojo.flatten;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.model.Model;

/**
 * This enum contains the predefined modes how to deal with {@link FlattenDescriptor additional POM elements} when
 * {@link FlattenMojo generating the flattened POM}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0-beta-2
 */
public enum FlattenMode
{
    /**
     * For projects that want to keep all {@link FlattenDescriptor optional POM elements}.
     *
     * @deprecated confusing name, unstable contract.
     */
    @Deprecated
    minimum,

    /**
     * For Open-Source-Software projects that want to keep all {@link FlattenDescriptor optional POM elements} except
     * for {@link Model#getRepositories() repositories} and {@link Model#getPluginRepositories() pluginRepositories}.
     */
    oss,

    /**
     * Keeps all {@link FlattenDescriptor optional POM elements} that are required for
     * <a href="https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide">OSS
     * Repository-Hosting</a>.
     */
    ossrh,

    /**
     * Like {@link #ossrh} but additionally keeps {@link Model#getDependencyManagement() dependencyManagement} and
     * {@link Model#getProperties() properties}. Especially it will keep the {@link Model#getDependencyManagement()
     * dependencyManagement} <em>as-is</em> without resolving parent influences and import-scoped dependencies. This is
     * useful if your POM represents a <a href=
     * "http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#Importing_Dependencies"
     * >BOM (Bill Of Material)</a> and you do not want to deploy it as is (to remove parent and resolve version
     * variables, etc.).
     */
    bom,

    /**
     * The default mode that removes all {@link FlattenDescriptor optional POM elements} except
     * {@link Model#getRepositories() repositories}.
     */
    defaults,

    /** Removes all {@link FlattenDescriptor optional POM elements}. */
    clean,

    /** Removes all {@link FlattenDescriptor optional POM elements} and dependencies. */
    fatjar,
    
    /** Only flattens the version field in pom. Keeps everything else. */
    resolveVersionOnly,
    
    /** Only resolves variables revision, sha and changelist. Keeps everything else. */
    resolveCiFriendliesOnly;

    /**
     * @return the {@link FlattenDescriptor} defined by this {@link FlattenMode}.
     */
    public FlattenDescriptor getDescriptor()
    {

        FlattenDescriptor descriptor = new FlattenDescriptor();
        switch ( this )
        {
            case minimum:
                descriptor.setPluginRepositories( ElementHandling.expand );
                //$FALL-THROUGH$
            case bom:
                // MOJO-2041
                descriptor.setDependencyManagement( ElementHandling.keep );
                descriptor.setProperties( ElementHandling.expand );
                //$FALL-THROUGH$
            case oss:
                descriptor.setCiManagement( ElementHandling.expand );
                descriptor.setContributors( ElementHandling.expand );
                descriptor.setDistributionManagement( ElementHandling.expand );
                descriptor.setInceptionYear( ElementHandling.expand );
                descriptor.setIssueManagement( ElementHandling.expand );
                descriptor.setMailingLists( ElementHandling.expand );
                descriptor.setOrganization( ElementHandling.expand );
                descriptor.setPrerequisites( ElementHandling.expand );
                //$FALL-THROUGH$
            case ossrh:
                descriptor.setName( ElementHandling.expand );
                descriptor.setDescription( ElementHandling.expand );
                descriptor.setUrl( ElementHandling.expand );
                descriptor.setScm( ElementHandling.expand );
                descriptor.setDevelopers( ElementHandling.expand );
                //$FALL-THROUGH$
            case defaults:
                descriptor.setRepositories( ElementHandling.expand );
                break;
            case fatjar:
                descriptor.setDependencies( ElementHandling.remove );
                break;
            case resolveVersionOnly:
                descriptor.setBuild( ElementHandling.keep );
                descriptor.setCiManagement( ElementHandling.keep );
                descriptor.setContributors( ElementHandling.keep );
                descriptor.setDependencies( ElementHandling.keep );
                descriptor.setDependencyManagement( ElementHandling.keep );
                descriptor.setDescription( ElementHandling.keep );
                descriptor.setDevelopers( ElementHandling.keep );
                descriptor.setDistributionManagement( ElementHandling.keep );
                descriptor.setInceptionYear( ElementHandling.keep );
                descriptor.setIssueManagement( ElementHandling.keep );
                descriptor.setMailingLists( ElementHandling.keep );
                descriptor.setModules( ElementHandling.keep );
                descriptor.setName( ElementHandling.keep );
                descriptor.setOrganization( ElementHandling.keep );
                descriptor.setParent( ElementHandling.keep );
                descriptor.setPluginManagement( ElementHandling.keep );
                descriptor.setPluginRepositories( ElementHandling.keep );
                descriptor.setPrerequisites( ElementHandling.keep );
                descriptor.setProfiles( ElementHandling.keep );
                descriptor.setProperties( ElementHandling.keep );
                descriptor.setReporting( ElementHandling.keep );
                descriptor.setRepositories( ElementHandling.keep );
                descriptor.setScm( ElementHandling.keep );
                descriptor.setUrl( ElementHandling.keep );
                descriptor.setVersion( ElementHandling.expand );
                break;
            case resolveCiFriendliesOnly:
                descriptor.setBuild( ElementHandling.resolve );
                descriptor.setCiManagement( ElementHandling.resolve );
                descriptor.setContributors( ElementHandling.resolve );
                descriptor.setDependencies( ElementHandling.resolve );
                descriptor.setDependencyManagement( ElementHandling.resolve );
                descriptor.setDescription( ElementHandling.resolve );
                descriptor.setDevelopers( ElementHandling.resolve );
                descriptor.setDistributionManagement( ElementHandling.resolve );
                descriptor.setInceptionYear( ElementHandling.resolve );
                descriptor.setIssueManagement( ElementHandling.resolve );
                descriptor.setMailingLists( ElementHandling.resolve );
                descriptor.setModules( ElementHandling.resolve );
                descriptor.setName( ElementHandling.resolve );
                descriptor.setOrganization( ElementHandling.resolve );
                descriptor.setParent( ElementHandling.resolve );
                descriptor.setPluginManagement( ElementHandling.resolve );
                descriptor.setPluginRepositories( ElementHandling.resolve );
                descriptor.setPrerequisites( ElementHandling.resolve );
                descriptor.setProfiles( ElementHandling.resolve );
                descriptor.setProperties( ElementHandling.resolve );
                descriptor.setReporting( ElementHandling.resolve );
                descriptor.setRepositories( ElementHandling.resolve );
                descriptor.setScm( ElementHandling.resolve );
                descriptor.setUrl( ElementHandling.resolve );
                descriptor.setVersion( ElementHandling.resolve );
                break;
            case clean:
                // nothing to do...
                break;

            default:
                // nothing to do...Could not happen.
                break;
        }
        return descriptor;
    }

}
