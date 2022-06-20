package f1.model;

import java.util.Objects;

public class Circuito {
	private final String idCircuito;
	private final String nome;
	private final String stato;
	private final String descrizione;
	private final int lunghezza;
	private final String tipologia;
	private final int numeroDiCurve;

	public Circuito(String idCircuito, String nome, String stato, String descrizione, int lunghezza, String tipologia,
			int numeroDiCurve) {
		super();
		this.idCircuito = idCircuito;
		this.nome = nome;
		this.stato = stato;
		this.descrizione = descrizione;
		this.lunghezza = lunghezza;
		this.tipologia = tipologia;
		this.numeroDiCurve = numeroDiCurve;
	}

	public String getIdCircuito() {
		return idCircuito;
	}

	public String getNome() {
		return nome;
	}

	public String getStato() {
		return stato;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public int getLunghezza() {
		return lunghezza;
	}

	public String getTipologia() {
		return tipologia;
	}

	public int getNumeroDiCurve() {
		return numeroDiCurve;
	}

	@Override
	public String toString() {
		return "Circuito [idCircuito=" + idCircuito + ", noem=" + nome + ", stato=" + stato + ", descrizione="
				+ descrizione + ", lunghezza=" + lunghezza + ", tipologia=" + tipologia + ", numeroDiCurve="
				+ numeroDiCurve + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(descrizione, idCircuito, lunghezza, nome, numeroDiCurve, stato, tipologia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Circuito other = (Circuito) obj;
		return Objects.equals(descrizione, other.descrizione) && Objects.equals(idCircuito, other.idCircuito)
				&& lunghezza == other.lunghezza && Objects.equals(nome, other.nome)
				&& numeroDiCurve == other.numeroDiCurve && Objects.equals(stato, other.stato)
				&& Objects.equals(tipologia, other.tipologia);
	}

}
