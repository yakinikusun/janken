package oit.is.z3065.kaizi.janken.model;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

  @Select("SELECT * FROM USERS;")
  ArrayList<User> selectAllUser();

  @Select("SELECT name FROM USERS WHERE id = #{id}")
  String selectById(int id);

  @Select("SELECT id FROM USERS WHERE name = #{name}")
  int selectByName(String name);

}
