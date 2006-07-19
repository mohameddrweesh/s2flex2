package org.seasar2.flex2.rpc.remoting.service.browser.ui
{
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