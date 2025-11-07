package oit.is.z3065.kaizi.janken.model;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {
    @Insert("INSERT INTO MATCHINFO (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
    void insertMatchInfo(MatchInfo match);

    @Update("UPDATE MATCHINFO SET isActive = #{isActive} WHERE id = #{id};")
    void setActivebyId(int id, boolean isActive);
}
