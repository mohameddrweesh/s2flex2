package org.seasar2.flex2.ui
{
    import flash.events.Event;
    import flash.events.FocusEvent;
    import flash.events.MouseEvent;
    import flash.events.TextEvent;
    
    import mx.collections.ArrayCollection;
    import mx.containers.Canvas;
    import mx.controls.ComboBox;
    import mx.controls.TextInput;
    
    import org.seasar2.flex2.io.LocalStrage;
    
    public class AutoCompleteTextInputBase extends Canvas {
        
        public var input:TextInput;
        
        public var history:ComboBox;
        
        private var text_:String;
        
        private var historyArray:ArrayCollection;
        
        private var historyStrage:LocalStrage;
        
        private var isTyping:Boolean;
        
        
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
            historyStrage.flush();
        }
        
        private function initHistory():void{
            historyStrage = new LocalStrage("-history_of_" + this.id );
            historyArray = historyStrage.getProperty("history") as ArrayCollection;
            if( historyArray == null ){
                historyArray = new ArrayCollection();
                historyStrage.deleteProperty("history");
                historyStrage.setProperty("history", historyArray);
                historyStrage.flush();
            }
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
            
            historyStrage.flush();
        }
        
        private function recoveryToTextInput( text:String ):void {
            input.text = text;
        }
        
        protected override function initializationComplete():void{
            super.initializationComplete();
            initHistory();
            initEventHandler();
        }
        
        protected override function commitProperties():void{
            history.dataProvider = historyArray;
            input.text = this.text_;
        }
        
        [Inspectable(defaultValue="")]
        public function set text( text:String ):void{
            this.text_ = text;
            if( input != null){
                input.text = text;
            }
        }
        
        public function get text():String{
            return input.text;
        }
    }
}