package oit.is.z3065.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchMapper {

    @Select("SELECT * FROM MATCHES;")
    ArrayList<Match> selectAllMatch();

    @Insert("INSERT INTO MATCHES (user1,user2,user1Hand,user2Hand,result) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},#{result});")
    void insertMatch(Match match);
}
