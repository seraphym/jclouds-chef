/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
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
package org.jclouds.chef;

import static org.jclouds.Constants.PROPERTY_SESSION_INTERVAL;
import static org.jclouds.chef.reference.ChefConstants.CHEF_BOOTSTRAP_DATABAG;

import java.net.URI;
import java.util.Properties;

import org.jclouds.apis.ApiMetadata;
import org.jclouds.chef.config.ChefRestClientModule;
import org.jclouds.ohai.config.JMXOhaiModule;
import org.jclouds.rest.RestContext;
import org.jclouds.rest.internal.BaseRestApiMetadata;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;
import com.google.inject.Module;

/**
 * Implementation of {@link ApiMetadata} for OpsCode's Chef api.
 * 
 * @author Adrian Cole
 */
public class ChefApiMetadata extends BaseRestApiMetadata {

   /** The serialVersionUID */
   private static final long serialVersionUID = 3450830053589179249L;

   public static final TypeToken<RestContext<ChefClient, ChefAsyncClient>> CONTEXT_TOKEN = new TypeToken<RestContext<ChefClient, ChefAsyncClient>>() {
      private static final long serialVersionUID = -5070937833892503232L;
   };

   @Override
   public Builder toBuilder() {
      return new Builder(getApi(), getAsyncApi()).fromApiMetadata(this);
   }

   public ChefApiMetadata() {
      this(new Builder(ChefClient.class, ChefAsyncClient.class));
   }

   protected ChefApiMetadata(Builder builder) {
      super(Builder.class.cast(builder));
   }

   public static Properties defaultProperties() {
      Properties properties = BaseRestApiMetadata.defaultProperties();
      properties.setProperty(PROPERTY_SESSION_INTERVAL, "1");
      properties.setProperty(CHEF_BOOTSTRAP_DATABAG, "bootstrap");
      return properties;
   }

   public static class Builder extends BaseRestApiMetadata.Builder {

      protected Builder(Class<?> client, Class<?> asyncClient) {
         super(client, asyncClient);
         id("chef")
         .name("OpsCode Chef Api")
         .identityName("User")
         .credentialName("Certificate")
         .version(ChefAsyncClient.VERSION)
         .documentation(URI.create("http://wiki.opscode.com/display/chef/Server+API"))
         .defaultEndpoint("http://localhost:4000")
         .defaultProperties(ChefApiMetadata.defaultProperties())
         .context(TypeToken.of(ChefContext.class))
         .defaultModules(ImmutableSet.<Class<? extends Module>>of(ChefRestClientModule.class, JMXOhaiModule.class));
      }

      @Override
      public ChefApiMetadata build() {
         return new ChefApiMetadata(this);
      }
      
      @Override
      public Builder fromApiMetadata(ApiMetadata in) {
         super.fromApiMetadata(in);
         return this;
      }
   }

}