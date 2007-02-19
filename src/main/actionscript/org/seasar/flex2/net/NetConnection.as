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
 *
 * @ignore
 */
package org.seasar.flex2.net{
    
    import flash.net.NetConnection;

/**
 * NetConnectionは、flash.net.Connectionクラスに、サーバロジック呼び出し時のcallBack関数を追加したクラスです。
 * CallBack関数には以下のものがあります。
 * <ul>
 * 	<li>AppendToGatewayUrl</li>
 * 	<li> ReplaceGatewayUrl</li>
 * </ul>
 * AMFのヘッダに関する仕様については以下をご参照ください。
 * <ul><li><a href="http://osflash.org/amf/envelopes/remoting/headers">Predefined headers</li>
 * </ul>
 * <!--
 * Flash Player 9のセキュリティに関する仕様については以下をご参照ください。
 * <ul>
 * <li>
 * <a href="http://livedocs.macromedia.com/flex/2_jp/docs/wwhelp/wwhimpl/js/html/wwhelp.htm?href=00001950.html">Flash Player セキュリティ</a>
 * </li>
 * <li>
 * <a href="http://www.adobe.com/devnet/flashplayer/articles/flash_player_9_security.pdf">Flash Player 9 Secirity</a>
 * </li>
 * </ul>
 * -->
 */ 
    public class NetConnection extends flash.net.NetConnection{
        /**
        * @private
        */ 
        private var _originalUrl:String;
        /**
        * @private
        * sessionIdを含む接続URLに加える文字列.<br />
        * リモートサービス呼び出し時のcallBack関数のひとつである&qout;AppendToGatewayUrl&qout;の引数で来た文字列が入る
        */ 
        private var append:String =null;
        
        /**
        * 引数で指定されたサーバに接続します。
        * @param command 接続先URL
        * @param rest 接続時のパラメータ
        */ 
        public override function connect(command:String, ...rest):void{
            _originalUrl = command;
            super.connect(command,rest);
        }
        
        /**
        * 引数で指定されたURLに接続します。既にどこかのサーバに接続しているときには一度切断(close)してから接続します。
        * 
        * @param uri 接続先URI
        */  
        private function reconnect( uri:String ):void{
            if( this.connected ){
                this.close();
            }
            this.connect( uri );
        }
        
        /**
        * パラメータで渡されたsessionIDなどを接続するURLに付与した上で再接続をします。これによってセッションの維持をします。
        * @param append セッションIDを含むURLとして追加する文字列
        */ 
        public function AppendToGatewayUrl(append:String):void{ 
            this.append = append;
            var url:String=this._originalUrl+ append;
            
            reconnect(url);
        }
        
        /**
        * 指定された値をAMFのヘッダとして追加します。
        * @param operation ヘッダに追加する名称
        * @param mustUnderstand サーバに送信された後に必ずこのヘッダを読み取るかどうかの指定。デフォルトはfalse.
        * @param object ヘッダに追加する値
        */ 
        public override function addHeader(operation:String,mustUnderstand:Boolean=false,param:Object=null):void{
            super.addHeader(operation,mustUnderstand,param);
        }
        
       /**
        * 指定されたURLをGatewayのURLとして、再接続します。
        * @param url gatewayのURL
        */  
        public function ReplaceGatewayUrl(url:String):void{
            reconnect(url);
        }
        
        /**
        * 接続先のGatewayURLを返します。
        */ 
        public function get connectedUrl():String{
            if(append!= null){
                return _originalUrl + append;
            }
            return _originalUrl;
        }
    }
}
