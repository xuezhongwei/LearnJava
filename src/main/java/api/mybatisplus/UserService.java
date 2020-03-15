package api.mybatisplus;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public class UserService extends ServiceImpl<UserMapper,UserModel>{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void selectDemo() {
		// 通过Wrappers来获得Wrapper类，Wrapper用于包装条件
		// 通过entity获得的Wrapper与通过API生成的Wrapper没有任何关联关系
		// 具体Wrapper有QueryWrapper,UpdateWrapper,都是AbstractWrapper的子类
		
		UserModel queryModel = new UserModel();
		
		// 通过entity获得一个QueryWrapper
		QueryWrapper queryWrapper1 = Wrappers.query(queryModel);
		
		// 通过API获得一个Wrapper
		QueryWrapper queryWrapper2 = Wrappers.query();
		// 构造等于条件
		queryWrapper2.eq(true, "name", "zhangsan");
		// 构造大于等于条件
		queryWrapper2.ge(true, "age", 1);
		// 以上两个构造的条件中，第一个参数表示当前条件是否生效，true生效，false不生效；
		// 第二个参数表示数据库中字段名
		
		// 查询一条记录
		UserModel resultModel = getOne(queryWrapper1);
		// 查询多条记录
		List<UserModel> resultList = list(queryWrapper1);
		
		// 分页查询
		// 使用分页查询一定要注入分页插件
		// 构造Page分页对象就好了
		Page<UserModel> page = new Page<>();
		// 设置当前为第一页
		page.setCurrent(1);
		// 设置每页10条数据
		page.setSize(10);
		IPage page2 = page(page, queryWrapper2);
		// 当前页记录数
		List<UserModel> records = page2.getRecords();
		// 总记录数
		long total = page2.getTotal();
	}
	
	public void updateDemo() {
		// 调用ServiceImpl中提供的update相关方法即可
		// 根据id单笔更新
		updateById(new UserModel());
		// 通过id批量更新
		updateBatchById(new ArrayList<UserModel>());
	}
	
	public void insertDemo() {
		// 调用ServiceImp提供的相关save方法即可
		// 单笔插入
		save(new UserModel());
		// 批量插入
		saveBatch(new ArrayList<>());
	}
	
	public void deleteDemo() {
		// 调用ServiceImpl中提供的remove相关方法即可
		remove(Wrappers.emptyWrapper());
		removeByIds(new ArrayList<>());
		removeById("id");
	}
	
	public void mapperDemo() {
		// 使用自定义mapper接口的方式，操作数据库
		
		// 通过getBaseMapper方法获得与当前service相关联的mapper接口
		UserMapper baseMapper2 = getBaseMapper();
		
	}
}
