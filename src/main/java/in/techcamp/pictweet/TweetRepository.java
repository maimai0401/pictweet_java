package in.techcamp.pictweet;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface TweetRepository {

    @Insert("insert into tweets (name, content, image) values (#{name}, #{content}, #{image})")
    void insert(String name, String content, String image);
    @Select("select * from tweets")
    List<TweetEntity> findAll();
}
