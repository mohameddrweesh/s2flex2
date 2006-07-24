package org.seasar2.flex2.ui
{
    import flash.events.Event;
    import flash.events.FocusEvent;
    import flash.events.NetStatusEvent;
    import flash.events.TextEvent;
    import flash.net.SharedObject;
    import flash.net.SharedObjectFlushStatus;
    import flash.utils.escapeMultiByte;
    
    import mx.collections.ArrayCollection;
    import mx.containers.Canvas;
    import mx.controls.ComboBox;
    import mx.controls.TextInput;
    import mx.core.UIComponent;
    import mx.utils.ArrayUtil;
    import mx.events.FlexEvent;
    import flash.events.MouseEvent;
    import mx.events.DropdownEvent;
    
    public class AutoCompleteTextInputBase extends Canvas {
        
        public var input:TextInput;
        
        public var history:ComboBox;
        
        private var historyArray:ArrayCollection;
        
        private var historySo:SharedObject;
        
        private var isTyping:Boolean;
        
        private var text_:String;
        
        public function AutoCompleteTextInputBase(){
            super();
        }
        
        public function save():void{
            if( isTyping ){
                saveToHistory(input.text);
                isTyping = false;
            }
        }
        
        public function clearHistory():void {
            this.historyArray.removeAll();
            flushSharedObject();
        }
        
        [Inspectable(defaultValue="")]
        public function get text():String{
            return this.input.text;
        }
        
        private function initHistory():void{
            historySo = SharedObject.getLocal("input_history_of_" + this.id, null, false);
            if( historySo.data["history"] == null ){
                historySo.setProperty("history", new ArrayCollection());
                flushSharedObject();
            }
            historyArray = historySo.data["history"] as ArrayCollection;
        }
        
        private function initEventHandler():void{
            input.addEventListener(TextEvent.TEXT_INPUT,textInputHandler);
            history.addEventListener(Event.CHANGE,hitoryChangeHandler);
        }
        
        private function textInputHandler( event:TextEvent ):void{
            if( !isTyping ){
                isTyping = true;
            }
        }
        
        private function hitoryChangeHandler( event:Event ):void{
            recoveryToTextInput( history.selectedItem["label"] );
        }
        
        private function saveToHistory( text:String ):void {
            historyArray.addItem( {label:text, data:1});
            if( historyArray.length > 5 ){
                historyArray.removeItemAt(0);
            }
            
            flushSharedObject();
        }
        
        private function recoveryToTextInput( text:String ):void {
            input.text = text;
        }

        public function set text( text:String):void{
            this.text_ = text;
        }
        
        private function flushSharedObject():void{
            var flushStatus:String = null;
            try {
                flushStatus = historySo.flush(10000);
            } catch (error:Error) {
                trace("Error...Could not write SharedObject to disk");
            }
            if (flushStatus != null) {
                switch (flushStatus) {
                    case SharedObjectFlushStatus.PENDING:
                        trace("Requesting permission to save object...");
                        historySo.addEventListener(NetStatusEvent.NET_STATUS, flushStatusHandler);
                        break;
                    case SharedObjectFlushStatus.FLUSHED:
                        trace("Value flushed to disk.");
                        break;
                }
            }
        }

        private function flushStatusHandler(event:NetStatusEvent):void {
            trace("User closed permission dialog...");
            switch (event.info.code) {
                case "SharedObject.Flush.Success":
                    trace("User granted permission -- value saved.");
                    break;
                case "SharedObject.Flush.Failed":
                    trace("User denied permission -- value not saved.");
                    break;
            }

            historySo.removeEventListener(NetStatusEvent.NET_STATUS, flushStatusHandler);
        }
        
        
        protected override function initializationComplete():void{
            super.initializationComplete();
            initHistory();
            initEventHandler();
        }
        
        protected override function commitProperties():void{
            input.text = this.text_;
            history.dataProvider = historyArray;
        }    
    }
}