package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DataUserItem{

	@SerializedName("password")
	private String password;

	@SerializedName("level")
	private String level;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("image_user")
	private String imageUser;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setLevel(String level){
		this.level = level;
	}

	public String getLevel(){
		return level;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setImageUser(String imageUser){
		this.imageUser = imageUser;
	}

	public String getImageUser(){
		return imageUser;
	}

	@Override
 	public String toString(){
		return 
			"DataUserItem{" + 
			"password = '" + password + '\'' + 
			",level = '" + level + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",image_user = '" + imageUser + '\'' + 
			"}";
		}
}