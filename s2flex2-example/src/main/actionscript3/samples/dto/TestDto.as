package samples.dto {
	[RemoteClass(alias="samples.dto.TestDto")]
	public class TestDto {
		private var _xmlData:XML;

 		public function get xmlData():XML{ return _xmlData; }
 		public function set xmlData(newValue:XML):void{ this._xmlData=newValue; } 	 	

        }
}