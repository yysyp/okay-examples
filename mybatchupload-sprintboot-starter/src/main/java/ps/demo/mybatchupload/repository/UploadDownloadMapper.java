package ps.demo.mybatchupload.repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ps.demo.mybatchupload.entity.UploadDownloadExcel;

@Mapper
public interface UploadDownloadMapper extends BaseMapper<UploadDownloadExcel> {

}
