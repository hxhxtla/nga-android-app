package com.hxhxtla.ngaapp.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostContentBuilder {

	private static final Pattern P_QUOTE = Pattern.compile(
			"\\[quote\\](.+?)\\[/quote\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	private static final Pattern P_REPLY = Pattern.compile(
			"\\[[pt]id\\S+\\](Reply|Topic)\\[/pid\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	private static final Pattern P_B = Pattern.compile("\\[b\\](.+?)\\[/b\\]",
			Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	public PostContentBuilder() {
		// TODO Auto-generated constructor stub
	}

	public static String buildContent(String value) {
		Matcher matcher;
		String temp;
		StringBuffer sb;

		matcher = P_QUOTE.matcher(value);
		sb = new StringBuffer();
		while (matcher.find()) {
			temp = matcher.group(1);
			matcher.appendReplacement(sb, getR_QUOTE(temp));
		}
		matcher.appendTail(sb);
		value = sb.toString();

		matcher = P_REPLY.matcher(value);
		value = matcher.replaceAll(getR_REPLY());

		matcher = P_B.matcher(value);
		sb = new StringBuffer();
		while (matcher.find()) {
			temp = matcher.group(1);
			matcher.appendReplacement(sb, getR_B(temp));
		}
		matcher.appendTail(sb);
		value = sb.toString();
		value = "<html><head>"
				+ "<META http-equiv='Content-Type' content='text/html; charset=UTF-8'>"
				+ "<style type='text/css'>"
				+ ".quote {background:#E8E8E8;border:1px solid #888;margin:10px 10px 10px 20px;padding:10px}"
				+ ".silver {color:#888}" + "</style></head><body>" + value
				+ "</body></html>";
		return value;
	}

	private static String getR_REPLY() {
		return "<span class='silver'>\\[</span>Reply<span class='silver'>\\]</span>";
	}

	private static String getR_B(String value) {
		return "<b>" + value + "</b>";
	}

	private static String getR_QUOTE(String value) {
		return "<div class='quote'>" + value + "</div>";
	}

}
