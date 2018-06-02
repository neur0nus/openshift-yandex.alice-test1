package com.neuronus.yandex.alice.protocol;

public class YARequest {

	public YARequestMeta meta = new YARequestMeta();
	public YARequestContent request = new YARequestContent();
	public YARequestSession session = new YARequestSession();
	public String version = "1.0";
	
}
