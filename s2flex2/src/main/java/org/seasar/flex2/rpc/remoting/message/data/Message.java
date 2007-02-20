/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.flex2.rpc.remoting.message.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AMFのメッセージクラスです。<br>
 * <p>
 * Flex(Flash)クライアントから送信されたバイナリフォーマットを読み込み、S2Flex2側でデータを扱うときにこのメッセージクラスを利用します。
 * </p>
 * 
 * @author e1.arkw
 * @author nod
 * 
 */
public class Message {

    /** AMF Body */
    private List bodies;

    /** AMF Header */
    private Map headerMap;

    /** AMF Headerのリスト */
    private List headers;

    /** AMF version */
    private int version;

    /**
     * コンストラクタ。Messageクラスを構築します。<br/> 各プロパティの初期化を行います。versionはAMF3を示す値をセットします。
     * 
     */
    public Message() {
        bodies = new ArrayList(4);
        headers = new ArrayList(8);
        headerMap = new HashMap(8);
        version = 0x03;
    }

    /**
     * MessageにBodyを新規に追加します。
     * 
     * @param body
     *            追加するMessageBody
     */
    public void addBody(final MessageBody body) {
        bodies.add(body);
    }

    /**
     * MessageにHeaderを新規に追加します。
     * 
     * @param header
     *            追加するMessageHeader
     */
    public void addHeader(final MessageHeader header) {
        headers.add(header);
        headerMap.put(header.getName(), header.getValue());
    }

    /**
     * 指定されたindexに対するbodyを取得します。
     * 
     * @param index
     * @return 指定されたインデックスのMessageBody
     */
    public MessageBody getBody(final int index) {
        return (MessageBody) bodies.get(index);
    }

    /**
     * メッセージに登録されているbodyの数を返します。
     * 
     * @return bodyのサイズ
     */
    public int getBodySize() {
        return bodies.size();
    }

    /**
     * 指定されたindexに対するHeaderを取得します。
     * 
     * @param index
     * @return 指定されたインデックスのMeassageHeader
     */
    public MessageHeader getHeader(final int index) {
        return (MessageHeader) headers.get(index);
    }

    /**
     * 指定されたヘッダ名に対する文字列表現を返す。
     * 
     * @param headerName
     *            ヘッダの名称
     * @return 指定されたヘッダの文字列表現
     */
    public String getHeader(final String headerName) {
        return (String) getHeaderValue(headerName);
    }

    /**
     * 指定されたヘッダ名に対する値を取得します。
     * 
     * @param headerName
     *            ヘッダ名
     * @return Messageに指定のヘッダがあるときにはその値。それ以外のときはnull
     */
    public Object getHeaderValue(final String headerName) {
        return headerMap.get(headerName);
    }

    /**
     * メッセージに登録されているヘッダの数を返します。
     * 
     * @return headerのサイズ
     */
    public int getHeaderSize() {
        return headers.size();
    }

    /**
     * AMFのバージョンを返します。
     * 
     * @return versionの値. 現時点では0,または3
     */
    public int getVersion() {
        return version;
    }

    /**
     * AMFのバージョンをセットします。
     * 
     * @param version
     *            versionの新規の値
     */
    public void setVersion(final int version) {
        this.version = version;
    }
}