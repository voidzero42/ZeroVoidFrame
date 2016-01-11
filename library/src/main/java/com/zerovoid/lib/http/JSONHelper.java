package com.zerovoid.lib.http;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 将对象转成JSON字符串
 * 
 * @author chenxiang
 */
public class JSONHelper {

	/**
	 * JSON字符串特殊字符处理，比如：“\A1;1300”
	 * 
	 * @param s
	 * @return String
	 */
	public static String string2Json(String s) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			// case '/':
			// sb.append("\\/");
			// break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString().replaceAll("null", "");
	}

	/**
	 * JSON对象处理
	 * 
	 * @param obj
	 * @return
	 */
	public static String Object2Json(Object obj) {
		if (obj instanceof Map) {
			return Map2Json((Map<String, ?>) obj);
		} else if (obj instanceof List) {
			return List2Json((List<?>) obj);
		} else {
			return "\"" + string2Json(String.valueOf(obj)) + "\"";
		}
	}

	/**
	 * JSON Map处理
	 * 
	 * @param map
	 * @return
	 */
	public static String Map2Json(Map<String, ?> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (String key : map.keySet()) {
			if (1 < sb.length())
				sb.append(",");
			sb.append(getString(key));
			sb.append(":");
			Object obj = map.get(key);
			if (obj instanceof List) {
				sb.append(List2Json((List<?>) obj));
			} else if (obj instanceof Map) {
				sb.append(Map2Json((Map<String, ?>) obj));
			} else
				sb.append("\"" + string2Json(String.valueOf(obj)) + "\"");
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * JSON List处理
	 * 
	 * @param map
	 * @return
	 */
	public static String List2Json(List<?> list) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (Object obj : list) {
			if (1 < sb.length())
				sb.append(",");
			if (obj instanceof Map) {
				sb.append(Map2Json((Map<String, ?>) obj));
			} else if (obj instanceof List) {
				sb.append(List2Json((List<?>) obj));
			} else
				sb.append("\"" + string2Json(String.valueOf(obj)) + "\"");
		}
		sb.append("]");
		return sb.toString();
	}

	public static String getString(String str) {
		return "\"" + str + "\"";
	}

	public static Object json2Object(String json) {
		json = filterJson(json);
		if (json.startsWith("[") && json.endsWith("]")) {
			return jsonToList(json);
		} else if (json.startsWith("{") && json.endsWith("}")) {
			return jsonToMap(json);
		} else {
			return json;
		}
	}

	public static String filterJson(String json) {
		if (null == json || "".equals(json))
			return json;
		return replaceJson(json);
	}

	private static String replaceJson(String json) {
		String ret = null;
		if (json.contains("\n")) {
			ret = json.replaceAll("\n", "");
		} else if (json.contains("\t")) {
			ret = json.replaceAll("\t", "");
		} else if (json.contains("null")) {
			ret = json.replaceAll("null", "");
		} else {
			return json;
		}
		return replaceJson(ret);
	}

	public static Map<String, Object> jsonToMap(String json) {
		try {
			if (json == null) {
				return null;
			}
			json = json.trim();
			if (!json.startsWith("{") || !json.endsWith("}")) {
				return null;
			}
			return jsonobjToMap(new JSONObject(json));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> jsonobjToMap(JSONObject jsonObject) {
		try {
			@SuppressWarnings("unchecked")
			Iterator<String> keyIter = jsonObject.keys();
			HashMap<String, Object> valueMap = new HashMap<String, Object>();
			while (keyIter.hasNext()) {
				String key = (String) keyIter.next();
				Object value = jsonObject.get(key);
				if (value instanceof JSONObject) {
					value = jsonobjToMap((JSONObject) value);
				} else if (value instanceof JSONArray) {
					value = jsonarrToList((JSONArray) value);

				} else {
					value = String.valueOf(value);
				}
				valueMap.put(key, value);
			}
			return valueMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Object> jsonToList(String json) {
		try {
			if (json == null) {
				return null;
			}
			json = json.trim();
			if (!json.startsWith("[") || !json.endsWith("]")) {
				return null;
			}
			return jsonarrToList(new JSONArray(json));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Object> jsonarrToList(JSONArray jsonArray) {
		List<Object> list = new ArrayList<Object>();
		try {

			for (int i = 0; i < jsonArray.length(); i++) {
				Object value = jsonArray.get(i);
				if (value instanceof JSONObject) {
					value = jsonobjToMap((JSONObject) value);
				} else if (value instanceof JSONArray) {
					value = jsonarrToList((JSONArray) value);
				} else {
					value = String.valueOf(value);
				}
				list.add(value);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Map<String, String>> josn2list(String param) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		HashMap<String, String> map = null;
		System.out.println(param);
		String paramRe = param.replaceAll("\\[\\{", "")
				.replaceAll("\\}\\]", "").replaceAll("\\}\\,", "###")
				.replaceAll(" ", "");
		paramRe = paramRe.replaceAll("\\{", "").replaceAll("\\}", "")
				.replaceAll("=,", "=").replaceAll(",,", ",");
		System.out.println(paramRe);
		String josnStr[] = paramRe.split("###");
		String elmentStr[] = null;
		if (josnStr.length > 1) {
			for (int i = 0; i < josnStr.length; i++) {
				map = new HashMap<String, String>();
				elmentStr = josnStr[i].split(",");
				for (int j = 0; j < elmentStr.length; j++) {
					String item[] = elmentStr[j].split("=");
					if (item.length > 1) {
						map.put(item[0], item[1]);
					}
				}
				list.add(map);
			}
		} else {
			map = new HashMap<String, String>();
			elmentStr = paramRe.split(",");
			for (int j = 0; j < elmentStr.length; j++) {
				String item[] = elmentStr[j].split("=");
				if (item.length > 1) {
					map.put(item[0], item[1]);
				}
			}
			list.add(map);
		}
		return list;
	}

	public static List<Map<String, String>> josn2list(Object param,
			String header, List<Map<String, String>> dataList) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<String> title = new ArrayList<String>();

		for (Map<String, String> map : dataList) {
			title.add(map.get(header));
		}

		List<Object> objList = (List<Object>) param;
		HashMap<String, String> map = null;

		for (int i = 0; i < objList.size(); i++) {
			Object value = objList.get(i);
			if (value instanceof List) {
				map = new HashMap<String, String>();
				List listVlaue = (List) value;
				for (int j = 0; j < listVlaue.size(); j++) {
					map.put(title.get(j), listVlaue.get(j).toString());
				}
			}
			list.add(map);
		}

		return list;
	}

	private static List<Object> jons2list(JSONArray jsonArray) {
		List<Object> list = new ArrayList<Object>();
		try {

			for (int i = 0; i < jsonArray.length(); i++) {
				Object value = jsonArray.get(i);

				if (value instanceof JSONArray) {
					value = jsonarrToList((JSONArray) value);
				} else {
					value = String.valueOf(value);
				}
				list.add(value);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Map<String, String>> drawList(
			List<Map<String, String>> list, String KeyName, String KeyValue) {
		List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
		for (Map<String, String> map : list) {
			if (map.get(KeyName).equals(KeyValue)) {
				listmap.add(map);
			}
		}
		return listmap;
	}
}
