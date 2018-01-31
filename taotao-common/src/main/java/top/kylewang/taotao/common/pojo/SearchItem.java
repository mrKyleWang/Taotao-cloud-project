package top.kylewang.taotao.common.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
@Document(indexName = "taotao",type = "item")
public class SearchItem implements Serializable{

	@Id
	@Field(index = FieldIndex.not_analyzed,store = true,type = FieldType.Long)
	private Long id;
	@Field(index = FieldIndex.analyzed,store = true,type = FieldType.String)
	private String title;
	@Field(index = FieldIndex.analyzed,store = true,type = FieldType.String)
	private String sell_point;
	@Field(index = FieldIndex.not_analyzed,store = true,type = FieldType.String)
	private Long price;
	@Field(index = FieldIndex.not_analyzed,store = true,type = FieldType.String)
	private String image;
	@Field(index = FieldIndex.analyzed,store = true,type = FieldType.String)
	private String category_name;
	@Field(index = FieldIndex.analyzed,store = true,type = FieldType.String)
	private String item_des;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSell_point() {
		return sell_point;
	}
	public void setSell_point(String sell_point) {
		this.sell_point = sell_point;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getItem_des() {
		return item_des;
	}
	public void setItem_des(String item_des) {
		this.item_des = item_des;
	}
	public String[] getImages() {
		if (image != null && !"".equals(image)) {
			String[] strings = image.split(",");
			return strings;
		}
		return null;
	}
	
}
