package au.edu.unsw.comp9322.CLIENT.util;

import java.lang.reflect.Type;
import java.util.Collection;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
@Service
public class HttpUtil {
	
	@SuppressWarnings("unchecked")
	public <T> Object doHttpGet(String REST_URI, HttpHeaders headers, @SuppressWarnings("rawtypes") Class<T> clazz) {
		System.out.println("Requesting server url (GET):" + REST_URI);
		
		RestTemplate restTemplate = new RestTemplate();
		
		//object -> json
//		String objectGson = new Gson().toJson(object);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		// send request and parse result
		ResponseEntity<String> response = restTemplate.exchange(REST_URI, HttpMethod.GET, entity, String.class);	
		
		T resultObj = null;
		try {
			resultObj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		//json -> object
		resultObj = (T) new Gson().fromJson(response.getBody(),  resultObj.getClass());
		
		System.out.println(resultObj.toString());
		return resultObj;
	}
	public <T> Collection<T> doHttpGet_List(String REST_URI, HttpHeaders headers,  @SuppressWarnings("rawtypes") Class<T> clazz) {
		System.out.println("Requesting server url (GET):" + REST_URI);
		
		RestTemplate restTemplate = new RestTemplate();
		
		//object -> json
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		// send request and parse result
		ResponseEntity<String> response = restTemplate.exchange(REST_URI, HttpMethod.GET, entity, String.class);	
//		System.out.println(response.getBody().toString());

		Collection<T> collection;
		Type collectionType = new TypeToken<Collection<T>>(){}.getType();
		collection = new Gson().fromJson(response.getBody(), collectionType);
//		for(T noticeDto:collection) {
//			System.out.println(noticeDto.toString());
//		}
		return collection;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Object doHttpPostEmpty(String REST_URI, HttpHeaders headers, @SuppressWarnings("rawtypes") Class<T> clazz) {
		System.out.println("Requesting server url (POST):" + REST_URI);
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpEntity<String> entity = new HttpEntity<String>( headers);

		// send request and parse result
		ResponseEntity<String> response = restTemplate.exchange(REST_URI, HttpMethod.POST, entity, String.class);	
		
		T resultObj = null;
		try {
			resultObj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		//json -> object
		resultObj = (T) new Gson().fromJson(response.getBody(),  resultObj.getClass());
		
		System.out.println(resultObj.toString());
		return resultObj;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Object doHttpPost(String REST_URI, HttpHeaders headers, Object object, @SuppressWarnings("rawtypes") Class<T> clazz) {
		System.out.println("Requesting server url (POST):" + REST_URI);
		
		RestTemplate restTemplate = new RestTemplate();
		
		//object -> json
		String objectGson = new Gson().toJson(object);
		HttpEntity<String> entity = new HttpEntity<String>(objectGson, headers);

		// send request and parse result
		ResponseEntity<String> response = restTemplate.exchange(REST_URI, HttpMethod.POST, entity, String.class);	
		
		T resultObj = null;
		try {
			resultObj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		//json -> object
		resultObj = (T) new Gson().fromJson(response.getBody(),  resultObj.getClass());
		
		System.out.println(resultObj.toString());
		return resultObj;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public <T> Object doHttpPut(String REST_URI, HttpHeaders headers, Object object, @SuppressWarnings("rawtypes") Class<T> clazz) {
		System.out.println("Requesting server url (PUT):" + REST_URI);
		
		RestTemplate restTemplate = new RestTemplate();
		
		//object -> json
		String objectGson = new Gson().toJson(object);
		HttpEntity<String> entity = new HttpEntity<String>(objectGson, headers);

		// send request and parse result
		ResponseEntity<String> response = restTemplate.exchange(REST_URI, HttpMethod.PUT, entity, String.class);	
		
		T resultObj = null;
		try {
			resultObj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		//json -> object
		resultObj = (T) new Gson().fromJson(response.getBody(),  resultObj.getClass());
		
		System.out.println(resultObj.toString());
		return resultObj;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Object doHttpPutEmpty(String REST_URI, HttpHeaders headers,  @SuppressWarnings("rawtypes") Class<T> clazz) {
		System.out.println("Requesting server url (PUT):" + REST_URI);
		
		RestTemplate restTemplate = new RestTemplate();
		
	
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		// send request and parse result
		ResponseEntity<String> response = restTemplate.exchange(REST_URI, HttpMethod.PUT, entity, String.class);	
		
		T resultObj = null;
		try {
			resultObj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		//json -> object
		resultObj = (T) new Gson().fromJson(response.getBody(),  resultObj.getClass());
		
		System.out.println(resultObj.toString());
		return resultObj;
	}
	
	public <T> Object doHttpDelete(String REST_URI, HttpHeaders headers, Class<T> clazz) {
		System.out.println("Requesting server url (Delete):" + REST_URI);
		
		RestTemplate restTemplate = new RestTemplate();
		
	
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		// send request and parse result
		ResponseEntity<String> response = restTemplate.exchange(REST_URI, HttpMethod.DELETE, entity, String.class);	
		
		T resultObj = null;
		try {
			resultObj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		//json -> object
		resultObj = (T) new Gson().fromJson(response.getBody(),  resultObj.getClass());
		
		System.out.println(resultObj.toString());
		return resultObj;
		
	}
}