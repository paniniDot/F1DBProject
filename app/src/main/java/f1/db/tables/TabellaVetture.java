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
import f1.model.Vettura;

public class TabellaVetture extends TableImpl<Vettura, String> {

	private final static String TABLE_NAME = "VETTURE";
	
	public TabellaVetture(Connection connection) {
		super(connection);
	}

	@Override
	public String getTableName() {
		return TabellaVetture.TABLE_NAME;
	}

	@Override
	public boolean createTable() {
		final String query = "CREATE TABLE " + TABLE_NAME + " (" +
				"idVettura CHAR(16) NOT NULL PRIMARY KEY," +
				"nome VARCHAR(50) NOT NULL," +
				"annoDiProduzione INT NOT NULL," +
				"idTeamProprietario CHAR(16) NOT NULL" +
				")";
		try(final Statement statement = super.getConnection().createStatement()) {
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
	public Optional<Vettura> findByPrimaryKey(String idVettura) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idVettura = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, idVettura);
            final ResultSet resultSet = statement.executeQuery();
            return readVetturaFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Vettura> findByNome(final String nome) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE nome = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, nome);
            final ResultSet resultSet = statement.executeQuery();
            return readVetturaFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public List<Vettura> findByIdTeam(final String idTeam) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idTeamProprietario = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, idTeam);
            final ResultSet resultSet = statement.executeQuery();
            return readVetturaFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public List<Vettura> findByAnno(final int anno) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE annoDiProduzione = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setInt(1, anno);
            final ResultSet resultSet = statement.executeQuery();
            return readVetturaFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	@Override
	public List<Vettura> findAll() {
		final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            final ResultSet resultSet = statement.executeQuery();
            return readVetturaFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean save(Vettura vettura) {
		final String query = "INSERT INTO " + TABLE_NAME + "(idVettura, nome, annoDiProduzione, idTeamProprietario) VALUES (?,?,?,?)";
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, vettura.getIdVettura());
            statement.setString(2, vettura.getNome());
            statement.setInt(3, vettura.getAnnoDiProduzione());
            statement.setString(4, vettura.getIdTeamProprietario());
			statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Vettura updatedValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(String primaryKey) {
		throw new UnsupportedOperationException();
	}

	private List<Vettura> readVetturaFromResultSet(final ResultSet set) {
		List<Vettura> vetture = new ArrayList<>();
		try {
            while (set.next()) {
                final String idVettura = set.getString("idVettura");
                final String nome = set.getString("nome");
                final int annoDiProduzione = set.getInt("annoDiProduzione");
                final String idTeamProprietario = set.getString("idTeamProprietario");
            	final Vettura vettura = new Vettura(idVettura, nome, annoDiProduzione, idTeamProprietario);
                vetture.add(vettura);
            }
        } catch (final SQLException e) {}
        return vetture;
		
	}
	
}
