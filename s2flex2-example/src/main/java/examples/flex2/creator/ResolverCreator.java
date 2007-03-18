package examples.flex2.creator;

import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.creator.ComponentCreatorImpl;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

/**
 * Resolver用のCreatorクラスです。<br />
 * クラス名がResolverで終わるクラスをcomponentとして登録します。
 * @author nod
 *
 */
public class ResolverCreator extends ComponentCreatorImpl{

    /**
     * Resolver用のクリエータを返します。
     * 
     * @param namingConvention
     */
    public ResolverCreator(NamingConvention namingConvention) {
        super(namingConvention);
        setNameSuffix("Resolver");
        setInstanceDef(InstanceDefFactory.PROTOTYPE);
    }

    /**
     * Resolver用のカスタマイザを返します。
     * 
     * @return 　Resolver用のカスタマイザ
     */
    public ComponentCustomizer getResolverCustomizer() {
        return getCustomizer();
    }

    /**
     * Resolver用のカスタマイザを設定します。
     * 
     * @param customizer ComponentCustomizer
     */
    public void setResolverCustomizer(ComponentCustomizer customizer) {
        setCustomizer(customizer);
    }
}