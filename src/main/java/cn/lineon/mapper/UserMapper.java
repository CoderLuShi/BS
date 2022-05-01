package cn.lineon.mapper;

import cn.lineon.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 刘国庆
 * @version 1.0.0
 * @ClassName User.java
 * @Description TODO
 * @createTime 2022年04月26日 23:04:00
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
