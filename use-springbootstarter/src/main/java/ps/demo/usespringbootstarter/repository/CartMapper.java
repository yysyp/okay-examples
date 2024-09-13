package ps.demo.usespringbootstarter.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ps.demo.usespringbootstarter.entity.Cart;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {

    Cart getCartAndItems(@Param("id") Long id);

}
