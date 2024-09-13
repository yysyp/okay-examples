package ps.demo.usespringbootstarter.repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ps.demo.usespringbootstarter.entity.Order;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
