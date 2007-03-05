package s2flex2.tutorial.web.emp.impl;

import org.seasar.flex2.rpc.remoting.service.annotation.RemotingService;

import s2flex2.tutorial.dao.EmpDao;
import s2flex2.tutorial.entity.Emp;
import s2flex2.tutorial.web.emp.EmployeeAppService;

@RemotingService
public class EmployeeAppServiceImpl implements EmployeeAppService {

	private EmpDao empDao;

	public Emp[] selectAll() {
		return empDao.selectAll();
	}

	public void update(Emp emp) {
		empDao.update(emp);
	}

	/**
	 * @param empDao
	 *            the empDao to set
	 */
	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
	}

}
