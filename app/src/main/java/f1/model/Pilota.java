package f1.model;

import java.util.Date;
import java.util.Objects;

public class Pilota {
	private final String cf;
	private final String nome;
	private final String cognome;
	private final java.util.Date dataNascita;
	private String residenza;
	private int campionatiVinti;
	private int numeroDiPresenze;
	private int gareVinte;
	


	public Pilota(String cf, String nome, String cognome, Date dataNascita, String residenza, int campionatiVinti,
			int numeroDiPresenze, int gareVinte) {
		super();
		this.cf = cf;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.residenza = residenza;
		this.campionatiVinti = campionatiVinti;
		this.numeroDiPresenze = numeroDiPresenze;
		this.gareVinte = gareVinte;
	}

	public String getCf(){
		return cf;
	}

	public String getNome(){
		return nome;
	}

	public String getCognome(){
		return cognome;
	}

	public java.util.Date getDatanascita(){
		return dataNascita;
	}

	public String getResidenza(){
		return residenza;
	}

	public void setResidenza(String residenza){
		this.residenza=residenza;
	}

	public int getCampionatiVinti() {
		return campionatiVinti;
	}

	public void setCampionatiVinti(int campionatiVinti) {
		this.campionatiVinti = campionatiVinti;
	}

	public int getNumeroDiPresenze() {
		return numeroDiPresenze;
	}

	public void setNumeroDiPresenze(int numeroDiPresenze) {
		this.numeroDiPresenze = numeroDiPresenze;
	}

	public int getGareVinte() {
		return gareVinte;
	}

	public void setGareVinte(int gareVinte) {
		this.gareVinte = gareVinte;
	}

	public java.util.Date getDataNascita() {
		return dataNascita;
	}

	
	@Override
	public String toString() {
		return "Pilota [cf=" + cf + ", nome=" + nome + ", cognome=" + cognome + ", dataNascita=" + dataNascita
				+ ", residenza=" + residenza + ", campionatiVinti=" + campionatiVinti + ", numeroDiPresenze="
				+ numeroDiPresenze + ", gareVinte=" + gareVinte + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(campionatiVinti, cf, cognome, dataNascita, gareVinte, nome, numeroDiPresenze, residenza);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pilota other = (Pilota) obj;
		return campionatiVinti == other.campionatiVinti && Objects.equals(cf, other.cf)
				&& Objects.equals(cognome, other.cognome) && Objects.equals(dataNascita, other.dataNascita)
				&& gareVinte == other.gareVinte && Objects.equals(nome, other.nome)
				&& numeroDiPresenze == other.numeroDiPresenze && Objects.equals(residenza, other.residenza);
	}
	
	
	



}
