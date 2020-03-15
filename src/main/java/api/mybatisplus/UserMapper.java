package api.mybatisplus;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

// 自定义mapper一定要被Spring加载到Spring容器中，利用代理生成默认实现类
@Component
public interface UserMapper extends BaseMapper<UserModel> {
	// 自定义mapper需要继承MP提供的BaseMapper
	// BaseMapper中提供了很多默认CRUD接口，与ServiceImpl中的默认实现差不多
	
	// 自定义mapper要注意
	// (1)mapper.xml中的namespace一定要是该接口的全类名
	// (2)mapper接口中的方法名一定要和mapper.xml中的表达式id相同
	// (3)如果是分页查询，第一个参数必须是Page对象
	// (4)普通参数要加@Param注解，给参数起个别名，以便在mapper.xml中使用
	
	Page<UserModel> getUserPage(IPage<UserModel> page, @Param("param") UserModel param);
}
