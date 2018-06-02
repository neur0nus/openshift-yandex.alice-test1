package com.neuronus.yandex.alice;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;

import com.neuronus.yandex.alice.protocol.YARequest;
import com.neuronus.yandex.alice.protocol.YAResponse;

public class DialogProcessor {

	public YAResponse handleRequest(YARequest req) throws IOException {
		if (req==null) throw new IOException("Invalid request");
		final String user_id = req.session.user_id;
		final String session_id = req.session.session_id;
		final String version = req.version;
		final int message_id = req.session.message_id;
		final String query = req.request.original_utterance;
		
		//TODO - реализовать обработку диалога
		
		YAResponse res = new YAResponse();
		res.version = version;
		res.session.message_id = message_id;
		res.session.session_id = session_id;
		res.session.user_id = user_id;
		res.response.text = "Вы сказали: \""+query+"\"...\nЯ не могу пока вам ответить.\nИзвините!";
		res.response.tts = "Вы сказ+али: \""+query+"\"...\nЯ н+е мог+у пока в+ам +ответить.\n+Извините!";
		return res;
	}
	
	public String loadRequestContentAsString(InputStream inp, String ch) throws IOException {
		if (inp==null) return null;
		StringBuilder res = new StringBuilder();
		LineNumberReader reader = new LineNumberReader(new InputStreamReader(inp,ch));
		String line = reader.readLine();
		while (line!=null) {
			res.append(line).append("\n");
			line = reader.readLine();
		}
		return res.toString();
	}
	
}
