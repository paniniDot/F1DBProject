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
import f1.model.Componente;

public class TabellaComponente extends TableImpl<Componente, String>{
	
	private static final String TABLE_NAME = "COMPONENTE";
	
	public TabellaComponente(final Connection connection) {
		super(connection);
	}
	
	@Override
	public String getTableName() {
		return TabellaComponente.TABLE_NAME;
	}

	@Override
	public boolean createTable() {
		final String query = "CREATE TABLE " + TABLE_NAME + " (" +
			"idComponente CHAR(16) NOT NULL PRIMARY KEY," +
			"descrizione VARCHAR(50) NOT NULL," +
			"prezzoUnitario INT NOT NULL" +
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
	public Optional<Componente> findByPrimaryKey(String idComponente) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idComponente = ?";
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, idComponente);
            final ResultSet resultSet = statement.executeQuery();
            return readComponenteFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Componente> findAll() {
		final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            final ResultSet resultSet = statement.executeQuery();
            return readComponenteFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	
	@Override
	public boolean save(Componente componente) {
		final String query = "INSERT INTO " + TABLE_NAME + "(idComponente, descrizione, prezzoUnitario) VALUES (?,?,?)";
		try (final PreparedStatement statement = super.getConnection().prepareStatement(query)) {
            statement.setString(1, componente.getIdComponente());
            statement.setString(2, componente.getDescrizione());
            statement.setInt(3, componente.getPrezzoUnitario());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Componente updatedValue) {
		throw new UnsupportedOperationException(); 
	}	

	@Override
	public boolean delete(String primaryKey) {
		throw new UnsupportedOperationException();
	}
	
	
	private List<Componente> readComponenteFromResultSet(final ResultSet set) {
		List<Componente> list = new ArrayList<>();
		try {
            while (set.next()) {
                final String idComponente = set.getString("idComponente");
                final String descrizione = set.getString("descrizione");
                final int prezzoUnitario = set.getInt("prezzoUnitario");
            	final Componente componente = new Componente(idComponente, descrizione, prezzoUnitario);
            	list.add(componente);
            }
        } catch (final SQLException e) {}
        return list;
	}
}
