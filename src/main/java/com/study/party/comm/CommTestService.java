package com.study.party.comm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommTestService {

    private final CommTestDao commTestDao;

    public List<Map> getTest() {
        return commTestDao.getTest();
    }

}
