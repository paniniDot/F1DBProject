package f1.model;

import java.util.Objects;

public class Componente {
	private final String idComponente;
	private final String descrizione;
	private final int prezzoUnitario;
	public Componente(String idComponente, String descrizione, int prezzoUnitario) {
		super();
		this.idComponente = idComponente;
		this.descrizione = descrizione;
		this.prezzoUnitario = prezzoUnitario;
	}
	public String getIdComponente() {
		return idComponente;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public int getPrezzoUnitario() {
		return prezzoUnitario;
	}
	
	@Override
	public String toString() {
		return "Componente [idComponente=" + idComponente + ", descrizione=" + descrizione + ", prezzoUnitario="
				+ prezzoUnitario + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(descrizione, idComponente, prezzoUnitario);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Componente other = (Componente) obj;
		return Objects.equals(descrizione, other.descrizione) && Objects.equals(idComponente, other.idComponente)
				&& prezzoUnitario == other.prezzoUnitario;
	}
	
	
}
