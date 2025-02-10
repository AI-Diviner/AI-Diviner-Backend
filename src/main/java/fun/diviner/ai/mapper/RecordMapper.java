package fun.diviner.ai.mapper;

import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import fun.diviner.ai.entity.database.Record;

@Repository
public interface RecordMapper extends BaseMapper<Record> {

}