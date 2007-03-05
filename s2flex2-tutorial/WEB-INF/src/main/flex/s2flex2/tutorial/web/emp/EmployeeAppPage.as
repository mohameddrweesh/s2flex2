package s2flex2.tutorial.web.emp {

	import mx.controls.Alert;
	import mx.rpc.AsyncToken;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.events.FaultEvent;
	import flash.events.Event;
	import s2flex2.tutorial.entity.Emp;
	import s2flex2.tutorial.web.AbstractPage;
	import s2flex2.tutorial.web.AppMode;
	import mx.collections.ArrayCollection;

	[Bindable]
	public class EmployeeAppPage extends AbstractPage {

		public var model: Emp;
		
		public var models: ArrayCollection;

		public var appMode: int;

		override public function onCreationComplete(event: Event): void {
			super.onCreationComplete(event);
			setInitEntryMode();
			selectAll();
		}
		
		public function setInitEntryMode(): void {
			appMode = AppMode.NEUTRAL;
			model = null;
		}
		
		public function setNewEntryMode(): void {
			appMode = AppMode.NEW;
		}

		public function setCorEntryMode(): void {
			appMode = AppMode.COR;
		}
		
		public function convertFormData(): void {
			loadFormData(this.model);
		}
		
		public function selectAll():void {
			remoteCall(service.selectAll(), selectAllOnSuccess, selectAllOnFault);
		}
		public function selectAllOnSuccess(e:ResultEvent, token:Object=null):void {
		    models = new ArrayCollection(e.result as Array);
		}
		public function selectAllOnFault(e:FaultEvent, token:Object=null):void {
			Alert.show("selectAll is fault");
		}
		public function update():void {
			remoteCall(service.update(model), updateOnSuccess, updateOnFault);
		}
		public function updateOnSuccess(e:ResultEvent, token:Object=null):void {
		}
		public function updateOnFault(e:FaultEvent, token:Object=null):void {
			Alert.show("update is fault");
		}
		public function dgOnChange(e:Event):void {
			model = document.dg.selectedItem;
		}
		public function doUpdateOnClick(e:Event):void {
			update();
		}
	}
}