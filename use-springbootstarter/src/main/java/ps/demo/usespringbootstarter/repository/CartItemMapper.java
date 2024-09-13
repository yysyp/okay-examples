package ps.demo.usespringbootstarter.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ps.demo.usespringbootstarter.entity.CartItem;

@Mapper
public interface CartItemMapper extends BaseMapper<CartItem> {

}
