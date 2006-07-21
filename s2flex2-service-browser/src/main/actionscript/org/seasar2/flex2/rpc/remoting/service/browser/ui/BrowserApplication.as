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
package org.seasar2.flex2.rpc.remoting.service.browser.ui {
    
    import mx.core.Application;
    import mx.controls.TextInput;
    import mx.controls.Button;
    import mx.events.FlexEvent;
    import mx.containers.Panel;
    import org.seasar2.flex2.rpc.remoting.service.browser.mxml.Browser;
    import flash.events.TextEvent;

    public class BrowserApplication extends Application {
        
        public var gatewayUri:TextInput;
        
        public var browseBtn:Button;
        
        public var browserPanel:Panel;
        
        public var browser:BrowserBase;
        
        public function BrowserApplication(){
            super();
        }
        
        protected override function initializationComplete():void{
            initEventHandler();
        }
        
        private function initEventHandler():void{
            browseBtn.addEventListener(FlexEvent.BUTTON_DOWN,browseClickHandler);
            gatewayUri.addEventListener(TextEvent.TEXT_INPUT,gatewayInputHandler);
        }
        
        private function browseClickHandler( e:FlexEvent ):void{
            if( gatewayUri.text.length > 0 ){
                browser.gateway = gatewayUri.text;
                browser.browse();
                browserPanel.enabled = true;
            }
        }

        private function gatewayInputHandler( e:TextEvent ):void{
            if( browserPanel.enabled){
                browserPanel.enabled = false;
                browser.clear();
            }
        }
    }
}