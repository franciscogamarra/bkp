package br.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import br.utils.strings.StringEmpty;
import br.utils.strings.StringReplace;
import br.utils.strings.StringSplit;
import br.utils.strings.StringTrim;

public class JsonUtils {

	private JsonUtils() {
	}

	private static final ObjectMapper om = new ObjectMapper();
	private static final ObjectMapper omString = new ObjectMapper();
	static {
		omString.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false); // desabilita conversão automática de float para int
//		omString.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		omString.disable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

		SimpleModule module = new SimpleModule();
		module.addDeserializer(String.class, new com.fasterxml.jackson.databind.JsonDeserializer<String>() {
			@Override
			public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
				return jp.getText(); // Converte qualquer valor para string
			}
		});

		om.registerModule(module);

	}

	public static String toJson(Object o) {

		if (o == null) {
			return null;
		}

		if (o instanceof String) {
			return (String) o;
		}

		try {
			return om.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw DevException.build(e);
		}

	}
	
	public static <T> T fromJson(String json, Class<T> classe) {
		
		try {

			if (StringEmpty.is(json)) {
				return null;
			}
	
			if (json.contains("{}")) {
				try {
					return classe.getDeclaredConstructor().newInstance();
				} catch (Exception e) {
					throw DevException.build(e);
				}
			}
			
			boolean tudoString = classe == Object.class || classe == LinkedHashMap.class;
	
			if (tudoString) {
				return omString.readValue(json, classe);
			} else {
				return om.readValue(json, classe);
			}
	
		} catch (Exception e) {
			throw DevException.build(e);
		}
		
	}

	public static <T> Lst<T> fromJsonList(String json, Class<T> classe) {

		try {
			List<T> itens = om.readValue(json, om.getTypeFactory().constructCollectionType(List.class, classe));
			return new Lst<>(itens);
		} catch (Exception e) {
			throw DevException.build(e);
		}

	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> fromJson(String text) {

		if (StringEmpty.is(text)) {
			return null;
		}
		
		if (StringTrim.plus(text).equals("{}")) {
			return new HashMap<>();
		}
		
		if (text.startsWith("[")) {
			text = "{\"itens\" : " + text + "}";
		}

		return (Map<String, Object>) fromJson(text, Object.class);

	}

	public static <T> T readValue(File file, TypeReference<T> valueTypeRef) {

		try {
			return om.readValue(file, valueTypeRef);
		} catch (Exception e) {
			throw DevException.build(file.getAbsolutePath());
		}

	}

	public static String trim(String json) {
		return toJson(fromJson(json, Object.class));
	}
	
	public static String format(String s) {
		
		if (StringEmpty.is(s)) {
			return null;
		}

		Object o = s.startsWith("[") ? fromJsonList(s, Object.class) : fromJson(s);
		
		return toJsonFormatado(o);
		
	}
	
	public static String toJsonFormatado(Object o) {
		
		try {
			
			ObjectWriter writer = om.writerWithDefaultPrettyPrinter();
			String string = writer.writeValueAsString(o);
			
			string = StringReplace.exec(string, "\r", "\n");
			string = StringReplace.exec(string, "\n ", "\n");
			string = StringReplace.whilee(string, "\n\n", "\n");
			
			Lst<String> list = StringSplit.exec(string, "\n").map(i -> i.trim());
			Lst<String> list2 = new Lst<>();
			
			String tabs = "";
			
			for (String s : list) {
				
				if (s.startsWith("}")) {
					tabs = tabs.substring(1);
				}
				
				list2.add(tabs + s);
				
				if (s.endsWith("{")) {
					tabs += "\t";
				}
				
			}
			
			return list2.joinString("\n");
			
		} catch (JsonProcessingException e) {
			throw DevException.build(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getProperty(String json, String property) {
		return (T) fromJson(json, LinkedHashMap.class).get(property);
	}

	public static String loadResource(String resourceName) {
		try {
			ClassPathResource resource = new ClassPathResource("jsons/" + resourceName + ".json");
			return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw DevException.build(e);
		}
	}
	
}
