package f1.model;

import java.util.Objects;

public class Sponsor {
	private final String idSponsor;
	private final String nome;
	private final String stato;

	public Sponsor(String idSponsor, String nome, String stato) {
		super();
		this.idSponsor = idSponsor;
		this.nome = nome;
		this.stato = stato;
	}

	public String getIdSponsor() {
		return idSponsor;
	}

	public String getNome() {
		return nome;
	}

	public String getStato() {
		return stato;
	}

	@Override
	public String toString() {
		return "Sponsor [idSponsor=" + idSponsor + ", nome=" + nome + ", stato=" + stato + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idSponsor, nome, stato);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sponsor other = (Sponsor) obj;
		return Objects.equals(idSponsor, other.idSponsor) && Objects.equals(nome, other.nome)
				&& Objects.equals(stato, other.stato);
	}

}
