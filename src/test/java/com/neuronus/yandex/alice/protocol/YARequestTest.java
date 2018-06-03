package com.neuronus.yandex.alice.protocol;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.neuronus.yandex.alice.DialogProcessor;
import com.neuronus.yandex.alice.test1.MessageServlet;

class YARequestTest {

	private String testReqJSON;
	
	@BeforeEach
	void setUp() throws Exception {
		try(InputStream inp = this.getClass().getResourceAsStream("/ya_test_request.json")) {
			this.testReqJSON = DialogProcessor.loadRequestContentAsString(inp, "UTF-8");
		}
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFromJSON() throws Exception {
		YARequest yareq = MessageServlet.GSON.fromJson(this.testReqJSON,YARequest.class);
		assertEquals("test command", yareq.request.command, "Unexpected value after JSON deserialization");
	}

}
