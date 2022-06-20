package f1.model;

import java.util.Date;
import java.util.Objects;

public class Team {
	private final String idTeam;
	private final String nome;
	private final String sedeCentrale;
	private final java.util.Date dataEsordio;
	private String residenza;
	private int gareVinte;
	private int campionatiVinti;
	
	public Team(String idTeam, String nome, String sedeCentrale, Date dataEsordio, String residenza, int gareVinte,
			int campionatiVinti) {
		super();
		this.idTeam = idTeam;
		this.nome = nome;
		this.sedeCentrale = sedeCentrale;
		this.dataEsordio = dataEsordio;
		this.residenza = residenza;
		this.gareVinte = gareVinte;
		this.campionatiVinti = campionatiVinti;
	}

	public String getResidenza() {
		return residenza;
	}

	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}

	public int getGareVinte() {
		return gareVinte;
	}

	public void setGareVinte(int gareVinte) {
		this.gareVinte = gareVinte;
	}

	public int getCampionatiVinti() {
		return campionatiVinti;
	}

	public void setCampionatiVinti(int campionatiVinti) {
		this.campionatiVinti = campionatiVinti;
	}

	public String getIdTeam() {
		return idTeam;
	}

	public String getNome() {
		return nome;
	}

	public String getSedeCentrale() {
		return sedeCentrale;
	}

	public java.util.Date getDataEsordio() {
		return dataEsordio;
	}

	@Override
	public String toString() {
		return "Team [idTeam=" + idTeam + ", nome=" + nome + ", sedeCentrale=" + sedeCentrale + ", dataEsordio="
				+ dataEsordio + ", residenza=" + residenza + ", gareVinte=" + gareVinte + ", campionatiVinti="
				+ campionatiVinti + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(campionatiVinti, dataEsordio, gareVinte, idTeam, nome, residenza, sedeCentrale);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		return campionatiVinti == other.campionatiVinti && Objects.equals(dataEsordio, other.dataEsordio)
				&& gareVinte == other.gareVinte && Objects.equals(idTeam, other.idTeam)
				&& Objects.equals(nome, other.nome) && Objects.equals(residenza, other.residenza)
				&& Objects.equals(sedeCentrale, other.sedeCentrale);
	}
	
}
