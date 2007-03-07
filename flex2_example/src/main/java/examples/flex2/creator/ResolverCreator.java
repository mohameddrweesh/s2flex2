package examples.flex2.creator;

import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.creator.ComponentCreatorImpl;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

/**
 * Resolver�p��Creator�N���X�ł��B<br />
 * �N���X����Resolver�ŏI���N���X��component�Ƃ��ēo�^���܂��B
 * @author nod
 *
 */
public class ResolverCreator extends ComponentCreatorImpl{

    /**
     * Resolver�p�̃N���G�[�^��Ԃ��܂��B
     * 
     * @param namingConvention
     */
    public ResolverCreator(NamingConvention namingConvention) {
        super(namingConvention);
        setNameSuffix("Resolver");
        setInstanceDef(InstanceDefFactory.SINGLETON);
    }

    /**
     * Resolver�p�̃J�X�^�}�C�U��Ԃ��܂��B
     * 
     * @return �@Resolver�p�̃J�X�^�}�C�U
     */
    public ComponentCustomizer getResolverCustomizer() {
        return getCustomizer();
    }

    /**
     * Resolver�p�̃J�X�^�}�C�U��ݒ肵�܂��B
     * 
     * @param customizer ComponentCustomizer
     */
    public void setResolverCustomizer(ComponentCustomizer customizer) {
        setCustomizer(customizer);
    }
}