package f1.model;

import java.util.Objects;

public class Campionato {
	private final String idCampionato;
	private final int anno;
	private final String nome;
	private final String descrizione;

	public Campionato(String idCampionato, int anno, String nome, String descrizione) {
		this.idCampionato = idCampionato;
		this.anno = anno;
		this.nome = nome;
		this.descrizione = descrizione;
	}

	public String getIdCampionato() {
		return idCampionato;
	}

	public int getAnno() {
		return anno;
	}

	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public String toString() {
		return "Campionato [idCampionato=" + idCampionato + ", anno=" + anno + ", nome=" + nome + ", descrizione="
				+ descrizione + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(anno, descrizione, idCampionato, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campionato other = (Campionato) obj;
		return anno == other.anno && Objects.equals(descrizione, other.descrizione)
				&& Objects.equals(idCampionato, other.idCampionato) && Objects.equals(nome, other.nome);
	}

}
