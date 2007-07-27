package org.seasar.flex2.utils {
    import mx.core.Application;
    import mx.core.ApplicationGlobals;
    
    public class HttpUtil {
    	
    	private static const SERVER_URL_PATTERN:RegExp = /^http[s]?:\/\/.+?\//;
        
        private static const SWF_DIR_URL_PATTERN:RegExp = /^http[s]?:\/\/.+\//;
        
        public static function getUri( path:String):String{
			const httpIndex:int = path.indexOf("http");
			var url:String = null;
			switch( httpIndex ){
				case 0:
					url = path;
					break;
				case -1:
					url = getUriBySwfUrl( path );
					break;
				default:
				
			}
			
			return url;
        }
        
        private static function getUriBySwfUrl( path:String, application:Application=null):String{
            var curretApplication:Application = application;
            if( curretApplication == null ){
                curretApplication = ApplicationGlobals.application as Application;
            }
            const swfUrl:String = curretApplication.url;
            
            const slashIndex:int = path.indexOf("/");
            var relativePath:String = null;
            var parentUrl:String = null;
            switch( slashIndex ){
            	case 0:
            		relativePath = path.substr(1,path.length);
					var serverUrlMatchResult:Array = swfUrl.match(SERVER_URL_PATTERN);
					parentUrl = serverUrlMatchResult[0];
            		break;
            	default:
         			relativePath = path;
					var swfDirUrlMatchResult:Array = swfUrl.match(SWF_DIR_URL_PATTERN);
					parentUrl = swfDirUrlMatchResult[0];
            		break;
            }
            
            return parentUrl + relativePath;
        }
    }
}