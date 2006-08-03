package org.seasar.flex2.rpc.remoting.message.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class TestExternalizeObject implements Externalizable {

    private MyBean myBean;

    public TestExternalizeObject() {
    }

    public void readExternal(ObjectInput input) {
        try {
            myBean = (MyBean) input.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeExternal(ObjectOutput output) {
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
