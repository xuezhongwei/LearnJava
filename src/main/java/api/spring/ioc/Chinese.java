package api.spring.ioc;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Chinese implements Person, BeanNameAware, BeanFactoryAware, ApplicationContextAware,
	InitializingBean, DisposableBean {
	private Axe axe;
	
	private List<String> list;
	
	private Map<String, String> map;
	
	private Set<String> set;
	
	@Override
	public void useAxe() {
		System.out.println(axe.chop());
	}
	
	public void setAxe(Axe axe) {
		this.axe = axe;
		System.out.println("正在调用setter方法");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("execute destory method ");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("execute afterPropertiesSet method ");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("execute setApplicationContext method ");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("execute setBeanFactory method ");
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("execute setBeanName method ");
	}
	
	public void myInit() {
		System.out.println("execute myInit method ");
	}
	
	public void myDestroy() {
		System.out.println("execute myDestory method ");
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}

	@Override
	public String toString() {
		return "Chinese [axe=" + axe + ", list=" + list + ", map=" + map + ", set=" + set + "]";
	}
	
}
