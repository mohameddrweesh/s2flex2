package org.seasar.flex2.rpc.amf.data.factory.impl;

import org.seasar.flex2.rpc.amf.data.AmfBody;
import org.seasar.flex2.rpc.amf.data.factory.AmfBodyFactory;
import org.seasar.flex2.rpc.amf.data.impl.AmfBodyImpl;

public class AmfBodyFactoryImpl implements AmfBodyFactory {
    
    private final String responseTarget = "null";

    public AmfBody createBody(String target, String response, Object data){
        AmfBody body;
        if( response != null ){
            body = new AmfBodyImpl(target, response, data);
        } else {
            body = new AmfBodyImpl(target, getResponseTarget(), data);
        }
        return body;
    }
    
    protected String getResponseTarget(){
        return responseTarget;
    }
}
