package f1.model;

import java.util.Objects;

public class Vettura {

	private final String idVettura;
	private final String nome;
	private final int annoDiProduzione;
	private final String idTeamProprietario;

	public Vettura(String idVettura, String nome, int annoDiProduzione, String idTeamProprietario) {
		this.idVettura = idVettura;
		this.nome = nome;
		this.annoDiProduzione = annoDiProduzione;
		this.idTeamProprietario = idTeamProprietario;
	}

	public String getIdVettura() {
		return idVettura;
	}

	public String getNome() {
		return nome;
	}

	public int getAnnoDiProduzione() {
		return annoDiProduzione;
	}

	public String getIdTeamProprietario() {
		return idTeamProprietario;
	}

	@Override
	public String toString() {
		return "Vettura [idVettura=" + idVettura + ", nome=" + nome + ", annoDiProduzione=" + annoDiProduzione
				+ ", idTeamProprietario=" + idTeamProprietario + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(annoDiProduzione, idTeamProprietario, idVettura, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vettura other = (Vettura) obj;
		return annoDiProduzione == other.annoDiProduzione
				&& Objects.equals(idTeamProprietario, other.idTeamProprietario)
				&& Objects.equals(idVettura, other.idVettura) && Objects.equals(nome, other.nome);
	}

}
