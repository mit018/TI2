package maven.demo;

public class Serie {
	private int id;
	private int qtd_episodios;
	private int qtd_temporadas;
	private String nome;
	
	public Serie() {
		this.id = 0;
		this.qtd_episodios = 0;
		this.qtd_temporadas = 0;
		this.nome = "";
	}
	
	public Serie(int id, int ep, int temp, String nome) {
		this.id = id;
		this.qtd_episodios = ep;
		this.qtd_temporadas = temp;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQtdEpisodios() {
		return qtd_episodios;
	}

	public void setQtdEpisodios(int qtd_episodios) {
		this.qtd_episodios = qtd_episodios;
	}

	public int getQtdTemporadas() {
		return qtd_temporadas;
	}

	public void setQtdTemporadas(int qtd_temporadas) {
		this.qtd_temporadas = qtd_temporadas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String toString() {
		return "Serie [id = " + id + ", nome = " + nome + ", quantidade de episodios = " + qtd_episodios + ", quantidade de temporadas = " + qtd_temporadas  + "]";
	}
	
	
}
