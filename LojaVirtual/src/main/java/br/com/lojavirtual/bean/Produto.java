package br.com.lojavirtual.bean;

import javax.persistence.*;

@Entity
@Table(name= "produto")
public class Produto {
	
	@Id
	@GeneratedValue
	@Column(name = "prod_id")
	private int id;
	@Column(name = "pro_nome", length = 60, nullable = true)
	private String nome;
	@Column(name = "pro_preco", nullable = true)
	private float preco;
	@Column(name = "pro_url_image", nullable = true)
	private String url_image;



	
	public String getUrl_image() {
		return url_image;
	}
	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getNome(){
		return nome;
	}
	public void setNome(String nome){
		this.nome = nome;
	}
	public float getPreco(){
		return preco;
	}
	public void setPreco(float preco){
		this.preco = preco;
	}
	
}
