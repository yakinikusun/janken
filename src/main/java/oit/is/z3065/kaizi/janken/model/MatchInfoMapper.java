package oit.is.z3065.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {
    @Select("SELECT * FROM MATCHINFO WHERE isActive = true;")
    ArrayList<MatchInfo> selectActives();

    @Select("SELECT * FROM MATCHINFO WHERE id = #{id}")
    MatchInfo selectbyId(int id);

    @Select("SELECT id FROM MATCHINFO WHERE (user1 = #{id1} AND user2 = #{id2}) AND isActive = true;")
    Integer searchActive(int id1, int id2);

    @Insert("INSERT INTO MATCHINFO (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
    void insertMatchInfo(MatchInfo match);

    @Update("UPDATE MATCHINFO SET isActive = #{isActive} WHERE id = #{id};")
    void updateActive(int id, boolean isActive);

}
