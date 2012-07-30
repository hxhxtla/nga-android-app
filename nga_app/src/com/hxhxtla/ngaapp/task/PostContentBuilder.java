package com.hxhxtla.ngaapp.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.AsyncTask;

import com.hxhxtla.ngaapp.bean.CommentInfo;
import com.hxhxtla.ngaapp.bean.IImageTask;
import com.hxhxtla.ngaapp.bean.PostInfo;
import com.hxhxtla.ngaapp.controller.DatabaseManager;
import com.hxhxtla.ngaapp.controller.LocalFileManager;
import com.hxhxtla.ngaapp.controller.SharedInfoController;

public class PostContentBuilder extends AsyncTask<Object, String, String>
		implements IImageTask {
	private static String _IMG_SERVER_PATH;
	private static String _IMG_SERVER_BASE;
	// 正则表达式 检索器
	private static final Pattern P_QUOTE = Pattern.compile(
			"\\[quote\\](.+?)\\[/quote\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	private static final Pattern P_REPLY = Pattern.compile(
			"\\[[pt]id=?\\d{0,20}\\](.+?)\\[/pid\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	private static final Pattern P_B = Pattern.compile("\\[b\\](.+?)\\[/b\\]",
			Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
	private static final Pattern P_URL = Pattern.compile(
			"\\[url=?(.*?)\\](.+?)\\[/url\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	private static final Pattern P_SMILES = Pattern.compile("\\[s:(\\d+)\\]",
			Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
	private static final Pattern P_IMG = Pattern.compile(
			"\\[img\\](.+?)\\[/img\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	private static final Pattern P_COLOR = Pattern.compile(
			"\\[color.*?\\](.+?)\\[/color\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	private static final Pattern P_FLASH = Pattern.compile(
			"\\[flash\\](.+?)\\[/flash\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	private static final Pattern P_DEL = Pattern.compile(
			"\\[del\\](.+?)\\[/del\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	private static final Pattern P_FONT = Pattern.compile(
			"\\[font.*?\\](.+?)\\[/font\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	private static final Pattern P_SIZE = Pattern.compile(
			"\\[size=(\\d{1,3})%?\\](.+?)\\[/size\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	private static final Pattern P_U = Pattern.compile("\\[u\\](.+?)\\[/u\\]",
			Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
	private static final Pattern P_I = Pattern.compile("\\[i\\](.+?)\\[/i\\]",
			Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
	private static final Pattern P_CUSTOMACHIEVE = Pattern.compile(
			"\\[(custom)?achieve\\](.+?)\\[/(custom)?achieve\\]",
			Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
	private static final Pattern P_COLLAPSE = Pattern.compile(
			"\\[collapse=?(.*?)\\](.+?)\\[/collapse\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	private static final Pattern P_LR = Pattern.compile(
			"\\[[lr]\\](.+?)\\[/[lr]\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	private static final Pattern P_ALIGN = Pattern.compile(
			"\\[align=\\w+\\](.+?)\\[/align\\]", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);

	private static final Pattern P_HTTP = Pattern.compile("^http",
			Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	private static final String P_LIST_L = "\\[list\\]";

	private static final String P_LIST_R = "\\[/list\\]";

	private static final Pattern P_LI = Pattern.compile(
			"\\[\\*\\](.+?)(\\[\\*\\]|</ul>)", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);
	// 公用
	public static final Pattern P_PAGENUM = Pattern.compile("\\d+");

	public static final Pattern P_SQUARE_BRACKETS = Pattern.compile(
			"\\[.+?\\]", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	public static final Pattern P_BRACES = Pattern.compile("\\{.+?\\}",
			Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	private static final String ICON_IMAGE_LOADING = "file:///android_res/drawable/logo.png?";

	private static final String ICON_IMAGE_LOADING_REGULAREXPRESSION = "file:///android_res/drawable/logo\\.png\\?";

	private static final HashMap<String, GetImageTask> imageTaskList = SharedInfoController.imageTaskList;

	private PostInfo target;

	private String postContent;

	private DatabaseManager dbm;

	public PostContentBuilder(PostInfo value) {
		target = value;
	}

	private String getContentHtml(String value) {
		if (value != null && !value.isEmpty()) {
			Matcher matcher;
			String temp;
			String temp2;
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
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				matcher.appendReplacement(sb, getR_REPLY(temp));
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = P_B.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				matcher.appendReplacement(sb, getR_B(temp));
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = P_URL.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				temp2 = matcher.group(2);
				matcher.appendReplacement(sb, getR_URL(temp, temp2));
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = P_SMILES.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				matcher.appendReplacement(sb, getR_SMILES(temp));
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = P_IMG.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				matcher.appendReplacement(sb, getR_IMG(temp));
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = P_COLOR.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				matcher.appendReplacement(sb, getR_COLOR(temp));
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = P_FLASH.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				matcher.appendReplacement(sb, getR_URL(temp, temp));
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = P_DEL.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				matcher.appendReplacement(sb, getR_DEL(temp));
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = P_FONT.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				matcher.appendReplacement(sb, getR_FONT(temp));
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = P_SIZE.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				temp2 = matcher.group(2);
				matcher.appendReplacement(sb, getR_SIZE(temp, temp2));
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = P_U.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				matcher.appendReplacement(sb, getR_U(temp));
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = P_I.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				matcher.appendReplacement(sb, getR_I(temp));
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = P_CUSTOMACHIEVE.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				matcher.appendReplacement(sb, getR_REPLY("成就样式，暂不支持"));
			}
			matcher.appendTail(sb);
			value = sb.toString();

			matcher = P_COLLAPSE.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				temp2 = matcher.group(2);
				matcher.appendReplacement(sb, getR_COLLAPSE(temp, temp2));
			}
			matcher.appendTail(sb);
			value = sb.toString();
			value = "<p>" + value + "</p>";

			matcher = P_LR.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				matcher.appendReplacement(sb, getR_FONT(temp));
			}
			matcher.appendTail(sb);
			value = sb.toString();
			value = "<p>" + value + "</p>";

			matcher = P_ALIGN.matcher(value);
			sb = new StringBuffer();
			while (matcher.find()) {
				temp = matcher.group(1);
				matcher.appendReplacement(sb, getR_FONT(temp));
			}
			matcher.appendTail(sb);
			value = sb.toString();
			value = "<p>" + value + "</p>";

			value = value.replaceAll(P_LIST_L, "<ul>");
			value = value.replaceAll(P_LIST_R, "</ul>");

			do {
				matcher = P_LI.matcher(value);
				sb = new StringBuffer();
				if (matcher.find()) {
					temp = matcher.group(1);
					temp2 = matcher.group(2);
					matcher.appendReplacement(sb, getR_LI(temp, temp2));
					matcher.appendTail(sb);
					value = sb.toString();
				} else {
					break;
				}
			} while (true);
		}
		return value;
	}

	public static String getTitleHtml(String value) {
		if (value != null && !value.isEmpty()) {
			Matcher matcher = P_URL.matcher(value);
			if (matcher.find()) {
				value = matcher.group(2);
			}
			value = "[" + value + "]";
		} else {
			value = "";
		}
		return value;
	}

	public static String getCommentHtml(ArrayList<CommentInfo> commentList) {
		String value = "";
		if (commentList != null && !commentList.isEmpty()) {
			value = "<h4 class='comment'>评论</h4><br/>";
			for (CommentInfo ci : commentList) {
				value = value + "<b>" + ci.author
						+ " :</b><p class='comment_content'>" + ci.conntent
						+ "</p>";
			}

		}
		return value;
	}

	// 对应的代替html tag
	private static String getR_REPLY(String value) {
		return "<span class='silver'>\\[</span>" + value
				+ "<span class='silver'>\\]</span>";
	}

	private static String getR_B(String value) {
		return "<b>" + value + "</b>";
	}

	private static String getR_QUOTE(String value) {
		return "<div class='quote'>" + value + "</div>";
	}

	private static String getR_URL(String value, String value2) {
		String href;
		if (value == null || value.isEmpty()) {
			href = value2;
		} else {
			href = value;
		}
		return "<span class='chocolate'>[</span><a href='" + href + "'>"
				+ value2 + "</a><span class='chocolate'>]</span>";
	}

	private static String getR_SMILES(String temp) {
		if (_IMG_SERVER_PATH == null || _IMG_SERVER_PATH.isEmpty()) {
			_IMG_SERVER_PATH = "http://img4.ngacn.cc/ngabbs";
		}
		if (temp.equals("1"))
			temp = _IMG_SERVER_PATH + "/post/smile/smile.gif";
		else if (temp.equals("2"))
			temp = _IMG_SERVER_PATH + "/post/smile/mrgreen.gif";
		else if (temp.equals("3"))
			temp = _IMG_SERVER_PATH + "/post/smile/question.gif";
		else if (temp.equals("4"))
			temp = _IMG_SERVER_PATH + "/post/smile/wink.gif";
		else if (temp.equals("5"))
			temp = _IMG_SERVER_PATH + "/post/smile/redface.gif";
		else if (temp.equals("6"))
			temp = _IMG_SERVER_PATH + "/post/smile/sad.gif";
		else if (temp.equals("7"))
			temp = _IMG_SERVER_PATH + "/post/smile/cool.gif";
		else if (temp.equals("8"))
			temp = _IMG_SERVER_PATH + "/post/smile/crazy.gif";
		else if (temp.equals("32"))
			temp = _IMG_SERVER_PATH + "/post/smile/12.gif";
		else if (temp.equals("33"))
			temp = _IMG_SERVER_PATH + "/post/smile/13.gif";
		else if (temp.equals("34"))
			temp = _IMG_SERVER_PATH + "/post/smile/14.gif";
		else if (temp.equals("30"))
			temp = _IMG_SERVER_PATH + "/post/smile/10.gif";
		else if (temp.equals("29"))
			temp = _IMG_SERVER_PATH + "/post/smile/09.gif";
		else if (temp.equals("28"))
			temp = _IMG_SERVER_PATH + "/post/smile/08.gif";
		else if (temp.equals("27"))
			temp = _IMG_SERVER_PATH + "/post/smile/07.gif";
		else if (temp.equals("26"))
			temp = _IMG_SERVER_PATH + "/post/smile/06.gif";
		else if (temp.equals("25"))
			temp = _IMG_SERVER_PATH + "/post/smile/05.gif";
		else if (temp.equals("24"))
			temp = _IMG_SERVER_PATH + "/post/smile/04.gif";
		else if (temp.equals("35"))
			temp = _IMG_SERVER_PATH + "/post/smile/15.gif";
		else if (temp.equals("36"))
			temp = _IMG_SERVER_PATH + "/post/smile/16.gif";
		else if (temp.equals("37"))
			temp = _IMG_SERVER_PATH + "/post/smile/17.gif";
		else if (temp.equals("38"))
			temp = _IMG_SERVER_PATH + "/post/smile/18.gif";
		else if (temp.equals("39"))
			temp = _IMG_SERVER_PATH + "/post/smile/19.gif";
		else if (temp.equals("40"))
			temp = _IMG_SERVER_PATH + "/post/smile/20.gif";
		else if (temp.equals("41"))
			temp = _IMG_SERVER_PATH + "/post/smile/21.gif";
		else if (temp.equals("42"))
			temp = _IMG_SERVER_PATH + "/post/smile/22.gif";
		else if (temp.equals("43"))
			temp = _IMG_SERVER_PATH + "/post/smile/23.gif";

		return "<img src='" + temp + "' alt=''/>";
	}

	private String getR_IMG(String temp) {
		if (_IMG_SERVER_BASE == null || _IMG_SERVER_BASE.isEmpty()) {
			_IMG_SERVER_BASE = "http://img.ngacn.cc/attachments";
		}
		Matcher matcher = P_HTTP.matcher(temp);
		String url;
		if (matcher.find()) {
			url = temp;
		} else {
			url = _IMG_SERVER_BASE + temp.substring(1);
		}
		if (SharedInfoController.LoadedImageList.containsKey(url)) {// 一级缓存
			url = SharedInfoController.LoadedImageList.get(url);
		} else {

			if (dbm == null) {
				dbm = new DatabaseManager(SharedInfoController.CURRENT_ACTIVITY);
			}
			String uuid = dbm.getImageUUIDByURL(url);
			if (uuid != null && !uuid.isEmpty()) {// 二级缓存
				String localFilePath = "file://"
						+ LocalFileManager.getImageLocalPath(uuid);
				SharedInfoController.LoadedImageList.put(url, localFilePath);
				url = localFilePath;
			} else {// 加载
				GetImageTask imageTask;
				if (imageTaskList.containsKey(url)) {
					imageTask = imageTaskList.get(url);
					if (imageTask.imageLocalURL != null
							&& !imageTask.imageLocalURL.isEmpty()) {
						url = imageTask.imageLocalURL;
					} else {
						url = ICON_IMAGE_LOADING + imageTask.imageUUID;
						imageTask.addTaskDestination(this);
					}
				} else {
					imageTask = new GetImageTask(this);
					imageTask.imageUUID = UUID.randomUUID().toString();
					imageTaskList.put(url, imageTask);
					String tempUrl = ICON_IMAGE_LOADING + imageTask.imageUUID;
					if (SharedInfoController.showImage()) {
						imageTask.execute(url);
					} else {
						SharedInfoController.Wait4LoadImageList.put(tempUrl,
								url);
						target.setJSEnabled(true);
					}
					url = tempUrl;
				}
			}
		}
		return "<img class='img' src='"
				+ url
				+ "' alt='' onclick='window.postInfo.showImageInExtendTool(this.src)' />";
	}

	private static String getR_COLOR(String temp) {
		return "<span class='color'>" + temp + "</span>";
	}

	private static String getR_DEL(String temp) {
		return "<span class='del'>" + temp + "</span>";
	}

	private static String getR_FONT(String temp) {
		return temp;
	}

	private static String getR_SIZE(String temp, String temp2) {
		return "<span style='font-size:" + temp + "%;line-height:" + temp
				+ "%'>" + temp2 + "</span>";
	}

	private static String getR_U(String temp) {
		return "<u>" + temp + "</u>";
	}

	private static String getR_I(String temp) {
		return "<i style='font-style:italic'>" + temp + "</i>";
	}

	private static String getR_COLLAPSE(String temp, String temp2) {
		return "<div style='border-top:1px solid #fff;border-bottom:1px solid #fff'>"
				+ temp2 + "</div>";
	}

	private static String getR_LI(String temp, String temp2) {
		return "<li>" + temp + "</li>" + temp2;
	}

	@Override
	protected String doInBackground(Object... params) {
		String content = (String) params[0];
		@SuppressWarnings("unchecked")
		ArrayList<CommentInfo> comment = (ArrayList<CommentInfo>) params[1];
		String title = (String) params[2];

		postContent = "<!DOCTYPE HTML><html><head>"
				+ "<META http-equiv='Content-Type' content='text/html; charset=UTF-8'>"
				+ "<META http-equiv='Cache-Control' content='no-cache'>"
				+ "<META http-equiv='Cache-Control' content='no-store'>"
				+ "<style type='text/css'>"
				+ ".quote {background:#E8E8E8;border:1px solid #888;margin:10px 10px 10px 10px;padding:10px}"
				+ ".silver {color:#888}"
				+ ".gray {color:#555}"
				+ ".chocolate {color:chocolate}"
				+ ".img {max-width:100%}"
				+ ".color {color:#D00}"
				+ ".del {text-decoration:line-through;color:#666}"
				+ ".comment_content {color:#AAA;font-size:14px;margin:0px 0px 0px 20px;}"
				+ ".comment {color:#AAA;font-size:14px;font-weight:bold;border-bottom:1px solid #aaa;clear:both;margin-bottom:0px}"
				+ "</style></head>" + "<body><section>" + getTitleHtml(title)
				+ getContentHtml(content) + getCommentHtml(comment)
				+ "</section></body></html>";
		return postContent;
	}

	@Override
	protected void onPostExecute(String result) {
		target.setContent(result);
	}

	public void callImageBackHander(GetImageTask git) {
		if (postContent != null && !postContent.isEmpty()) {
			if (git.imageLocalURL != null && !git.imageLocalURL.isEmpty()) {
				postContent = postContent.replaceAll(
						ICON_IMAGE_LOADING_REGULAREXPRESSION + git.imageUUID,
						git.imageLocalURL);
				target.setJSEnabled(true);
				SharedInfoController.LoadedImageList.put(git.imageURL,
						git.imageLocalURL);
			}
			imageTaskList.remove(git.imageURL);
			target.setContent(postContent);
		}
	}
}
