package api.fastjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FastJsonDemo {

	public static void main(String[] args) {
		testMap();
	}
	public static void testMap() {
		Map<String, String> map = new HashMap<>();
		map.put("key1", "val1");
		map.put("key2", "val2");
		
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> item1 = new HashMap<>();
		item1.put("k1", "v1");
		Map<String, String> item2 = new HashMap<>();
		item2.put("k2", "v2");
		list.add(item1);
		list.add(item2);
		
		TestBean testBean = new TestBean();
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("map", map);
		jsonObj.put("str", "hello");
		jsonObj.put("list", list);
		jsonObj.put("int", 1);
		jsonObj.put("float", 1.90);
		jsonObj.put("testBean", testBean);
		
		
		System.out.println(jsonObj.toString());
		JSONObject parseJson = JSONObject.parseObject(jsonObj.toString());
		System.out.println(parseJson.get("map").getClass());
		System.out.println(parseJson.get("list").getClass());
		System.out.println(parseJson.get("str").getClass());
		System.out.println(parseJson.get("int").getClass());
		// 末尾的0给弄没了
		System.out.println(parseJson.get("float").getClass() + " " + parseJson.get("float"));
		System.out.println(parseJson.get("testBean").getClass());
		
		// parse list
		JSONArray jsonArray = (JSONArray)parseJson.get("list");
		if (jsonArray.size() != 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject temp = jsonArray.getJSONObject(i);
				System.out.println(temp.get("k1"));
			}
		}
		
		TestBean newBean = parseJson.getObject("testBean", TestBean.class);
		System.out.println(newBean.getA());
	}
	
	static class TestBean {
		int a;
		float b;
		String c;
		Map d;
		List e;
		public int getA() {
			return a;
		}
		public void setA(int a) {
			this.a = a;
		}
		public float getB() {
			return b;
		}
		public void setB(float b) {
			this.b = b;
		}
		public String getC() {
			return c;
		}
		public void setC(String c) {
			this.c = c;
		}
		public Map getD() {
			return d;
		}
		public void setD(Map d) {
			this.d = d;
		}
		public List getE() {
			return e;
		}
		public void setE(List e) {
			this.e = e;
		}
		
	}
}
