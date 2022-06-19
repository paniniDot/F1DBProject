package f1.model;

import java.util.Date;
import java.util.Objects;

public class Dipendente {
	private final String cf;
	private final String nome;
	private final String cognome;
	private final java.util.Date dataNascita;
	private String residenza;
	
	public Dipendente(final String cf, final String nome, final String cognome, final Date dataNascita, final String residenza) {
		this.cf = cf;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.residenza = residenza;
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
	
	@Override
	public String toString() {
		return "Dipendente [cf=" + cf + ", nome=" + nome + ", cognome=" + cognome + ", dataNascita=" + dataNascita
				+ ", residenza=" + residenza + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cf, cognome, dataNascita, nome, residenza);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dipendente other = (Dipendente) obj;
		return Objects.equals(cf, other.cf) && Objects.equals(cognome, other.cognome)
				&& Objects.equals(dataNascita, other.dataNascita) && Objects.equals(nome, other.nome)
				&& Objects.equals(residenza, other.residenza);
	}


	
	
	
	
}