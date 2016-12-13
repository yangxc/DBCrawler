package com.peraglobal.crawler.model;

import static com.peraglobal.crawler.process.DataImportException.SEVERE;

import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import com.peraglobal.crawler.process.ConfigParseUtil;
import com.peraglobal.crawler.process.DataImportException;

public class EntityField {

	public static final String AS = "as";

	public static String getAs() {
		return AS;
	}

	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String REF_FILETYPE = "filetype";
	public static final String REF_FILENAME = "filename";

	private final String name;
	private final String as;// 字段名称
	private final Entity entity;
	private final int type; // 字段类型
	private final String filetype;
	private final String filename;
	private final Map<String, String> allAttributes; // 所有field属性 coumn="file"
														// type="2008"
	public String getName() {
		return name;
	}
	
	public String getAS() {
		return as;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public int getType() {
		return type;
	}

	public String getFiletype() {
		return filetype;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public Map<String, String> getAllAttributes() {
		return allAttributes;
	}
	
	
	public EntityField(Builder b) {
		this.name = b.name;
		this.as = b.as;
		this.entity = b.entity;
		this.type = b.type;
		this.filetype = b.filetype;
		this.filename = b.filename;
		this.allAttributes = Collections.unmodifiableMap(new HashMap<String, String>(b.allAttributes));
	}

	/**
	 * Field构建器
	 */
	public static class Builder {
		public String name;
		public String as;
		public Entity entity;
		public int type = Types.VARCHAR; // 默认值
		private final String filetype;
		private final String filename;
		public Map<String, String> allAttributes = new HashMap<String, String>();
		
		public Builder(Element e) {
			this.name = ConfigParseUtil.getStringAttribute(e, NAME, null);
			if (name == null) {
				throw new DataImportException(SEVERE, "Field must have a name attribute");
			}
			this.as = ConfigParseUtil.getStringAttribute(e, AS, null);
			if (as == null) {
				throw new DataImportException(SEVERE, "Field must have a as attribute");
			}
			this.allAttributes = new HashMap<String, String>(ConfigParseUtil.getAllAttributes(e));
			this.type = Integer.parseInt(ConfigParseUtil.getStringAttribute(e, TYPE, null));
			if (Types.BLOB == type || Types.CLOB == type) {
				this.filetype = ConfigParseUtil.getStringAttribute(e, REF_FILETYPE, null);
				this.filename = ConfigParseUtil.getStringAttribute(e, REF_FILENAME, null);
			} else {
				this.filetype = "";
				this.filename = "";
			}
		}
	}

}
