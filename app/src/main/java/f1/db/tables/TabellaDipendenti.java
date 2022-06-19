package f1.db.tables;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import f1.db.TableImpl;
import f1.model.dipendenti.Dipendente;
import f1.utils.Utils;

public class TabellaDipendenti extends TableImpl<Dipendente, String>{
	
	private static final String TABLE_NAME = "DIPENDENTI";
	
	public TabellaDipendenti(final Connection connection) {
		super(connection);
	}
	
	@Override
	public String getTableName() {
		return TabellaDipendenti.TABLE_NAME;
	}

	@Override
	public boolean createTable() {
		final String query = "CREATE TABLE" + TABLE_NAME + " (" +
			"cf CHAR(16) NOT NULL PRIMARY KEY," +
			"nome VARCHAR(50) NOT NULL," +
			"cognome VARCHAR(50) NOT NULL," +
			"dataNascita DATE NOT NULL," +
			"residenza VARCHAR(200) NOT NULL" +")";
				
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
	public Optional<Dipendente> findByPrimaryKey(String CF) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, CF);
            final ResultSet resultSet = statement.executeQuery();
            return readDipendenteFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Dipendente> findAll() {
		final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            final ResultSet resultSet = statement.executeQuery();
            return readDipendenteFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Dipendente> findByNameAndSurname(final String nome, final String cognome) {
		final String query = "SELECT * FROM " + TABLE_NAME + "WHERE nome = ?" + " AND cognome = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, nome);
            statement.setString(2, cognome);
        	final ResultSet resultSet = statement.executeQuery();
            return readDipendenteFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	@Override
	public boolean save(Dipendente dipendente) {
		final String query = "INSERT INTO " + TABLE_NAME + "(cf, nome, cognome, dataNascita, residenza) VALUES (?,?,?,?,?)";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, dipendente.getCf());
            statement.setString(2, dipendente.getNome());
            statement.setString(3, dipendente.getCognome());
            statement.setDate(4, Utils.dateToSqlDate(dipendente.getDatanascita()));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Dipendente updatedValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(String primaryKey) {
		throw new UnsupportedOperationException();
	}

	private List<Dipendente> readDipendenteFromResultSet(final ResultSet set) {
		List<Dipendente> list = new ArrayList<>();
		try {
            while (set.next()) {
                final String cf = set.getString("cf");
                final String nome = set.getString("nome");
                final String cognome = set.getString("cognome");
                final Date dataNascita = Utils.sqlDateToDate(set.getDate("dataNascita"));
                final String residenza = set.getString("residenza");
                final Dipendente dipendente = new Dipendente(cf, nome, cognome, dataNascita, residenza);
                list.add(dipendente);
            }
        } catch (final SQLException e) {}
        return list;
	}

}
