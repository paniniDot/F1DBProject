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
import f1.model.Sponsor;

public class TabellaSponsor  extends TableImpl<Sponsor, String>{
	
	private static final String TABLE_NAME = "SPONSOR";
	
	public TabellaSponsor(final Connection connection) {
		super(connection);
	}
	
	@Override
	public String getTableName() {
		return TabellaSponsor.TABLE_NAME;
	}

	@Override
	public boolean createTable() {
		final String query = "CREATE TABLE " + TABLE_NAME + " (" +
			"idSponsor CHAR(16) NOT NULL PRIMARY KEY," +
			"nome VARCHAR(50) NOT NULL," +
			"stato VARCHAR(50) NOT NULL" +
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
	public Optional<Sponsor> findByPrimaryKey(String idSponsor) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idSponsor = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, idSponsor);
            final ResultSet resultSet = statement.executeQuery();
            return readSponsorFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public List<Sponsor> findByName(final String nome) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE nome = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, nome);
        	final ResultSet resultSet = statement.executeQuery();
            return readSponsorFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Sponsor> findAll() {
		final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            final ResultSet resultSet = statement.executeQuery();
            return readSponsorFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	
	@Override
	public boolean save(Sponsor sponsor) {
		final String query = "INSERT INTO " + TABLE_NAME + "(idSponsor, nome, stato) VALUES (?,?,?)";
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, sponsor.getIdSponsor());
            statement.setString(2, sponsor.getNome());
            statement.setString(3, sponsor.getStato());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Sponsor updatedValue) {
		throw new UnsupportedOperationException(); 
	}	

	@Override
	public boolean delete(String primaryKey) {
		throw new UnsupportedOperationException();
	}
	
	
	private List<Sponsor> readSponsorFromResultSet(final ResultSet set) {
		List<Sponsor> list = new ArrayList<>();
		try {
            while (set.next()) {
                final String idSponsor = set.getString("idSponsor");
                final String nome = set.getString("nome");
                final String stato = set.getString("stato");
            	final Sponsor sponsor = new Sponsor(idSponsor, nome, stato);
                list.add(sponsor);
            }
        } catch (final SQLException e) {}
        return list;
	}

}
