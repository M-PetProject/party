package com.study.party.comm;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommTestDao {

    List<Map> getTest();

}
