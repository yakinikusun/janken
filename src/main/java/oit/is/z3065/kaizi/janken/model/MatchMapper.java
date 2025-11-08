package oit.is.z3065.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {

    @Select("SELECT * FROM MATCHES;")
    ArrayList<Match> selectAllMatch();

    @Select("SELECT * FROM MATCHES WHERE (user1 = #{Uid} OR user2 = #{Uid}) AND isActive = true;")
    Match selectActiveByUserId(int Uid);

    @Insert("INSERT INTO MATCHES (user1,user2,user1Hand,user2Hand,result,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},#{result},#{isActive});")
    void insertMatch(Match match);

    @Update("UPDATE MATCHES SET isActive = #{isActive} WHERE id = #{id};")
    void updateMatchActive(int id, boolean isActive);
}
