package org.seasar2.flex2.io
{
    import flash.net.SharedObject;
    import flash.events.NetStatusEvent;
    import flash.net.SharedObjectFlushStatus;
    
    public class LocalStrage {
        
        private var so:SharedObject;
        
        public function LocalStrage( name:String ){
            so = SharedObject.getLocal("local_strage_" + name, null, false);
        }
        
        
        public function setProperty( name:String, value:Object ):void{
            so.setProperty(name,value);
        }
        
        public function getProperty( name:String ):Object{
            return so.data[ name ];
        }
        
        public function deleteProperty( name:String ):void{
            so.data[ name ] = null;
            delete so.data[ name ];
        }

        public function flush():void{
            var flushStatus:String = null;
            try {
                flushStatus = so.flush(1000);
            } catch (error:Error) {
                trace("Error...Could not write SharedObject to disk");
            } finally {
                flushStatusCheck( flushStatus );
            }
        }
        
        private function flushStatusCheck( flushStatus:String ):void{
            if (flushStatus != null) {
                switch (flushStatus) {
                    case SharedObjectFlushStatus.PENDING:
                        trace("Requesting permission to save object...");
                        so.addEventListener(NetStatusEvent.NET_STATUS, flushStatusHandler);
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

            so.removeEventListener(NetStatusEvent.NET_STATUS, flushStatusHandler);
        }
        
    }
}