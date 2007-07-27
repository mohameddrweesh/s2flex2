package org.seasar.flex2.utils
{
    import mx.core.Application;
    import mx.core.ApplicationGlobals;
    
	public final class ApplicationUtil {
	    
        /**
         * 指定されたパラメータに対する値を取得する。
         * 
         * @param application アプリケーション
         * @param prameterName パラメータ名
         * @return parameterValue パラメータの値 
         */
        public static function getParameterValue( parameterName:String, application:Application=null):String{
            var curretApplication:Application = application;
            if( curretApplication == null ){
                curretApplication = ApplicationGlobals.application as Application;
            }
            
            return curretApplication.parameters[ parameterName ];
        }
    }
}