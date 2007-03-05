package s2flex2.tutorial.web.emp;

import s2flex2.tutorial.entity.Emp;

public interface EmployeeAppService {

	Emp[] selectAll();

	void update(Emp emp);
}
