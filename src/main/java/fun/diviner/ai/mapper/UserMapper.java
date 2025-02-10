package fun.diviner.ai.mapper;

import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import fun.diviner.ai.entity.database.User;

@Repository
public interface UserMapper extends BaseMapper<User> {

}