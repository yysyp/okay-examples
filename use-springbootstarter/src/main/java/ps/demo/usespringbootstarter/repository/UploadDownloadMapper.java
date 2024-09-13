package ps.demo.usespringbootstarter.repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ps.demo.usespringbootstarter.entity.UploadDownloadExcel;

@Mapper
public interface UploadDownloadMapper extends BaseMapper<UploadDownloadExcel> {

}
