package org.seasar.flex2.rpc.remoting.message.data
{
	
[RemoteClass(alias="org.seasar.flex2.rpc.remoting.message.data.Fault")]
	public class Fault
	{
	    public var faultCode:String;
	
	    /**
	     *  A simple description of the error.
	     */
	    public var faultString:String;
	
	    /**
	     *  Detailed description of what caused the error.
	     *  This is typically a stack trace from the remote destination.
	     */
	    public var faultDetail:String;
	
	    /**
	     *  Should a root cause exist for the error, this property contains those details.
	     *  This may be an ErrorMessage, a NetStatusEvent info Object, or an underlying
	     *  Flash error event: ErrorEvent, IOErrorEvent, or SecurityErrorEvent.
	     */
	    public var rootCause:Object;
	    
	    /**
	     * Extended data that the remote destination has chosen to associate
	     * with this error to facilitate custom error processing on the client.
	     */
	    public var extendedData:Object;

	}
}