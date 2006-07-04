package org.seasar.flex2.rpc.remoting.message.io;

import java.io.IOException;

import org.seasar.flex2.core.format.amf3.io.DataInput;
import org.seasar.flex2.core.format.amf3.io.DataOutput;
import org.seasar.flex2.core.format.amf3.io.Externalizable;

public class TestExternalizeObject implements Externalizable {

    private MyBean myBean;

    public TestExternalizeObject() {
    }

    public void readExternal(DataInput input) {
        try {
            myBean = (MyBean) input.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeExternal(DataOutput output) {
        try {
            output.writeObject(myBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MyBean getMyBean() {
        return myBean;
    }

    public void setMyBean(MyBean myBean) {
        this.myBean = myBean;
    }
}
