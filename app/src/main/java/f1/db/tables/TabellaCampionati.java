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
import f1.model.Campionato;

<<<<<<< HEAD
public class TabellaCampionati extends TableImpl<Campionato, String> {

	private static final String TABLE_NAME = "CAMPIONATI";

=======
public class TabellaCampionati extends TableImpl<Campionato, String>{
	
	private static final String TABLE_NAME = "CAMPIONATI";
	
>>>>>>> 816f891df805b27b2cabc6df61148a1d8c2239c2
	public TabellaCampionati(final Connection connection) {
		super(connection);
	}

	@Override
	public String getTableName() {
		return TabellaCampionati.TABLE_NAME;
	}

	@Override
	public boolean createTable() {
<<<<<<< HEAD
		final String query = "CREATE TABLE " + TABLE_NAME + " (" + "idCampionato CHAR(16) NOT NULL PRIMARY KEY,"
				+ "anno INT NOT NULL," + "nome VARCHAR(50) NOT NULL," + "descrizione VARCHAR(50) NOT NULL" + ")";
		try (final Statement statement = super.getConnection().createStatement()) {
=======
		final String query = "CREATE TABLE " + TABLE_NAME + " (" +
			"idCampionato CHAR(16) NOT NULL PRIMARY KEY," +
			"anno INT NOT NULL," +
			"nome VARCHAR(50) NOT NULL," +
			"descrizione VARCHAR(500) NOT NULL" +
			")";
		try(final Statement statement = super.getConnection().createStatement()) {
>>>>>>> 816f891df805b27b2cabc6df61148a1d8c2239c2
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
	public Optional<Campionato> findByPrimaryKey(String IdCampionato) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idCampionato = ?";
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
			statement.setString(1, IdCampionato);
			final ResultSet resultSet = statement.executeQuery();
			return readCampionatoFromResultSet(resultSet).stream().findFirst();
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	public List<Campionato> findByName(final String nome) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE nome = ?";
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
			statement.setString(1, nome);
			final ResultSet resultSet = statement.executeQuery();
			return readCampionatoFromResultSet(resultSet);
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public Optional<Campionato> findByYear(final int anno) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE anno = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setInt(1, anno);
        	final ResultSet resultSet = statement.executeQuery();
            return readCampionatoFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Campionato> findAll() {
		final String query = "SELECT * FROM " + TABLE_NAME;
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
			final ResultSet resultSet = statement.executeQuery();
			return readCampionatoFromResultSet(resultSet);
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean save(Campionato campionato) {
		final String query = "INSERT INTO " + TABLE_NAME + "(idCampionato, anno, nome, descrizione) VALUES (?,?,?,?)";
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
			statement.setString(1, campionato.getIdCampionato());
			statement.setInt(2, campionato.getAnno());
			statement.setString(3, campionato.getNome());
			statement.setString(4, campionato.getDescrizione());
			statement.executeUpdate();
			return true;
		} catch (final SQLIntegrityConstraintViolationException e) {
			return false;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean update(Campionato updatedValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(String primaryKey) {
		throw new UnsupportedOperationException();
	}

	private List<Campionato> readCampionatoFromResultSet(final ResultSet set) {
		List<Campionato> list = new ArrayList<>();
		try {
			while (set.next()) {
				final String idCampionato = set.getString("idCampionato");
				final int anno = set.getInt("anno");
				final String nome = set.getString("nome");
				final String descrizione = set.getString("descrizione");
				final Campionato campionato = new Campionato(idCampionato, anno, nome, descrizione);
				list.add(campionato);
			}
		} catch (final SQLException e) {
		}
		return list;
	}
}
