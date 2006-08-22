/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package examples.flex2.hotdeploy.creator;

import org.seasar.framework.container.autoregister.ComponentCustomizer;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.hotdeploy.creator.SinglePackageOndemandCreator;
import org.seasar.framework.convention.NamingConvention;

public class ConfigOndemandCreator extends SinglePackageOndemandCreator {

    public ConfigOndemandCreator(NamingConvention namingConvention) {
        super(namingConvention);
        setMiddlePackageName("config");
        setNameSuffix("Config");
        setInstanceDef(InstanceDefFactory.PROTOTYPE);
    }

    public ComponentCustomizer getConfigCustomizer() {
        return getCustomizer();
    }

    public void setConfigCustomizer(ComponentCustomizer customizer) {
        setCustomizer(customizer);
    }
}
