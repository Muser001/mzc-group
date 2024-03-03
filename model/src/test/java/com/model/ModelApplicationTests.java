package com.model;

import com.psbc.rm.dom.domain.MyLog;
import com.psbc.rm.dom.pojo.AtomicLogDo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = ModelApplication.class)
class ModelApplicationTests {

	@Autowired
	private MyLog myLog;

	@Test
	void contextLoads() {
		List<AtomicLogDo> atomicLogDos = myLog.selectLog("3927");

	}

}
