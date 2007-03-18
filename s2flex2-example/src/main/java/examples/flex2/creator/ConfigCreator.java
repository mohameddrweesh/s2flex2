package examples.flex2.creator;

import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.creator.ComponentCreatorImpl;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

/**
 * Config用のCreatorクラスです。<br />
 * クラス名がConfigで終わるクラスをcomponentとして登録します。
 * @author nod
 *
 */
public class ConfigCreator extends ComponentCreatorImpl{

    /**
     * Config用のクリエータを返します。
     * 
     * @param namingConvention
     */
    public ConfigCreator(NamingConvention namingConvention) {
        super(namingConvention);
        setNameSuffix("Config");
        setInstanceDef(InstanceDefFactory.PROTOTYPE);
        
    }

    /**
     * Config用のカスタマイザを返します。
     * 
     * @return 　Config用のカスタマイザ
     */
    public ComponentCustomizer getConfigCustomizer() {
        return getCustomizer();
    }

    /**
     * Config用のカスタマイザを設定します。
     * 
     * @param customizer ComponentCustomizer
     */
    public void setConfigCustomizer(ComponentCustomizer customizer) {
        setCustomizer(customizer);
    }
}