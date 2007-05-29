package org.seasar.flex2.utils
{
    import mx.core.Application;
    
	public final class ApplicationUtil {
	    
        /**
         * 指定されたパラメータに対する値を取得する。
         * 
         * @param application アプリケーション
         * @param prameterName パラメータ名
         * @return parameterValue パラメータの値 
         */
        public static function getParameterValue( application:Application, parameterName:String ):String{
            return application.parameters[ parameterName ];
        }
    }
}