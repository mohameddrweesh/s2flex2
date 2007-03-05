package s2flex2.tutorial.entity {
	
	[Bindable]
	[RemoteClass(alias="s2flex2.tutorial.entity.Emp")]
	public class Emp {

		public var id: int;
		public var empNo: int;
		public var empName: String;
		public var mgrId: int;
		public var hiredate: Date;
		public var sal: String;
		public var deptId: int;
		public var versionNo: int;
	}
}