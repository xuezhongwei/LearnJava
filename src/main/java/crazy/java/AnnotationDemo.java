package crazy.java;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Annotation是所有注解的父接口 
 * 只定义注解是没有用的，需要定义相应的注解处理器
 * 处理注解，一定是通过反射来处理的
 * 
 * AnnotatedElement接口是各种反射接口的父接口，AnnotatedElement中包含有各种处理Annotation的方法
 * 例如Class，Field，Method，Constructor等，所以可以通过统一的方式处理这些元素上的注解
 * （1）获得指定注解
 * 		A getAnnotation(Class<A> annotationClass) 
 * 		A getDeclaredAnnotation(Class<A> annotationClass)
 * 		getAnnotation与getDeclaredAnnotation的区别在于，getDeclaredAnnotation获得是直接修改该程序元素的注解，不包含继承而来的注解
 * （2）获得所有注解
 * 		Annotation[] getAnnotations()
 * 		Annotation[] getDeclaredAnnotations()
 * 		getAnnotations与getDeclaredAnnotations的区别在于，getDeclaredAnnotation获得是直接修改该程序元素的注解，不包含继承而来的注解
 * （3）判断某程序元素上是否有指定类型的注解
 * 		isAnnotationPresent(Class<? entends Annotation> annotationClass)
 * 
 * 
 * 我觉得注解是一种特殊的接口，当我们通过反射获取接口信息时，VM会自动创建注解的实现类
 */

//两个常用的元注解，就是对注解进行注解的注解。定义注解时，一般需要设置@Retention和@Target

//如果需要使用反射获取注解信息，需要设置为Runtime
@Retention(RetentionPolicy.RUNTIME)

//@Target用于表示，自定义的注解可以加在什么元素上边，比如加在类上、方法上、成员变量上等
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD})
@interface MyAnnotation{
	// 以声明接口方法的形式来定义注解成员变量
	public String field1();
	// 通常省去修饰符
	String field2();
	// 设置具有默认值的注解成员变量
	String field3() default "I am field3";
	// value是一个特殊的属性名,当只需要为value属性赋值时，不用以value="xxx"的形式赋值
	String value();
}

/**
* 注解中的属性如果没有默认值，则需要设置默认值
*/
@MyAnnotation(field1="field1 at class", field2="field2 at class", value="value at class")
class TestMyAnnotation {
	@MyAnnotation(field1="field1 at field", field2="field2 at field", value="value at field")
	private String testField;
	
	@MyAnnotation(field1="field1 at method", field2="field2 at method", value="value at method")
	public void testMethod() {
		
	}
	@MyAnnotation(field1="field1 at constructor", field2="field2 at constructor", value="value at constructor")
	public TestMyAnnotation() {
		
	}
}

public class AnnotationDemo {

	public static void main(String[] args) throws ClassNotFoundException {
		Class clazz = Class.forName("crazy.java.TestMyAnnotation");
		Annotation[] clazzAnnotations = clazz.getAnnotations();
		
		// 测试注解加在类上的效果
		if (clazzAnnotations.length != 0) {
			for (Annotation itemAnnotation : clazzAnnotations) {
				if (itemAnnotation instanceof MyAnnotation) {
					// 这里一定是进行强制类型转换的
					MyAnnotation tempAnnotation = (MyAnnotation)itemAnnotation;
					// 这里就以方法调用的方式，获得注解的属性值
					System.out.println(tempAnnotation.field1());
				}
			}
		}
		
		Field[] fields = clazz.getDeclaredFields();
		if (fields.length != 0) {
			for (Field itemField : fields) {
				Annotation[] fieldAnnotations = itemField.getAnnotations();
				for (Annotation itemAnnotation : fieldAnnotations) {
					if (itemAnnotation instanceof MyAnnotation) {
						// 这里一定是进行强制类型转换的
						MyAnnotation tempAnnotation = (MyAnnotation)itemAnnotation;
						// 这里就以方法调用的方式，获得注解的属性值
						System.out.println(tempAnnotation.field1());
					}
				}
			}
		}
		
		
		Method[] methods = clazz.getDeclaredMethods();
		if (methods.length != 0) {
			for (Method itemMethod : methods) {
				Annotation[] methodAnnotations = itemMethod.getAnnotations();
				for (Annotation itemAnnotation : methodAnnotations) {
					if (itemAnnotation instanceof MyAnnotation) {
						// 这里一定是进行强制类型转换的
						MyAnnotation tempAnnotation = (MyAnnotation)itemAnnotation;
						// 这里就以方法调用的方式，获得注解的属性值
						System.out.println(tempAnnotation.field1());
					}
				}
			}
		}
	}

}

