package examples.flex2.cooldeploy.creator;

import org.seasar.framework.container.autoregister.ComponentCustomizer;
import org.seasar.framework.container.cooldeploy.creator.SinglePackageCoolCreator;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

public class ResolverCoolCreator extends SinglePackageCoolCreator {
    
    public ResolverCoolCreator(NamingConvention namingConvention) {
        super(namingConvention);
        setMiddlePackageName("naming");
        setNameSuffix("Resolver");
        setInstanceDef(InstanceDefFactory.SINGLETON);
    }

    public ComponentCustomizer setResolverCustomizer() {
        return getCustomizer();
    }

    public void setResolverCustomizer(ComponentCustomizer resolverCustomizer) {
        setCustomizer(resolverCustomizer);
    }
}
