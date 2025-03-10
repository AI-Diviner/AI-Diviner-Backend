package fun.diviner.aidiviner.mapper;

import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import fun.diviner.aidiviner.entity.database.User;

@Repository
public interface UserMapper extends BaseMapper<User> {

}