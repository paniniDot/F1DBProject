package f1.db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import f1.db.TableImpl;
import f1.model.Circuito;

public class TabellaCircuiti extends TableImpl<Circuito, String> {

	private static final String TABLE_NAME = "CIRCUITI";

	public TabellaCircuiti(final Connection connection) {
		super(connection);
	}

	@Override
	public String getTableName() {
		return TabellaCircuiti.TABLE_NAME;
	}

	@Override
	public boolean createTable() {
		final String query = "CREATE TABLE " + TABLE_NAME + " (" + "idCircuito CHAR(16) NOT NULL PRIMARY KEY,"
				+ "nome VARCHAR(50) NOT NULL," + "stato VARCHAR(50) NOT NULL," + "descrizione VARCHAR(50) NOT NULL,"
				+ "lunghezza INT NOT NULL," + "tipologia VARCHAR(50) NOT NULL," + "numeroDiCurve INT NOT NULL" + ")";
		try (final Statement statement = super.getConnection().createStatement()) {
			statement.executeUpdate(query);
			return true;
		} catch (final SQLException e) {
			return false;
		}
	}

	@Override
	public boolean dropTable() {
		try (final Statement statement = super.getConnection().createStatement()) {
			statement.executeUpdate("DROP TABLE " + TABLE_NAME);
			return true;
		} catch (final SQLException e) {
			return false;
		}
	}

	@Override
	public Optional<Circuito> findByPrimaryKey(String idCircuito) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idCircuito = ?";
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
			statement.setString(1, idCircuito);
			final ResultSet resultSet = statement.executeQuery();
			return readCircuitoFromResultSet(resultSet).stream().findFirst();
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public List<Circuito> findAll() {
		final String query = "SELECT * FROM " + TABLE_NAME;
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
			final ResultSet resultSet = statement.executeQuery();
			return readCircuitoFromResultSet(resultSet);
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean save(Circuito circuito) {
		final String query = "INSERT INTO " + TABLE_NAME
				+ "(idCircuito, nome, stato, descrizione, lunghezza, tipologia, numeroDiCurve) VALUES (?,?,?,?,?,?,?)";
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
			statement.setString(1, circuito.getIdCircuito());
			statement.setString(2, circuito.getNome());
			statement.setString(3, circuito.getStato());
			statement.setString(4, circuito.getDescrizione());
			statement.setInt(5, circuito.getLunghezza());
			statement.setString(6, circuito.getTipologia());
			statement.setInt(7, circuito.getNumeroDiCurve());
			statement.executeUpdate();
			return true;
		} catch (final SQLIntegrityConstraintViolationException e) {
			return false;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean update(Circuito updatedValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(String primaryKey) {
		throw new UnsupportedOperationException();
	}

	private List<Circuito> readCircuitoFromResultSet(final ResultSet set) {
		List<Circuito> list = new ArrayList<>();
		try {
			while (set.next()) {
				final String idCircuito = set.getString("idCircuito");
				final String nome = set.getString("nome");
				final String stato = set.getString("stato");
				final String descrizione = set.getString("descrizione");
				final int lunghezza = set.getInt("lunghezza");
				final String tipologia = set.getString("tipologia");
				final int numeroDiCurve = set.getInt("numeroDiCurve");
				final Circuito circuito = new Circuito(idCircuito, nome, stato, descrizione, lunghezza, tipologia,
						numeroDiCurve);
				list.add(circuito);
			}
		} catch (final SQLException e) {
		}
		return list;
	}
}
